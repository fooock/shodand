package com.fooock.app.shodand.presenter;

import android.os.Handler;

import com.fooock.app.shodand.view.ExploreView;
import com.fooock.shodand.domain.executor.MainThread;
import com.fooock.shodand.domain.executor.ThreadExecutor;

/**
 *
 */
public class ExploreShodanPresenter extends BasePresenter<ExploreView> {

    private final Handler handler = new Handler();

    public ExploreShodanPresenter(MainThread mainThread, ThreadExecutor threadExecutor) {

    }

    public void update() {
        customView.showLoading();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                customView.hideLoading();
            }
        }, 2000);
    }

    @Override
    void release() {

    }
}
