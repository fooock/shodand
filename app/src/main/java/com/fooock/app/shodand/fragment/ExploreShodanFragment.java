package com.fooock.app.shodand.fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.ShodandApplication;
import com.fooock.app.shodand.model.DnsRow;
import com.fooock.app.shodand.model.DnsType;
import com.fooock.app.shodand.model.PopularTagRow;
import com.fooock.app.shodand.model.ProtocolRow;
import com.fooock.app.shodand.model.QueriesRow;
import com.fooock.app.shodand.model.QueryType;
import com.fooock.app.shodand.model.Row;
import com.fooock.app.shodand.model.ServicesRow;
import com.fooock.app.shodand.model.ServicesType;
import com.fooock.app.shodand.presenter.ExploreShodanPresenter;
import com.fooock.app.shodand.view.ExploreView;
import com.fooock.app.shodand.view.adapter.ExploreDataAdapter;
import com.fooock.shodand.domain.model.TagCount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class ExploreShodanFragment extends BaseFragment implements ExploreView,
        ExploreView.QueryListener, ExploreView.TagListener, ExploreView.ServiceListener,
        ExploreView.ProtocolListener, ExploreView.DnsListener {

    @BindView(R.id.pb_loading_content)
    protected ProgressBar progressBar;

    @BindView(R.id.rv_explore_data)
    protected RecyclerView recyclerView;

    private ExploreShodanPresenter exploreShodanPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_shodan, container, false);
        ButterKnife.bind(this, view);
        exploreShodanPresenter.attachView(this);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.d("Fragment view created");

        setTitle(R.string.title_explore);

        exploreShodanPresenter.update();
    }

    @Override
    public void onDestroy() {
        exploreShodanPresenter.detachView();
        super.onDestroy();
    }

    @Override
    void initializeComponents(ShodandApplication application) {
        Timber.d("Initializing components...");
        exploreShodanPresenter = new ExploreShodanPresenter(
                application.shodanRepository(),
                application.mainThread(),
                application.threadExecutor());
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPopularTags(List<TagCount> tags) {
        Timber.d("Show %s popular tags", tags.size());

        final List<Row> rows = new ArrayList<>();
        rows.add(createQueriesRow());
        rows.add(createPopularTagRow(tags));
        rows.add(createServicesRow());
        rows.add(createProtocolRow());
        rows.add(createDnsRow());

        final ExploreDataAdapter dataAdapter = new ExploreDataAdapter(
                rows, this, this, this, this, this);
        recyclerView.setAdapter(dataAdapter);
    }

    /**
     * Create the row to sho the dns card
     *
     * @return dns row
     */
    private DnsRow createDnsRow() {
        final DnsType resolve = new DnsType(R.drawable.ic_resolve, R.string.resolve_ip_address);
        final DnsType reverse = new DnsType(R.drawable.ic_reverse, R.string.reverse_dns);
        return new DnsRow(Arrays.asList(resolve, reverse));
    }

    /**
     * Create the row to show the protocol card
     *
     * @return protocol row
     */
    private ProtocolRow createProtocolRow() {
        return new ProtocolRow();
    }

    /**
     * Create the row to show the queries card
     *
     * @return query row
     */
    private QueriesRow createQueriesRow() {
        final QueryType queryType = new QueryType(R.drawable.ic_list_queries, R.string.title_list_queries);
        final QueryType searchQueries = new QueryType(R.drawable.ic_search_queries, R.string.title_search_queries);
        return new QueriesRow(Arrays.asList(queryType, searchQueries));
    }

    /**
     * Create the row to show the popular tags
     *
     * @param tags list of popular tags
     * @return popular tag row
     */
    private PopularTagRow createPopularTagRow(List<TagCount> tags) {
        return new PopularTagRow(tags);
    }

    /**
     * Create the row to show the services card
     *
     * @return service row
     */
    private ServicesRow createServicesRow() {
        final ServicesType byIp = new ServicesType(R.drawable.ic_search, R.string.title_service_by_ip);
        final ServicesType summaryInfo = new ServicesType(R.drawable.ic_info, R.string.title_service_summary_info);
        final ServicesType searchServices = new ServicesType(R.drawable.ic_services, R.string.title_search_service);
        return new ServicesRow(Arrays.asList(byIp, summaryInfo, searchServices));
    }

    @Override
    public void showUnexpectedError() {
        View view = getView();
        if (view == null) {
            return;
        }
        Snackbar.make(view, R.string.show_unexpected_error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onQueryListSelected() {
        Timber.d("Selected list queries");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ListQueriesFragment fragment = new ListQueriesFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onQuerySearchSelected() {
        Timber.d("Selected query search");
    }

    @Override
    public void onTagSelected(@NonNull TagCount tag) {
        Timber.d("Selected tag %s", tag.getName());
    }

    @Override
    public void onShowMoreTags() {
        Timber.d("Show more tags...");
    }

    @Override
    public void onSearchByIp() {
        Timber.d("Selected search by ip");
    }

    @Override
    public void onSearchSummaryInfo() {
        Timber.d("Selected get summary info");
    }

    @Override
    public void onSearchServices() {
        Timber.d("Selected search services");
    }

    @Override
    public void onProtocolSelected() {
        Timber.d("Selected show protocols");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ProtocolFragment fragment = new ProtocolFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResolveDnsSelected() {
        Timber.d("Resolve dns selected");
    }

    @Override
    public void onReverseDnsSelected() {
        Timber.d("Reverse dns selected");
    }
}
