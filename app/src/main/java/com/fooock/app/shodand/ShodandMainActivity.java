package com.fooock.app.shodand;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.fooock.app.shodand.fragment.InitialConfigurationFragment;

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

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new InitialConfigurationFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.activity_container, fragment);
        transaction.commit();
    }

    @Override
    void initializeComponents(@NonNull ShodandApplication application) {
        Timber.d("Initializing components...");

        ButterKnife.bind(this);
    }
}
