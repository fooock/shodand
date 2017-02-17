package com.fooock.app.shodand.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;

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
