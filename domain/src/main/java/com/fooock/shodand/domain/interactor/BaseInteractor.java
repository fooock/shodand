package com.fooock.shodand.domain.interactor;

import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Base class for all interactors
 */
public abstract class BaseInteractor<T, P> {

    private final MainThread mainThread;
    private final ThreadExecutor threadExecutor;
    private final CompositeDisposable compositeDisposable;

    public BaseInteractor(MainThread mainThread, ThreadExecutor threadExecutor) {
        this.mainThread = mainThread;
        this.threadExecutor = threadExecutor;
        this.compositeDisposable = new CompositeDisposable();
    }

    /**
     * Create the result {@link Observable} that will be used when executing the current action
     *
     * @param params Parameters, this argument is optional
     * @return Observable to be executed
     */
    protected abstract Observable<T> result(P params);

    /**
     * Execute the current action
     *
     * @param observer Observer which will be listening to the observable
     * @param params   Parameters, optional
     */
    public void execute(DisposableObserver<T> observer, P params) {
        final Observable<T> observable = result(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(mainThread.scheduler());
        compositeDisposable.add(observable.subscribeWith(observer));
    }

    /**
     * Disposes all resources
     */
    public void close() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }
}
