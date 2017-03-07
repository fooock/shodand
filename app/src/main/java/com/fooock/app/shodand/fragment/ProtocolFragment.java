package com.fooock.app.shodand.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.presenter.ProtocolPresenter;
import com.fooock.app.shodand.view.ProtocolView;
import com.fooock.app.shodand.view.adapter.ProtocolAdapter;
import com.fooock.app.shodand.view.decorator.DividerItemDecorator;
import com.fooock.shodand.domain.model.Protocol;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 *
 */
public class ProtocolFragment extends BaseFragment implements ProtocolView,
        ProtocolView.ProtocolClickListener {

    private static final int DESC_MAX_LINES_ON_COLLAPSED = 2;
    private static final int DESC_MAX_LINES_ON_EXPANDED = Integer.MAX_VALUE;

    @BindView(R.id.pb_loading_content)
    protected ProgressBar progressBar;

    @BindView(R.id.rv_protocols_data)
    protected RecyclerView rvProtocols;

    @BindView(R.id.layout_protocols_not_found)
    protected LinearLayout dataNotFound;

    @BindView(R.id.layout_bottom_sheet)
    protected LinearLayout bottomSheet;

    @BindView(R.id.txt_bs_protocol_name)
    protected TextView txtProtocolName;

    @BindView(R.id.txt_bs_protocol_desc)
    protected TextView txtProtocolDesc;

    private BottomSheetBehavior bottomSheetBehavior;
    private ProtocolPresenter protocolPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protocol, container, false);
        ButterKnife.bind(this, view);
        protocolPresenter.attachView(this);

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Timber.d("Bottom sheet change state to %s", newState);
                protocolPresenter.onBottomSheetStateChange(newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Timber.d("Bottom sheet slide offset %s", slideOffset);
            }
        });

        rvProtocols.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Timber.d("Scroll state change to %s", newState);
                protocolPresenter.onRecyclerViewStateChange(
                        bottomSheetBehavior.getState(), newState);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("Fragment view created");
        showDefaultTitle();

        protocolPresenter.update();
    }

    @Override
    public void onDestroy() {
        protocolPresenter.detachView();
        super.onDestroy();
    }

    @Override
    void initializeComponents(ShodandApplication application) {
        Timber.d("Initializing components...");
        protocolPresenter = new ProtocolPresenter(
                application.shodanRepository(),
                application.mainThread(),
                application.threadExecutor());
    }

    @Override
    public void showDefaultTitle() {
        setTitle(R.string.title_protocols);
    }

    @Override
    public void showCollapsedBottomSheet(Protocol protocol) {
        setTitle(R.string.title_protocols);
        txtProtocolName.setVisibility(View.VISIBLE);
        txtProtocolDesc.setMaxLines(DESC_MAX_LINES_ON_COLLAPSED);
        restoreDefaultPadding();
    }

    /**
     * Restore the default padding level in the protocol description text view
     */
    private void restoreDefaultPadding() {
        int horizontalPixels = getResources().getDimensionPixelSize(
                R.dimen.activity_horizontal_margin);
        txtProtocolDesc.setPadding(horizontalPixels, 0, horizontalPixels, 0);
    }

    @Override
    public void showExpandedBottomSheet(Protocol protocol) {
        setTitle(protocol.name);
        txtProtocolName.setVisibility(View.GONE);
        txtProtocolDesc.setMaxLines(DESC_MAX_LINES_ON_EXPANDED);
        changeDefaultPadding();
    }

    @Override
    public void onHideBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    /**
     * Change the default padding level of the protocol description text view to visualize
     * it correctly
     */
    private void changeDefaultPadding() {
        int horizontalPixels = getResources().getDimensionPixelSize(
                R.dimen.activity_horizontal_margin);
        int verticalPixels = getResources().getDimensionPixelSize(
                R.dimen.activity_vertical_margin);
        txtProtocolDesc.setPadding(horizontalPixels, verticalPixels, horizontalPixels, 0);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvProtocols.setVisibility(View.GONE);
        dataNotFound.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        rvProtocols.setVisibility(View.VISIBLE);
        dataNotFound.setVisibility(View.GONE);
    }

    @Override
    public void showProtocols(List<Protocol> protocols) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvProtocols.setLayoutManager(layoutManager);
        rvProtocols.addItemDecoration(new DividerItemDecorator(getActivity()));
        ProtocolAdapter adapter = new ProtocolAdapter(protocols, this);
        rvProtocols.setAdapter(adapter);
    }

    @Override
    public void showUnexpectedError() {
        dataNotFound.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.layout_protocols_not_found)
    public void refreshInfo() {
        Timber.d("Click on refresh info");
        protocolPresenter.update();
    }

    @Override
    public void onProtocolSelected(Protocol protocol) {
        Timber.d("Selected protocol %s", protocol.name);
        protocolPresenter.searchProtocolInfo(protocol);

        txtProtocolName.setText(protocol.name);
        txtProtocolDesc.setText(protocol.description);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}
