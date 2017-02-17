package com.fooock.app.shodand.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.fragment.InitialConfigurationFragment;

import butterknife.ButterKnife;
import timber.log.Timber;

public class ConfigurationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        Timber.d("In onCreate()");

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new InitialConfigurationFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.activity_configuration_container, fragment);
        transaction.commit();
    }

    @Override
    void initializeComponents(@NonNull ShodandApplication application) {
        Timber.d("Initializing components...");

        ButterKnife.bind(this);
    }
}
