package com.fooock.app.shodand.fragment;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduce_key, container, false);
        ButterKnife.bind(this, view);
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
    }

    @OnClick(R.id.btn_continue_with_api_key)
    public void onClickContinue(View view) {
        Timber.d("Continue with API key");

        view.setEnabled(false);
        tilApiKey.setEnabled(false);
        layoutLoading.setVisibility(View.VISIBLE);
    }
}
