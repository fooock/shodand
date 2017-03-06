package com.fooock.app.shodand.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

    @BindView(R.id.pb_loading_content)
    protected ProgressBar progressBar;

    @BindView(R.id.rv_protocols_data)
    protected RecyclerView rvProtocols;

    @BindView(R.id.layout_protocols_not_found)
    protected LinearLayout dataNotFound;

    private ProtocolPresenter protocolPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_protocol, container, false);
        ButterKnife.bind(this, view);
        protocolPresenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("Fragment view created");
        setTitle(R.string.title_protocols);

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
    }
}
