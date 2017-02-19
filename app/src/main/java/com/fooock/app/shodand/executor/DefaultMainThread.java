package com.fooock.app.shodand.executor;

import com.fooock.shodand.domain.executor.MainThread;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Default implementation of {@link com.fooock.shodand.domain.executor.MainThread}
 */
public class DefaultMainThread implements MainThread {

    private static final MainThread INSTANCE = new DefaultMainThread();

    private DefaultMainThread() {
        // force singleton
    }

    /**
     * Get the {@link MainThread}
     *
     * @return the default main thread
     */
    public static MainThread getInstance() {
        return INSTANCE;
    }

    @Override
    public Scheduler scheduler() {
        return AndroidSchedulers.mainThread();
    }
}
