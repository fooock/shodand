package com.fooock.app.shodand.presenter;

import com.fooock.app.shodand.view.ProtocolView;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.interactor.GetProtocols;
import com.fooock.shodand.domain.model.Protocol;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 *
 */
public class ProtocolPresenter extends BasePresenter<ProtocolView> {

    private final GetProtocols getProtocols;

    public ProtocolPresenter(ShodanRepository repository, MainThread mainThread,
                             ThreadExecutor threadExecutor) {
        getProtocols = new GetProtocols(repository, mainThread, threadExecutor);
    }

    public void update() {
        Timber.d("Prepared to get protocols...");
        customView.showLoading();
        getProtocols.execute(new DisposableObserver<List<Protocol>>() {
            @Override
            public void onNext(List<Protocol> protocols) {
                Timber.d("Received %s protocols", protocols.size());
                if (isAttached()) {
                    customView.showProtocols(protocols);
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                if (isAttached()) {
                    customView.hideLoading();
                    customView.showUnexpectedError();
                }
            }

            @Override
            public void onComplete() {
                Timber.d("Get protocols completed");
                if (isAttached()) {
                    customView.hideLoading();
                }
            }
        }, null);
    }

    @Override
    void release() {
        getProtocols.close();
    }
}
