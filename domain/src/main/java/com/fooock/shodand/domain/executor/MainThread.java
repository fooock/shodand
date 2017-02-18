package com.fooock.shodand.domain.executor;

import io.reactivex.Scheduler;

/**
 * Encapsulate UI thread to change the context from a background job
 */
public interface MainThread {

    Scheduler scheduler();
}
