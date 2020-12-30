package com.github.jitnegii.ytspotbot.youtube.utils;


import com.github.jitnegii.ytspotbot.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static com.github.jitnegii.ytspotbot.youtube.Constants.USER_AGENT;

public class HTTPUtility {


    public static String downloadPageSource(String url, Map<String, String> headers) {

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("User-Agent", USER_AGENT);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.addHeader(entry.getKey(), entry.getValue());
            }

            HttpResponse response = client.execute(request);

            int responseCode = response.getStatusLine().getStatusCode();

            Log.print("URL : "+url+"\nResponse code : " + responseCode);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder content = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                content.append(line);
            }

            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String downloadPageSource(String url) {


        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = client.execute(request);

            int responseCode = response.getStatusLine().getStatusCode();

            Log.print("URL : "+url+"\nResponse code : " + responseCode);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder content = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                content.append(line);
            }

            return content.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static InputStream getInputStream(String url) {

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("User-Agent", USER_AGENT);

            HttpResponse response = client.execute(request);

            int responseCode = response.getStatusLine().getStatusCode();

            Log.print("URL : "+url+"\nResponse code : " + responseCode);

            return response.getEntity().getContent();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}

