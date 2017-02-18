package com.fooock.app.shodand.presenter;

import android.os.Handler;

import com.fooock.app.shodand.view.IntroduceKeyView;

import timber.log.Timber;

/**
 *
 */
public class IntroduceKeyPresenter extends BasePresenter<IntroduceKeyView> {

    public void validateApiKey(final String apiKey) {
        customView.showProgress();
        Timber.d("Prepare for validate API key");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAttached()) {
                    if (apiKey == null || apiKey.isEmpty()) {
                        customView.showError("Error validating API key, check it and try again");
                        return;
                    }
                    customView.hideProgress();
                    customView.startApplication();
                }
            }
        }, 3000);
    }
}
