package com.fooock.app.shodand.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;

import butterknife.ButterKnife;
import timber.log.Timber;

import static com.fooock.app.shodand.fragment.IntroduceKeyFragment.PREF_API_KEY;

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

                if (isConfigurationValid()) {
                    Timber.d("Starting ConfigurationActivity...");
                    startAppConfiguration();

                } else {
                    Timber.d("Starting ShodandActivity...");
                    startMainApplication();
                }

                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }

    /**
     * Start the {@link ShodandMainActivity}
     */
    private void startMainApplication() {
        Intent mainActivity = new Intent(this, ShodandMainActivity.class);
        startActivity(mainActivity);
    }

    /**
     * Start the {@link ConfigurationActivity}
     */
    private void startAppConfiguration() {
        Intent confActivity = new Intent(this, ConfigurationActivity.class);
        startActivity(confActivity);
    }

    @Override
    void initializeComponents(@NonNull ShodandApplication application) {
        Timber.d("Initializing splash screen...");

        ButterKnife.bind(this);
    }

    /**
     * Check if the application has a valid configuration
     *
     * @return true if configured, false if not
     */
    private boolean isConfigurationValid() {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String apiKey = preferences.getString(PREF_API_KEY, "");
        Timber.d("Configured API key? %s", apiKey);
        return apiKey.isEmpty();
    }
}
