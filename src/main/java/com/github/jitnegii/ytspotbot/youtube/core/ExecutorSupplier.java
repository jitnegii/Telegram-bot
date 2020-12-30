
package com.github.jitnegii.ytspotbot.youtube.core;

import java.util.concurrent.Executor;

public interface ExecutorSupplier {

    RequestExecutor forRequestTasks();

    Executor forBackgroundTasks();


}
