

package com.github.jitnegii.ytspotbot.youtube.core;

import com.github.jitnegii.ytspotbot.youtube.extractor.ExtractorRunnable;

import java.util.concurrent.FutureTask;


public class ExtractorFutureTask extends FutureTask<ExtractorRunnable> {

    private final ExtractorRunnable runnable;

    ExtractorFutureTask(ExtractorRunnable downloadRunnable) {
        super(downloadRunnable, null);
        this.runnable = downloadRunnable;
    }

}
