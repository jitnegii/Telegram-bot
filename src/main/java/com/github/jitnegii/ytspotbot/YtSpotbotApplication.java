package com.github.jitnegii.ytspotbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YtSpotbotApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(YtSpotbotApplication.class, args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
