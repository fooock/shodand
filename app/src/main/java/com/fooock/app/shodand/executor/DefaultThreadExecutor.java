package com.fooock.app.shodand.executor;

import android.support.annotation.NonNull;

import com.fooock.shodand.domain.executor.ThreadExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of {@link ThreadExecutor}
 */
public class DefaultThreadExecutor implements ThreadExecutor {

    private final ThreadPoolExecutor threadPoolExecutor;

    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 10;

    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;

    // default work queue
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();
    private static final ThreadExecutor INSTANCE = new DefaultThreadExecutor();

    private DefaultThreadExecutor() {
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, TIME_UNIT, WORK_QUEUE);
    }

    /**
     * Get the {@link ThreadExecutor}
     *
     * @return the default thread executor
     */
    public static ThreadExecutor getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
