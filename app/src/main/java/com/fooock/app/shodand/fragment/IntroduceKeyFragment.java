package com.fooock.app.shodand.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fooock.app.shodand.Navigator;
import com.fooock.app.shodand.Prefs;
import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.presenter.IntroduceKeyPresenter;
import com.fooock.app.shodand.view.IntroduceKeyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 *
 */
public class IntroduceKeyFragment extends BaseFragment implements IntroduceKeyView {

    @BindView(R.id.til_input_api_key)
    protected TextInputLayout tilApiKey;

    @BindView(R.id.et_input_api_key)
    protected EditText etApiKey;

    @BindView(R.id.btn_continue_with_api_key)
    protected Button btnContinue;

    @BindView(R.id.layout_loading)
    protected LinearLayout layoutLoading;

    @BindView(R.id.txt_error_validating_api_key)
    protected TextView txtErrorValidating;

    private IntroduceKeyPresenter introduceKeyPresenter;
    private Navigator navigator;
    private Prefs prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce_key, container, false);
        ButterKnife.bind(this, view);
        // attach the presenter with this view
        introduceKeyPresenter.attachView(this);
        registerForContextMenu(etApiKey);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("Fragment view created");

        setTitle(R.string.title_introduce_key_fragment);
    }

    @Override
    public void onDestroy() {
        introduceKeyPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.edittext_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.mnu_paste:
                Timber.d("Selected paste option from context menu");
                break;
        }
        return true;
    }

    @Override
    void initializeComponents(ShodandApplication application) {
        Timber.d("Initializing components...");
        navigator = application.navigator();
        prefs = application.preferences();
        introduceKeyPresenter = new IntroduceKeyPresenter(
                application.validationRepository(),
                application.mainThread(),
                application.threadExecutor());
    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @OnClick(R.id.btn_continue_with_api_key)
    public void onClickContinue() {
        Timber.d("Continue with API key");
        closeKeyboard();
        // get the content to validate it
        String apiKey = etApiKey.getText().toString();
        introduceKeyPresenter.validateApiKey(apiKey);
    }

    @Override
    public void showProgress() {
        btnContinue.setEnabled(false);
        tilApiKey.setEnabled(false);
        layoutLoading.setVisibility(View.VISIBLE);
        txtErrorValidating.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        btnContinue.setEnabled(false);
        tilApiKey.setEnabled(false);
        layoutLoading.setVisibility(View.GONE);
        txtErrorValidating.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        btnContinue.setEnabled(true);
        tilApiKey.setEnabled(true);
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
        btnContinue.setEnabled(true);
        tilApiKey.setEnabled(true);
        txtErrorValidating.setVisibility(View.VISIBLE);
        txtErrorValidating.setText(R.string.empty_api_key);
    }

    @Override
    public void saveValidApiKey(String apiKey) {
        prefs.saveApiKey(apiKey);
    }
}
