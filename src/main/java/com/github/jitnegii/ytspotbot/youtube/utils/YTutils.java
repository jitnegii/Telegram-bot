package com.github.jitnegii.ytspotbot.youtube.utils;


import com.github.jitnegii.ytspotbot.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;

public class YTutils {

    private static final String TAG = YTutils.class.getSimpleName();




   /** public static String getImageUrl(String YtUrl) {
        String quality = MainActivity.fragmentActivity.getSharedPreferences("appSettings", Context.MODE_PRIVATE)
                .getString("pref_image_quality", "mq");
        return "https://i.ytimg.com/vi" + getVideoID(YtUrl) + "/" + "default.jpg";
    } */

    public static String getVideoID(String ytUrl) {
        String url = ytUrl;

        if (url.contains("youtube.com")) {
            url = ytUrl.split("=")[1];
        } else if (url.contains("youtu.be")) {
            url = ytUrl.replace("https://youtu.be/", "");
        }
        if (url.contains("&"))
            url = url.split("&")[0];

        return url;
    }

    public static Object getImageURLID( String videoID) {
//        String quality = MainActivity.activity.getSharedPreferences("appSettings", MODE_PRIVATE)
//                .getString("pref_image_quality", "mq");
        String quality = "mq";
        String imageUrlId = "https://i.ytimg.com/vi/" + videoID + "/" + quality + "default.jpg";
        return imageUrlId;
    }

    public static long getFileSize(URL url) {
        URLConnection connection = null;
        try {
            connection = url.openConnection();
            if (connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).setRequestMethod("HEAD");
            }
            connection.getInputStream();
            return connection.getContentLength();
        } catch (ProtocolException e) {
            Log.e(TAG, "URL-FILE_SIZE ERROR: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (connection instanceof HttpURLConnection) {
                ((HttpURLConnection) connection).disconnect();
            }
        }

    }


    public static String getImageURLID_MAX(String videoID) {
        return "https://i.ytimg.com/vi/" + videoID + "/maxresdefault.jpg";
    }

    public static String getImageUrlID_HQ(String videoID) {
        return "https://i.ytimg.com/vi/" + videoID + "/hqdefault.jpg";
    }


    public static String getVideoTitle(String title) {
        String t = title;
        if (t.contains("&#39;"))
            t = t.replace("&#39;", "'");
        if (t.contains("-")) {
            if (t.split("\\-").length < 3)
                t = t.split("\\-")[1].trim();
        }
        if (t.contains("(")) {
            t = t.split("\\(")[0].trim();
        }
        if (t.contains("["))
            t = t.split("\\[")[0].trim();
        return t.trim();
    }

    public static String getChannelTitle(String title, String channelTitle) {


        if (title.contains("&#39;"))
            title = title.replace("&#39;", "'");
        if (channelTitle.contains("-"))
            channelTitle = channelTitle.split("-")[0];
        if (title.contains("-")) {
            String t = title;
            if (t.split("\\-").length < 3)
                t = t.split("\\-")[0].trim();
            return t.trim();
        } else
            return channelTitle;
    }


    public static String getSize(Long size) {

        DecimalFormat df = new DecimalFormat("0.00");

        long sizeKb = 1024;
        long sizeMb = sizeKb * sizeKb;
        long sizeGb = sizeMb * sizeKb;
        long sizeTerra = sizeGb * sizeKb;

        if (size < sizeMb)
            return df.format(size / sizeKb) + " KB";
        else if (size < sizeGb)
            return df.format(size / sizeMb) + " MB";
        else if (size < sizeTerra)
            return df.format(size / sizeGb) + " GB";

        return "0";
    }


    public static String getYtUrl(String videoID) {

        return "https://www.youtube.com/watch?v=" + videoID;
    }

    public static String extractVideoID(String Url) {
        String r="(?<=(be/|v=))(.*?)(?=(&|\n| |\\z))";
        if (RegexUtils.hasMatch(r, Url)) {
            return RegexUtils.matchGroup(r, Url);
        }
        return Url;
    }

    public static boolean isListContain(List<String> arraylist, String statement) {
        for (String str : arraylist) {
            if (statement != null && statement.toLowerCase().contains(str)) {
                return true;
            }
        }
        return false;
    }

}