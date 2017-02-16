package com.fooock.app.shodand;

import android.os.Bundle;
import android.support.annotation.NonNull;

import butterknife.ButterKnife;
import timber.log.Timber;

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
    }
}
