package com.fooock.app.shodand.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 *
 */
public class IntroduceKeyFragment extends BaseFragment {

    @BindView(R.id.et_input_api_key)
    protected EditText etApiKey;

    @BindView(R.id.btn_continue_with_api_key)
    protected Button btnContinue;

    @BindView(R.id.layout_loading)
    protected LinearLayout layoutLoading;

    @BindView(R.id.txt_error_validating_api_key)
    protected TextView txtErrorValidating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce_key, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("Fragment view created");

        setTitle(R.string.title_introduce_key_fragment);
    }

    @Override
    void initializeComponents(ShodandApplication application) {
        Timber.d("Initializing components...");
    }

    @OnClick(R.id.btn_continue_with_api_key)
    public void onClickContinue(View view) {
        Timber.d("Continue with API key");

        view.setEnabled(false);
        layoutLoading.setVisibility(View.VISIBLE);
    }
}
