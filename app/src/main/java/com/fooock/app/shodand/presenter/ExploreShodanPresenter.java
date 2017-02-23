package com.fooock.app.shodand.presenter;

import com.fooock.app.shodand.view.ExploreView;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;
import com.fooock.shodand.domain.interactor.GetPopularTags;
import com.fooock.shodand.domain.model.TagCount;
import com.fooock.shodand.domain.model.params.SizeParam;
import com.fooock.shodand.domain.repository.ShodanRepository;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 *
 */
public class ExploreShodanPresenter extends BasePresenter<ExploreView> {

    private final GetPopularTags getPopularTags;

    public ExploreShodanPresenter(ShodanRepository repository, MainThread mainThread,
                                  ThreadExecutor threadExecutor) {
        getPopularTags = new GetPopularTags(repository, mainThread, threadExecutor);
    }

    public void update() {
        Timber.d("Prepared to get popular tags");
        customView.showLoading();
        getPopularTags.execute(new DisposableObserver<List<TagCount>>() {
            @Override
            public void onNext(List<TagCount> tagCounts) {
                if (isAttached()) {
                    customView.showPopularTags(tagCounts);
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                if (isAttached()) {
                    customView.hideLoading();
                    customView.showErrorMessage(e.getLocalizedMessage());
                }
            }

            @Override
            public void onComplete() {
                if (isAttached()) {
                    customView.hideLoading();
                }
            }
        }, new SizeParam());
    }

    @Override
    void release() {
        getPopularTags.close();
    }
}
