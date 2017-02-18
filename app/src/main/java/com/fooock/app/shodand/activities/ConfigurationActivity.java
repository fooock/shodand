package com.fooock.app.shodand.activities;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.fragment.InitialConfigurationFragment;
import com.google.zxing.integration.android.IntentIntegrator;

import butterknife.ButterKnife;
import timber.log.Timber;

import static com.fooock.app.shodand.fragment.InitialConfigurationFragment.CAMERA_REQUEST_CODE;

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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Timber.d("Checking permission result...");

        if (permissions.length == 0) {
            Timber.d("No permissions granted...");
            return;
        }
        if (requestCode != CAMERA_REQUEST_CODE) {
            Timber.d("Nothing to do, returning...");
            return;
        }
        if (Manifest.permission.CAMERA.equals(permissions[0])
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Timber.d("Permission granted, starting camera...");

            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setBeepEnabled(false).initiateScan();
        }
    }
}
