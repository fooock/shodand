package com.fooock.app.shodand.fragment;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fooock.app.shodand.Navigator;
import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.presenter.IntroduceKeyPresenter;
import com.fooock.app.shodand.view.IntroduceKeyView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.fooock.app.shodand.fragment.IntroduceKeyFragment.PREF_API_KEY;

/**
 *
 */
public class InitialConfigurationFragment extends BaseFragment implements IntroduceKeyView {

    public static final int CAMERA_REQUEST_CODE = 981;

    @BindView(R.id.btn_scan_qr_code)
    protected Button btnScanQr;

    @BindView(R.id.btn_introduce_key_manually)
    protected Button btnIntroduceManually;

    @BindView(R.id.layout_loading)
    protected LinearLayout layoutLoading;

    @BindView(R.id.txt_error_validating_api_key)
    protected TextView txtErrorValidating;

    private Snackbar rationaleSnackbar;

    private IntroduceKeyPresenter introduceKeyPresenter;
    private Navigator navigator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initial_configuration, container, false);
        ButterKnife.bind(this, view);
        // attach the presenter with this view
        introduceKeyPresenter.attachView(this);
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
    public void onDestroy() {
        introduceKeyPresenter.detachView();
        super.onDestroy();
    }

    @Override
    void initializeComponents(ShodandApplication application) {
        Timber.d("Initializing components...");
        navigator = application.navigator();
        introduceKeyPresenter = new IntroduceKeyPresenter(
                application.validationRepository(),
                application.mainThread(),
                application.threadExecutor());
    }

    @OnClick(R.id.btn_scan_qr_code)
    public void onClickOnScanQr() {
        Timber.d("Click on scan QR code");

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Timber.d("Running on android < 6.0, no need to request permissions");
            startCameraScan();
            return;
        }
        Timber.d("Running on android >= 6.0");

        // Request runtime permission
        if (ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            Timber.d("Permission not granted...");

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    getActivity(), Manifest.permission.CAMERA)) {
                showRationale();

            } else {
                requestCameraPermission();
            }

        } else {
            Timber.d("Permission granted, starting camera...");
            startCameraScan();
        }
    }

    private void showRationale() {
        View view = getView();
        if (view == null) {
            Timber.d("View is null, not show snackbar");
            return;
        }
        Timber.d("Showing rationale...");
        rationaleSnackbar = Snackbar.make(view, R.string.txt_show_rationale_camera,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.txt_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Timber.d("Clicked snackbar to request permission");
                        requestCameraPermission();
                    }
                });
        rationaleSnackbar.show();
    }

    private void requestCameraPermission() {
        Timber.d("Camera permission not granted, request it");

        String[] cameraPerm = new String[]{Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(
                getActivity(), cameraPerm, CAMERA_REQUEST_CODE);
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
        if (rationaleSnackbar != null && rationaleSnackbar.isShown()) {
            rationaleSnackbar.dismiss();
        }
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
        introduceKeyPresenter.validateApiKey(result.getContents());
    }

    @Override
    public void showProgress() {
        btnIntroduceManually.setEnabled(false);
        btnScanQr.setEnabled(false);
        layoutLoading.setVisibility(View.VISIBLE);
        txtErrorValidating.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        btnIntroduceManually.setEnabled(false);
        btnScanQr.setEnabled(false);
        layoutLoading.setVisibility(View.GONE);
        txtErrorValidating.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        btnIntroduceManually.setEnabled(true);
        btnScanQr.setEnabled(true);
        layoutLoading.setVisibility(View.GONE);
        txtErrorValidating.setVisibility(View.VISIBLE);
        txtErrorValidating.setText(message);
    }

    @Override
    public void startApplication() {
        Timber.d("Starting main application...");
        navigator.showShodandActivity();
    }

    @Override
    public void emptyApiKey() {
        btnIntroduceManually.setEnabled(true);
        btnScanQr.setEnabled(true);
        layoutLoading.setVisibility(View.GONE);
        txtErrorValidating.setVisibility(View.VISIBLE);
        txtErrorValidating.setText(R.string.empty_api_key);
    }

    @Override
    public void saveValidApiKey(String apiKey) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        preferences.edit().putString(PREF_API_KEY, apiKey).apply();
    }
}
