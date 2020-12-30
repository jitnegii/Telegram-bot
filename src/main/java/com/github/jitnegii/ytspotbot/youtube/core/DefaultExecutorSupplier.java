
package com.github.jitnegii.ytspotbot.youtube.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;


public class DefaultExecutorSupplier implements ExecutorSupplier {

    private static final int DEFAULT_MAX_NUM_THREADS = 2 * Runtime.getRuntime().availableProcessors() + 1;
    private final RequestExecutor requestExecutor;
    private final Executor backgroundExecutor;


    DefaultExecutorSupplier() {
        ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
        requestExecutor = new RequestExecutor(DEFAULT_MAX_NUM_THREADS, defaultThreadFactory);
        backgroundExecutor = Executors.newSingleThreadExecutor();

    }


    @Override
    public RequestExecutor forRequestTasks() {
        return requestExecutor;
    }

    @Override
    public Executor forBackgroundTasks() {
        return backgroundExecutor;
    }

}
