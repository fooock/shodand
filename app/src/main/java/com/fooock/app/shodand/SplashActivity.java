package com.fooock.app.shodand;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class SplashActivity extends BaseActivity {

    private static final int SPLASH_SCREEN_DELAY = 1000;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Timber.d("Splash screen called onCreate()");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Timber.d("Starting ShodandMainActivity...");

                Intent mainActivity = new Intent(SplashActivity.this, ShodandMainActivity.class);
                startActivity(mainActivity);

                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }

    @Override
    void initializeComponents(@NonNull ShodandApplication application) {
        Timber.d("Initializing splash screen...");

        ButterKnife.bind(this);
    }
}
