package com.fooock.app.shodand.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.presenter.ListQueriesPresenter;
import com.fooock.app.shodand.view.ListQueriesView;
import com.fooock.app.shodand.view.adapter.ListQueryAdapter;
import com.fooock.app.shodand.view.listener.EndlessScrollListener;
import com.fooock.shodand.domain.model.ListQuery;
import com.fooock.shodand.domain.model.Tag;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 *
 */
public class ListQueriesFragment extends BaseFragment implements ListQueriesView,
        ListQueriesView.ExecuteQueryListener, ListQueriesView.SearchTagListener {

    @BindView(R.id.pb_loading_content)
    protected ProgressBar progressBar;

    @BindView(R.id.rv_list_queries_data)
    protected RecyclerView rvListQueries;

    @BindView(R.id.layout_list_queries_not_found)
    protected RelativeLayout layoutDataNotFound;

    private ListQueriesPresenter listQueriesPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_queries, container, false);
        ButterKnife.bind(this, view);
        listQueriesPresenter.attachView(this);
        rvListQueries.addOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onUpdateMore(int page) {
                Timber.d("Update elements for page %s", page);
                listQueriesPresenter.updateNextPage(page);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.title_list_queries);

        listQueriesPresenter.update();
    }

    @Override
    public void onDestroyView() {
        listQueriesPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    void initializeComponents(ShodandApplication application) {
        Timber.d("Initializing components...");
        listQueriesPresenter = new ListQueriesPresenter(application.shodanRepository(),
                application.mainThread(), application.threadExecutor());
    }

    @OnClick(R.id.layout_list_queries_not_found)
    public void refreshInfo() {
        Timber.d("Click to refresh info...");
        listQueriesPresenter.update();
    }

    @Override
    public void addTagsToList(List<ListQuery> listQueries) {
        Timber.d("Update queries with %s new queries...");
        ListQueryAdapter adapter = (ListQueryAdapter) rvListQueries.getAdapter();
        adapter.updateQueries(listQueries);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvListQueries.setVisibility(View.GONE);
        layoutDataNotFound.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        rvListQueries.setVisibility(View.VISIBLE);
        layoutDataNotFound.setVisibility(View.GONE);
    }

    @Override
    public void showUnexpectedError() {
        progressBar.setVisibility(View.GONE);
        rvListQueries.setVisibility(View.GONE);
        layoutDataNotFound.setVisibility(View.VISIBLE);
    }

    @Override
    public void showQueries(List<ListQuery> queries) {
        Timber.d("Show %s queries", queries.size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvListQueries.setLayoutManager(layoutManager);
        ListQueryAdapter adapter = new ListQueryAdapter(getResources(), queries, this, this);
        rvListQueries.setAdapter(adapter);
    }

    @Override
    public void onExecuteQuery(ListQuery query) {
        Timber.d("Execute query: %s", query.query);
    }

    @Override
    public void onSearchTag(Tag tag) {
        Timber.d("Search tag %s", tag.name);
    }
}
