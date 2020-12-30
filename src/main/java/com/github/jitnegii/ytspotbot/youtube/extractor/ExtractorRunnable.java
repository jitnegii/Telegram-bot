package com.github.jitnegii.ytspotbot.youtube.extractor;

import com.github.jitnegii.ytspotbot.youtube.Response;
import com.github.jitnegii.ytspotbot.youtube.request.ExtractorRequest;

public class ExtractorRunnable implements Runnable{

    ExtractorRequest request;

    public ExtractorRunnable(ExtractorRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        FormatExtractorTask formatExtractorTask = FormatExtractorTask.getInstance(request);
        Response response = formatExtractorTask.run();

        if(response.isSuccessful()){
            request.sendSuccess();
        }else if(response.getError() != null){
            request.sendError(response.getError());
        }
    }
}
