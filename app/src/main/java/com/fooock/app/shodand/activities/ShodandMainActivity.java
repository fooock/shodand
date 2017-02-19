package com.fooock.app.shodand.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;

import butterknife.ButterKnife;
import timber.log.Timber;

import static com.fooock.app.shodand.fragment.IntroduceKeyFragment.PREF_API_KEY;

/**
 *
 */
public class ShodandMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shodand_main);

        Timber.d("In onCreate()");
    }

    @Override
    void initializeComponents(@NonNull ShodandApplication application) {
        Timber.d("Initializing components...");

        ButterKnife.bind(this);

        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        String apiKey = preferences.getString(PREF_API_KEY, "");

        if (apiKey.isEmpty()) {
            Timber.d("No API key found, opening configuration activity...");
            startAppConfiguration();
            finish();
        }
        Timber.d("Found API key: %s", apiKey);

        application.initializeApiWith(apiKey);
    }

    /**
     * Start the {@link ConfigurationActivity}
     */
    private void startAppConfiguration() {
        Intent confActivity = new Intent(this, ConfigurationActivity.class);
        startActivity(confActivity);
    }
}
