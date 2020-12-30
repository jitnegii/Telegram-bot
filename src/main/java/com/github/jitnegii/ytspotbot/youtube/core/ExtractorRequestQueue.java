
package com.github.jitnegii.ytspotbot.youtube.core;

import com.github.jitnegii.ytspotbot.youtube.Status;
import com.github.jitnegii.ytspotbot.youtube.extractor.ExtractorRunnable;
import com.github.jitnegii.ytspotbot.youtube.request.ExtractorRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ExtractorRequestQueue {

    private static ExtractorRequestQueue instance;
    private final Map<Integer, ExtractorRequest> currentRequestMap;
    private final AtomicInteger sequenceGenerator;

    private ExtractorRequestQueue() {
        currentRequestMap = new ConcurrentHashMap<>();
        sequenceGenerator = new AtomicInteger();
    }

    public static void initialize() {
        getInstance();
    }

    public static ExtractorRequestQueue getInstance() {
        if (instance == null) {
            synchronized (ExtractorRequestQueue.class) {
                if (instance == null) {
                    instance = new ExtractorRequestQueue();
                }
            }
        }
        return instance;
    }

    private int getSequenceNumber() {
        return sequenceGenerator.incrementAndGet();
    }

    private void cancelAndRemoveFromMap(ExtractorRequest request) {
        if (request != null) {
            request.cancel();
            currentRequestMap.remove(request.getRequestId());
        }
    }

    public void cancel(int downloadId) {
        ExtractorRequest request = currentRequestMap.get(downloadId);
        cancelAndRemoveFromMap(request);
    }

    public void cancelAll() {
        for (Map.Entry<Integer, ExtractorRequest> currentRequestMapEntry : currentRequestMap.entrySet()) {
            ExtractorRequest request = currentRequestMapEntry.getValue();
            cancelAndRemoveFromMap(request);
        }
    }

    public Status getStatus(int downloadId) {
        ExtractorRequest request = currentRequestMap.get(downloadId);
        if (request != null) {
            return request.getStatus();
        }
        return Status.UNKNOWN;
    }

    public void addRequest(ExtractorRequest request) {
        currentRequestMap.put(request.getRequestId(), request);
        request.setStatus(Status.QUEUED);
        request.setSequenceNumber(getSequenceNumber());

        request.setFuture(Core.getInstance()
                .getExecutorSupplier()
                .forRequestTasks()
                .submit(new ExtractorRunnable(request)));
    }

    public void finish(ExtractorRequest request) {
        currentRequestMap.remove(request.getRequestId());
    }

}
