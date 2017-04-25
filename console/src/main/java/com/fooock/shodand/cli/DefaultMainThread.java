package com.fooock.shodand.cli;

import com.fooock.shodand.domain.executor.MainThread;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
final class DefaultMainThread implements MainThread {

    private static final MainThread INSTANCE = new DefaultMainThread();

    public static MainThread getInstance() {
        return INSTANCE;
    }

    @Override
    public Scheduler scheduler() {
        return Schedulers.single();
    }
}
