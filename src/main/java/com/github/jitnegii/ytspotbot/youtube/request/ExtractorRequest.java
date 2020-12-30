package com.github.jitnegii.ytspotbot.youtube.request;

import com.github.jitnegii.ytspotbot.youtube.Status;
import com.github.jitnegii.ytspotbot.youtube.core.ExtractorRequestQueue;
import com.github.jitnegii.ytspotbot.youtube.models.Format;

import java.util.ArrayList;
import java.util.concurrent.Future;

public class ExtractorRequest {

    private ArrayList<Format> formats;
    private onCompleteListener onCompleteListener;
    private int requestId;
    private Status status;
    private int sequenceNumber;
    private Future future;
    private String videoId;

    public ExtractorRequest(String videoId) {
        this.videoId = videoId;
    }



    public ExtractorRequest start(onCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
        requestId = (int) System.currentTimeMillis();

        ExtractorRequestQueue.getInstance().addRequest(this);
        return this;
    }

    public void sendSuccess() {
        if (onCompleteListener != null) {
            onCompleteListener.onComplete(formats);
        }
        finish();
    }

    public void sendError(Error error) {
        if (onCompleteListener != null) {
            onCompleteListener.onFailure(error

            );
        }

        finish();
    }

    public void cancel() {
        status = Status.CANCELLED;
        if (future != null)
            future.cancel(true);
        finish();
        sendError(new Error("Cancelled"));
    }

    public int getRequestId() {
        return requestId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    private void finish() {
        destroy();
        ExtractorRequestQueue.getInstance().finish(this);
    }

    private void destroy() {
        this.onCompleteListener = null;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public ArrayList<Format> getFormats() {
        return formats;
    }

    public void setFormats(ArrayList<Format> formats) {
        this.formats = formats;
    }
}
