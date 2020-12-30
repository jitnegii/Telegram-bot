package com.github.jitnegii.ytspotbot.youtube.models;

import java.util.ArrayList;
import java.util.List;

public class VideoDetails {

    private String author;
    private String channelId;
    private boolean isLiveContent;
    private boolean isLive;
    private boolean isPrivate;
    private boolean useCipher;
    private String lengthSeconds;
    private Thumbnail thumbnail;
    private String title;
    private String videoId;

    private long expiresInSeconds;

    public long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public void setIsLive(boolean isLive) {
        this.isLive = isLive;
    }

    public boolean getisLive() {
        return isLive;
    }

    public void setUseChiper(boolean useChiper) {
        this.useCipher = useChiper;
    }

    public boolean getUseChiper() {
        return useCipher;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }


    public boolean getIsLiveContent() {
        return isLiveContent;
    }

    public void setIsLiveContent(boolean isLiveContent) {
        this.isLiveContent = isLiveContent;
    }



    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }


    public String getLengthSeconds() {
        return lengthSeconds;
    }

    public void setLengthSeconds(String lengthSeconds) {
        this.lengthSeconds = lengthSeconds;
    }


    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }


    public class Thumbnail {

        private List<Thumbnail_> thumbnails = new ArrayList<Thumbnail_>();

        public List<Thumbnail_> getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(List<Thumbnail_> thumbnails) {
            this.thumbnails = thumbnails;
        }

    }

    public class Thumbnail_ {

        private Integer height;
        private String url;
        private Integer width;

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

    }
}




