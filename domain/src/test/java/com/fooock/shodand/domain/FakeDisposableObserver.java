package com.fooock.shodand.domain;

import io.reactivex.observers.DisposableObserver;

/**
 *
 */
public class FakeDisposableObserver<T> extends DisposableObserver<T> {

    private int counter = 0;

    @Override
    public void onNext(T t) {
        counter++;
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public int getCounter() {
        return counter;
    }
}
