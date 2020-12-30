package com.github.jitnegii.ytspotbot.youtube;

import com.github.jitnegii.ytspotbot.youtube.request.ExtractorRequest;


public class Youtubedl {

    public Youtubedl(){;
    }

    public static ExtractorRequest execute(String url){
        return new ExtractorRequest(url);
    }


}
