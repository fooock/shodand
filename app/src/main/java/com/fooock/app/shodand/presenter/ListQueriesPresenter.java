package com.fooock.app.shodand.presenter;

import com.fooock.app.shodand.view.ListQueriesView;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.interactor.GetListQueries;
import com.fooock.shodand.domain.model.ListQuery;
import com.fooock.shodand.domain.model.params.ListQueryParams;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 *
 */
public class ListQueriesPresenter extends BasePresenter<ListQueriesView> {

    private final GetListQueries getListQueries;

    public ListQueriesPresenter(ShodanRepository repository, MainThread mainThread,
                                ThreadExecutor threadExecutor) {
        getListQueries = new GetListQueries(repository, mainThread, threadExecutor);
    }

    public void update() {
        customView.showLoading();
        getListQueries.execute(new DisposableObserver<List<ListQuery>>() {
            @Override
            public void onNext(List<ListQuery> listQueries) {
                if (isAttached()) {
                    customView.showQueries(listQueries);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isAttached()) {
                    customView.hideLoading();
                    customView.showUnexpectedError();
                }
            }

            @Override
            public void onComplete() {
                if (isAttached()) {
                    customView.hideLoading();
                }
            }
        }, new ListQueryParams());
    }

    @Override
    void release() {
        getListQueries.close();
    }

    /**
     * Update the queries when the user scroll the list of current queries
     *
     * @param page page to fetch
     */
    public void updateNextPage(int page) {
        getListQueries.execute(new DisposableObserver<List<ListQuery>>() {
            @Override
            public void onNext(List<ListQuery> listQueries) {
                if (isAttached()) {
                    customView.addTagsToList(listQueries);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        }, new ListQueryParams(page));
    }
}
