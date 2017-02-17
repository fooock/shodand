package com.fooock.app.shodand.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 *
 */
public class InitialConfigurationFragment extends BaseFragment {

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
    }

    @OnClick(R.id.btn_introduce_key_manually)
    public void onClickOnIntroduceManually() {
        Timber.d("Click on introduce key manually");

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment fragment = new IntroduceKeyFragment();
        transaction.replace(R.id.activity_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
