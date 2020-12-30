package com.github.jitnegii.ytspotbot.youtube.extractor;

import com.github.jitnegii.ytspotbot.Log;
import com.github.jitnegii.ytspotbot.youtube.utils.HTTPUtility;
import com.github.jitnegii.ytspotbot.youtube.Constants;
import com.github.jitnegii.ytspotbot.youtube.Response;
import com.github.jitnegii.ytspotbot.youtube.models.*;
import com.github.jitnegii.ytspotbot.youtube.request.ExtractorRequest;
import com.github.jitnegii.ytspotbot.youtube.utils.*;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class FormatExtractorTask {

    private static final String TAG = "YoutubeStreamExtractor";

    ExtractorRequest request;

    private String videoId;
    Map<String, String> Headers;
    ArrayList<Format> formats;
    private VideoDetails videoDetails;

    private Error error;


    private FormatExtractorTask(ExtractorRequest request) {
        this.request = request;
        videoId = request.getVideoId();
        formats = new ArrayList<>();
        Headers = new HashMap<>();
        Headers.put("Accept-Language", "en");
    }

    public static FormatExtractorTask getInstance(ExtractorRequest request) {
        return new FormatExtractorTask(request);
    }

    public Response run() {

        Response response = new Response();

        String jsonBody;
        try {

            YTSearch ytSearch = new YTSearch();
            videoId = ytSearch.getValidId(videoId);

            if (videoId == null) {
                throw new ExtractorException("Invalid url");
            }
            String body = HTTPUtility.downloadPageSource("https://www.youtube.com/watch?v=" + videoId + "&has_verified=1&bpctr=9999999999", Headers);

            jsonBody = parsePlayerConfig(body);

            if (jsonBody == null) {
                throw new ExtractorException("Couldn't parse url body");
            }

            PlayerResponse playerResponse = parseJson(jsonBody);

            if (playerResponse == null) {
                throw new ExtractorException("Couldn't parse player response");
            }

            videoDetails = playerResponse.getVideoDetails();

            if (!playerResponse.getVideoDetails().getisLive()) {

                StreamingData sd = playerResponse.getStreamingData();
                setDefaults(sd);

                Log.print("Formats= " + sd.getFormats().length);
                Log.print("AdaptiveFormats= " + sd.getAdaptiveFormats().length);


                parseUrls(sd.getAdaptiveFormats(), body);

                if (formats.size() == 0) {
                    throw new ExtractorException("Couldn't extract formats");
                }


            }
        } catch (Exception e) {
            response.setSuccessful(false);
            e.printStackTrace();
            error = new Error(e.getMessage());
        }

        if (error != null) {

            Log.e(TAG, "Extraction goes wrong");
            response.setSuccessful(false);
            response.setError(error);

        } else {
            Log.e(TAG, "Extraction goes right");
            request.setFormats(formats);
            response.setSuccessful(true);
        }

        return response;
    }

    private void setDefaults(StreamingData data) {
        try {
            videoDetails.setExpiresInSeconds(data.getExpiresInSeconds());
        } catch (Exception ignored) {
        }
    }


    private PlayerResponse parseJson(String body) {
        JsonParser parser = new JsonParser();
        return new GsonBuilder().serializeNulls().create().fromJson(parser.parse(body), PlayerResponse.class);
    }

    private String parsePlayerConfig(String body) throws ExtractorException {

        if (YTutils.isListContain(Constants.REASON_AVAILABLE, RegexUtils.matchGroup(Constants.REGEX_FIND_REASON, body))) {
            throw new ExtractorException(RegexUtils.matchGroup(Constants.REGEX_FIND_REASON, body));
        }
        if (body.contains("ytInitialPlayerResponse")) {
            Log.print("Contains");
            return RegexUtils.matchGroup(Constants.REGEX_PLAYER_JSON, body, 2);
        } else {
            throw new ExtractorException("This Video is unavailable");
        }
    }

    private void parseUrls(Format[] formats, String body) {

        try {
            for (Format format : formats) {

                if (format.getMimeType().contains("video"))
                    continue;

                if (format.useCipher()) {
                    String tempUrl = null;
                    String decodedSig = null;
                    for (String partCipher : format.getSignatureCipher().split("&")) {

                        if (partCipher.startsWith("s=")) {
                            decodedSig = CipherManager.dechipersig(URLDecoder.decode(partCipher.replace("s=", ""), String.valueOf(StandardCharsets.UTF_8)), body);
                        }

                        if (partCipher.startsWith("url=")) {
                            tempUrl = URLDecoder.decode(partCipher.replace("url=", ""), "UTF-8");

                            for (String url_part : tempUrl.split("&")) {
                                if (url_part.startsWith("s=")) {
                                    decodedSig = CipherManager.dechipersig(URLDecoder.decode(url_part.replace("s=", ""), String.valueOf(StandardCharsets.UTF_8)), body);
                                }
                            }
                        }
                    }

                    if (tempUrl == null || decodedSig == null)
                        continue;

                    String FinalUrl = tempUrl + "&sig=" + decodedSig;

                    Log.d(TAG, format.getMimeType() + " " + FinalUrl);

                    format.setUrl(FinalUrl);
                    addVideoToList(format);

                } else {
                    addVideoToList(format);
                }
            }

        } catch (Exception e) {
            //error = new Error(e.getMessage());
            e.printStackTrace();
        }

    }

    private void addVideoToList(Format format) {
        addVideoToList(format, videoDetails);
    }

    private void addVideoToList(Format format, VideoDetails videoDetails) {

        String quality = "";
        String ext = "mp3";

        if (format.getMimeType().contains("audio/mp4")) {
            ext = "mp4";
            format.isAudio(false);
            quality = String.valueOf(format.getBitrate() / 1000);
        } else if (format.getMimeType().contains("audio")) {

            format.isAudio(true);
            quality = String.valueOf(format.getBitrate() / 1000);

        } else {
            ext = "mp4";
            format.isAudio(false);
            quality = String.valueOf(format.getHeight());
            for (Format f : formats) {
                if (f.getQuality().equals(quality))
                    return;
            }
        }


        format.setTitle(videoDetails.getTitle());
        format.setAuthor(videoDetails.getAuthor());

        for (VideoDetails.Thumbnail_ thumbnail : videoDetails.getThumbnail().getThumbnails()) {
            if (thumbnail.getHeight() < 320 && thumbnail.getWidth() < 320) {
                format.setThumbnailUrl(thumbnail.getUrl());
                break;
            }
        }

        format.setExtension(ext);
        format.setQuality(quality);


        formats.add(format);
    }

}
