package com.github.jitnegii.ytspotbot.youtube.models;


public class StreamingData
{
    private String hlsManifestUrl;
    private long expiresInSeconds;
    private Format[] formats;
    private Format[] adaptiveFormats;

    public void setFormats(Format[] formats)
    {
        this.formats = formats;
    }

    public Format[] getFormats()
    {
        return formats;
    }

    public void setAdaptiveFormats(Format[] adaptiveFormats)
    {
        this.adaptiveFormats = adaptiveFormats;
    }

    public Format[] getAdaptiveFormats()
    {
        return adaptiveFormats;
    }

    public void setExpiresInSeconds(long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public long getExpiresInSeconds() {
        return expiresInSeconds;
    }
    public void setHlsManifestUrl(String hlsManifestUrl) {
        this.hlsManifestUrl = hlsManifestUrl;
    }
    public String getHlsManifestUrl() {
        return hlsManifestUrl;
    }
}

