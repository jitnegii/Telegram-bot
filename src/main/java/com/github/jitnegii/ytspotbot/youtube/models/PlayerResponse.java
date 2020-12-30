package com.github.jitnegii.ytspotbot.youtube.models;


public class PlayerResponse {

    private StreamingData streamingData;
    private VideoDetails videoDetails;

    public void setStreamingData(StreamingData streamingData) {
        this.streamingData = streamingData;
    }

    public StreamingData getStreamingData() {
        return streamingData;
    }

    public void setVideoDetails(VideoDetails videoDetails) {
        this.videoDetails = videoDetails;
    }

    public VideoDetails getVideoDetails() {
        return videoDetails;
    }

}



