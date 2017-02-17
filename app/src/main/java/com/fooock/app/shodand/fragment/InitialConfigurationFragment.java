package com.fooock.app.shodand.fragment;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 *
 */
public class InitialConfigurationFragment extends BaseFragment {

    public static final int CAMERA_REQUEST_CODE = 981;

    @BindView(R.id.btn_scan_qr_code)
    protected Button btnScanQr;

    @BindView(R.id.btn_introduce_key_manually)
    protected Button btnIntroduceManually;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initial_configuration, container, false);
        ButterKnife.bind(this, view);

        Timber.d("Creating view...");

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("Fragment view created");

        setTitle(R.string.title_configure_account);
    }

    @Override
    void initializeComponents(ShodandApplication application) {
        Timber.d("Initializing components...");
    }

    @OnClick(R.id.btn_scan_qr_code)
    public void onClickOnScanQr() {
        Timber.d("Click on scan QR code");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Timber.d("Running on android >= 6.0");

            // Request runtime permission
            if (ActivityCompat.checkSelfPermission(
                    getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

                Timber.d("Camera permission not granted, request it");

                String[] cameraPerm = new String[]{Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(
                        getActivity(), cameraPerm, CAMERA_REQUEST_CODE);
            } else {
                Timber.d("Permission granted, starting camera...");
                startCameraScan();
            }
        } else {
            Timber.d("Running on android < 6.0, no need to request permissions");
            startCameraScan();
        }
    }

    /**
     * Start the activity to start the QR scan with the camera
     */
    private void startCameraScan() {
        IntentIntegrator.forFragment(this)
                .setBeepEnabled(false)
                .initiateScan();
    }

    @OnClick(R.id.btn_introduce_key_manually)
    public void onClickOnIntroduceManually() {
        Timber.d("Click on introduce key manually");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new IntroduceKeyFragment();
        transaction.replace(R.id.activity_configuration_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result == null) {
            Timber.d("No result found in activity result");
            return;
        }
        String contents = result.getContents();
        if (contents == null || contents.isEmpty()) {
            Timber.d("No content found for the qr scan");
            return;
        }
        Timber.d("QR content %s", contents);
    }
}
