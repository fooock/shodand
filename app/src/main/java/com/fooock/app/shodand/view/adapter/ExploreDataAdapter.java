package com.fooock.app.shodand.view.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.model.DnsRow;
import com.fooock.app.shodand.model.DnsType;
import com.fooock.app.shodand.model.PopularTagRow;
import com.fooock.app.shodand.model.ProtocolRow;
import com.fooock.app.shodand.model.QueriesRow;
import com.fooock.app.shodand.model.QueryType;
import com.fooock.app.shodand.model.Row;
import com.fooock.app.shodand.model.ServicesRow;
import com.fooock.app.shodand.model.ServicesType;
import com.fooock.app.shodand.view.ExploreView;
import com.fooock.app.shodand.view.decorator.DividerItemDecorator;
import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class ExploreDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_POPULAR_TAGS = 0;
    private static final int VIEW_TYPE_QUERIES = 1;
    private static final int VIEW_TYPE_SERVICES = 2;
    private static final int VIEW_TYPE_PROTOCOLS = 3;
    private static final int VIEW_TYPE_DNS = 4;

    private final List<Row> rowList;

    private final ExploreView.QueryListener queryListener;
    private final ExploreView.TagListener tagListener;
    private final ExploreView.ServiceListener serviceListener;
    private final ExploreView.ProtocolListener protocolListener;
    private final ExploreView.DnsListener dnsListener;

    /**
     * Create this adapter with the given list of rows
     *
     * @param rows list of rows for this adapter
     */
    public ExploreDataAdapter(List<Row> rows, ExploreView.QueryListener queryListener,
                              ExploreView.TagListener tagListener,
                              ExploreView.ServiceListener serviceListener,
                              ExploreView.ProtocolListener protocolListener,
                              ExploreView.DnsListener dnsListener) {
        this.rowList = rows;
        this.queryListener = queryListener;
        this.tagListener = tagListener;
        this.serviceListener = serviceListener;
        this.protocolListener = protocolListener;
        this.dnsListener = dnsListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_POPULAR_TAGS) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_explore_tags, parent, false);
            viewHolder = new TagHolder(view, tagListener);

        } else if (viewType == VIEW_TYPE_QUERIES) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_explore_queries, parent, false);
            viewHolder = new QueriesHolder(view, queryListener);

        } else if (viewType == VIEW_TYPE_SERVICES) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_explore_services, parent, false);
            viewHolder = new ServicesHolder(view, serviceListener);

        } else if (viewType == VIEW_TYPE_PROTOCOLS) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_explore_protocols, parent, false);
            viewHolder = new ProtocolHolder(view, protocolListener);

        } else if (viewType == VIEW_TYPE_DNS) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_explore_dns, parent, false);
            viewHolder = new DnsHolder(view, dnsListener);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);

        if (viewType == VIEW_TYPE_POPULAR_TAGS) {
            PopularTagRow row = (PopularTagRow) rowList.get(position);
            TagHolder tagHolder = (TagHolder) holder;
            tagHolder.txtTitleTag.setText(row.getName());
            tagHolder.txtTagsDescription.setText(row.getDescription());
            tagHolder.updatePopularTags(row.data());

        } else if (viewType == VIEW_TYPE_QUERIES) {
            QueriesRow row = (QueriesRow) rowList.get(position);
            QueriesHolder sharedHolder = (QueriesHolder) holder;
            sharedHolder.txtTitleQueries.setText(row.getName());
            sharedHolder.txtDescriptionQueries.setText(row.getDescription());
            sharedHolder.setQueryTypes(row.data());

        } else if (viewType == VIEW_TYPE_SERVICES) {
            ServicesRow row = (ServicesRow) rowList.get(position);
            ServicesHolder servicesHolder = (ServicesHolder) holder;
            servicesHolder.txtTitleServices.setText(row.getName());
            servicesHolder.txtDescriptionServices.setText(row.getDescription());
            servicesHolder.setServicesTypes(row.data());

        } else if (viewType == VIEW_TYPE_PROTOCOLS) {
            ProtocolRow row = (ProtocolRow) rowList.get(position);
            ProtocolHolder protocolHolder = (ProtocolHolder) holder;
            protocolHolder.txtTitleProtocols.setText(row.getName());
            protocolHolder.txtDescriptionProtocols.setText(row.getDescription());

        } else if (viewType == VIEW_TYPE_DNS) {
            DnsRow row = (DnsRow) rowList.get(position);
            DnsHolder dnsHolder = (DnsHolder) holder;
            dnsHolder.txtTitleDns.setText(row.getName());
            dnsHolder.txtDescriptionDns.setText(row.getDescription());
            dnsHolder.setDnsTypes(row.data());
        }
    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_QUERIES;
        } else if (position == 1) {
            return VIEW_TYPE_POPULAR_TAGS;
        } else if (position == 2) {
            return VIEW_TYPE_SERVICES;
        } else if (position == 3) {
            return VIEW_TYPE_PROTOCOLS;
        }
        return VIEW_TYPE_DNS;
    }

    /**
     * View holder for the dns
     */
    static class DnsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title_dns)
        TextView txtTitleDns;

        @BindView(R.id.txt_description_dns)
        TextView txtDescriptionDns;

        @BindView(R.id.rv_dns_types)
        RecyclerView rvDnsTypes;

        private final ExploreView.DnsListener dnsListener;

        DnsHolder(View itemView, ExploreView.DnsListener dnsListener) {
            super(itemView);
            this.dnsListener = dnsListener;
            ButterKnife.bind(this, itemView);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext());
            rvDnsTypes.setLayoutManager(layoutManager);
            rvDnsTypes.setHasFixedSize(true);
            rvDnsTypes.addItemDecoration(new DividerItemDecorator(itemView.getContext()));
        }

        void setDnsTypes(List<DnsType> dnsTypes) {
            DnsAdapter adapter = new DnsAdapter(dnsTypes, dnsListener);
            rvDnsTypes.setAdapter(adapter);
        }
    }

    /**
     * View holder for the protocols
     */
    static class ProtocolHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_protocols_title)
        TextView txtTitleProtocols;

        @BindView(R.id.txt_protocols_description)
        TextView txtDescriptionProtocols;

        private final ExploreView.ProtocolListener protocolListener;

        ProtocolHolder(View itemView, ExploreView.ProtocolListener protocolListener) {
            super(itemView);
            this.protocolListener = protocolListener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.layout_protocols)
        void clickOnProtocols() {
            protocolListener.onProtocolSelected();
        }
    }

    /**
     * View holder for services
     */
    static class ServicesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title_services)
        TextView txtTitleServices;

        @BindView(R.id.txt_description_services)
        TextView txtDescriptionServices;

        @BindView(R.id.rv_service_types)
        RecyclerView rvServiceTypes;

        private final ExploreView.ServiceListener serviceListener;

        ServicesHolder(View itemView, ExploreView.ServiceListener serviceListener) {
            super(itemView);
            this.serviceListener = serviceListener;
            ButterKnife.bind(this, itemView);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext());
            rvServiceTypes.setLayoutManager(layoutManager);
            rvServiceTypes.setHasFixedSize(true);
            rvServiceTypes.addItemDecoration(new DividerItemDecorator(itemView.getContext()));
        }

        void setServicesTypes(List<ServicesType> servicesTypes) {
            final ServicesAdapter adapter = new ServicesAdapter(servicesTypes, serviceListener);
            rvServiceTypes.setAdapter(adapter);
        }
    }

    /**
     * View holder for queries
     */
    static class QueriesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_title_redently_shared)
        TextView txtTitleQueries;

        @BindView(R.id.txt_description_recently_shared)
        TextView txtDescriptionQueries;

        @BindView(R.id.rv_query_types)
        RecyclerView rvQueryTypes;

        private final ExploreView.QueryListener queryListener;

        QueriesHolder(View itemView, ExploreView.QueryListener queryListener) {
            super(itemView);
            this.queryListener = queryListener;
            ButterKnife.bind(this, itemView);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext());
            rvQueryTypes.setLayoutManager(layoutManager);
            rvQueryTypes.setHasFixedSize(true);
            rvQueryTypes.addItemDecoration(new DividerItemDecorator(itemView.getContext()));
        }

        void setQueryTypes(List<QueryType> queryTypes) {
            final QueriesAdapter adapter = new QueriesAdapter(queryTypes, queryListener);
            rvQueryTypes.setAdapter(adapter);
        }
    }

    /**
     * View holder for popular tags
     */
    static class TagHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_popular_tags)
        TextView txtTitleTag;

        @BindView(R.id.txt_popular_tags_description)
        TextView txtTagsDescription;

        @BindView(R.id.txt_data_not_found)
        TextView txtTagsNotFound;

        @BindView(R.id.rv_popular_tags)
        RecyclerView rvPopularTags;

        private final ExploreView.TagListener tagListener;

        TagHolder(View itemView, ExploreView.TagListener tagListener) {
            super(itemView);
            this.tagListener = tagListener;
            ButterKnife.bind(this, itemView);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext());
            rvPopularTags.setLayoutManager(layoutManager);
            rvPopularTags.addItemDecoration(new DividerItemDecorator(itemView.getContext()));
        }

        /**
         * Fill this view with the popular tag data
         *
         * @param tags list of tags to show
         */
        void updatePopularTags(List<TagCount> tags) {
            if (tags == null || tags.isEmpty()) {
                txtTagsNotFound.setVisibility(View.VISIBLE);
                rvPopularTags.setVisibility(View.GONE);
                return;
            }
            txtTagsNotFound.setVisibility(View.GONE);
            rvPopularTags.setVisibility(View.VISIBLE);

            final PopularTagAdapter popularTagAdapter = new PopularTagAdapter(tags, tagListener);
            rvPopularTags.setAdapter(popularTagAdapter);
        }
    }
}
