package com.github.jitnegii.ytspotbot.youtube.utils;


import com.github.jitnegii.ytspotbot.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpotifyUtils {


    public static String parseUrl(String url) {

        String id = getSpotifyID(url);

        if (id == null)
            return null;

        String Title = null;
        String Author = null;

        String stringUrl = "https://open.spotify.com/track/" + id + "&nd=1";

        Log.d("Spotify url : ", stringUrl);


        try {
            InputStream in = HTTPUtility.getInputStream(stringUrl);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            try {
                boolean foundTitle = false, foundAuthor = false;

                while ((line = reader.readLine()) != null) {


                    if (foundTitle && foundAuthor)
                        break;

                    if (line.contains("\"og:title\"")) {
                        Title = parseString(line, "og:title");
                        foundTitle = true;
                    }

                    if (line.contains("\"music:musician\"")) {
                        String musicianUrl = parseString(line, "music:musician") + "?nd=1";
                        Author = getMusicianName(musicianUrl);
                        foundAuthor = true;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Title == null || Author == null)
            return null;

        return Title + " by " + Author;

    }

    private static String parseString(String line, String tag) {

        String pattern = "<meta property=\"" + tag + "\" content=\".*?\" />";
        Matcher matcher1 = Pattern.compile(
                pattern).matcher(line);
        if (matcher1.find()) {
            return matcher1.group(0).split("\"")[3];
        } else
            return null;
    }

    private static String getMusicianName(String stringUrl) {
        try {

            InputStream in = HTTPUtility.getInputStream(stringUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    if (line.contains("\"og:title\"")) {
                        return parseString(line, "og:title");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSpotifyID(String link) {

        String id = link.split("/track/")[1];
        if (id.contains("&")) {
            id = id.split("&")[0];
        }

        return id;
    }

}
