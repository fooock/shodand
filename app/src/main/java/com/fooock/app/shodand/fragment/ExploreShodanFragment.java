package com.fooock.app.shodand.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fooock.app.shodand.Prefs;
import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.presenter.ExploreShodanPresenter;
import com.fooock.app.shodand.view.ExploreView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class ExploreShodanFragment extends BaseFragment implements ExploreView {

    @BindView(R.id.pb_loading_content)
    protected ProgressBar progressBar;

    private ExploreShodanPresenter exploreShodanPresenter;
    private Prefs prefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_shodan, container, false);
        ButterKnife.bind(this, view);
        exploreShodanPresenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("Fragment view created");

        setTitle(R.string.title_explore);

        exploreShodanPresenter.update(prefs.getApiKey());
    }

    @Override
    public void onDestroy() {
        exploreShodanPresenter.detachView();
        super.onDestroy();
    }

    @Override
    void initializeComponents(ShodandApplication application) {
        Timber.d("Initializing components...");
        prefs = application.preferences();
        exploreShodanPresenter = new ExploreShodanPresenter(
                application.mainThread(), application.threadExecutor());
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
