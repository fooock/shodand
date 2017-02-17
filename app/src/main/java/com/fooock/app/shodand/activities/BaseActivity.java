package com.fooock.app.shodand.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fooock.app.shodand.ShodandApplication;

/**
 *
 */
abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeComponents(getShodanApplication());
    }

    /**
     * Method to initialize components after {@link #onCreate(Bundle)} is called
     *
     * @param application ShodandApplication
     */
    abstract void initializeComponents(@NonNull ShodandApplication application);

    /**
     * Get the {@link ShodandApplication} class to initialize other components
     *
     * @return Application
     */
    private ShodandApplication getShodanApplication() {
        return (ShodandApplication) getApplication();
    }
}
