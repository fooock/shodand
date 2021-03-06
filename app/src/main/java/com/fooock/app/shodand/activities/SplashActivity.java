package com.fooock.app.shodand.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fooock.app.shodand.Navigator;
import com.fooock.app.shodand.Prefs;
import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class SplashActivity extends BaseActivity {

    private static final int SPLASH_SCREEN_DELAY = 1000;

    private final Handler handler = new Handler();

    private Navigator navigator;
    private Prefs prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Timber.d("Splash screen called onCreate()");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isConfigurationValid()) {
                    Timber.d("Starting ConfigurationActivity...");
                    navigator.showConfigurationActivity();

                } else {
                    Timber.d("Starting ShodandActivity...");
                    navigator.showShodandActivity();
                }

                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }

    @Override
    void initializeComponents(@NonNull ShodandApplication application) {
        Timber.d("Initializing splash screen...");

        ButterKnife.bind(this);
        navigator = application.navigator();
        prefs = application.preferences();
    }

    /**
     * Check if the application has a valid configuration
     *
     * @return true if configured, false if not
     */
    private boolean isConfigurationValid() {
        String apiKey = prefs.getApiKey();
        Timber.d("Configured API key? %s", apiKey);
        return apiKey.isEmpty();
    }
}
