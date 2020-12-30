
package com.github.jitnegii.ytspotbot.youtube.core;

import com.github.jitnegii.ytspotbot.youtube.extractor.ExtractorRunnable;

import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class RequestExecutor extends ThreadPoolExecutor {

    RequestExecutor(int maxNumThreads, ThreadFactory threadFactory) {
        super(maxNumThreads, maxNumThreads, 0, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<Runnable>(), threadFactory);

    }

    @Override
    public Future<?> submit(Runnable task) {

        ExtractorFutureTask futureTask = new ExtractorFutureTask((ExtractorRunnable) task);
        execute(futureTask);
        return futureTask;
    }
}
