package com.fooock.shodand.domain;

import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.interactor.BaseInteractor;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 *
 */
public class FakeInteractorTestClass extends BaseInteractor<Object, Void> {

    public FakeInteractorTestClass(MainThread mainThread, ThreadExecutor threadExecutor) {
        super(mainThread, threadExecutor);
    }

    @Override
    protected Observable<Object> result(Void params) {
        return Observable.empty();
    }

    @Override
    public void execute(DisposableObserver<Object> observer, Void params) {
        super.execute(observer, params);
    }

    @Override
    public void close() {
        super.close();
    }
}
