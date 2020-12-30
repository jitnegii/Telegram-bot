package com.github.jitnegii.ytspotbot.youtube.utils;

import com.github.jitnegii.ytspotbot.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YTSearch {
    private String videoId;
    private String TAG = "YTSearchThread";

    public String getValidId(String textToSearch) {

        try {
            String url;
            if (textToSearch.contains("&"))
                textToSearch = textToSearch.split("&")[0];

            url = "https://www.youtube.com/results?search_query=" + URLEncoder.encode(textToSearch.trim(), StandardCharsets.UTF_8.name());

            InputStream in = HTTPUtility.getInputStream(url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;


            while ((line = reader.readLine()) != null) {

                if (line.contains("//i.ytimg.com/vi")) {
                    Pattern compile = Pattern.compile("//i.ytimg.com/vi/.*?/", Pattern.DOTALL);
                    Matcher matcher = compile.matcher(line);
                    if (matcher.find()) {
                        videoId = matcher.group().split("/")[4];
                        return videoId;
                    }
                }
            }

            in.close();
            reader.close();

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedException " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }

        return null;
    }

    public String getVideoIDs() {
        return videoId;
    }

}
