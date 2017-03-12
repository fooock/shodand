package com.fooock.app.shodand.view.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.fooock.app.shodand.R;
import com.fooock.app.shodand.view.ListQueriesView;
import com.fooock.app.shodand.view.decorator.MarginItemDecorator;
import com.fooock.shodand.domain.model.ListQuery;
import com.fooock.shodand.domain.model.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 *
 */
public class ListQueryAdapter extends RecyclerView.Adapter<ListQueryAdapter.Holder> {

    private static final SimpleDateFormat FROM_DATE = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",
            Locale.ENGLISH);
    private static final SimpleDateFormat TO_DATE = new SimpleDateFormat("dd-MM-yyyy",
            Locale.ENGLISH);

    private final Resources resources;
    private final List<ListQuery> queries;
    private final ListQueriesView.SearchTagListener searchTagListener;
    private final ListQueriesView.ExecuteQueryListener executeQueryListener;

    public ListQueryAdapter(Resources resources, List<ListQuery> queries,
                            ListQueriesView.SearchTagListener searchTagListener,
                            ListQueriesView.ExecuteQueryListener executeQueryListener) {
        this.resources = resources;
        this.queries = queries;
        this.searchTagListener = searchTagListener;
        this.executeQueryListener = executeQueryListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list_queries, parent, false);
        return new Holder(view, searchTagListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final ListQuery query = queries.get(position);
        holder.txtTitle.setText(query.title);
        final int votes = query.votes;
        if (votes == 1) {
            holder.txtVotes.setText(String.format("%s %s",
                    votes, resources.getString(R.string.vote)));
        } else {
            holder.txtVotes.setText(String.format("%s %s",
                    votes, resources.getString(R.string.votes)));
        }
        try {
            Date date = FROM_DATE.parse(query.timestamp);
            holder.txtTimestamp.setText(TO_DATE.format(date));
        } catch (ParseException e) {
            Timber.e(e);
        }

        final String description = query.description;
        if (description == null || description.isEmpty()) {
            holder.txtDescription.setVisibility(View.GONE);
            holder.dividerVotes.setVisibility(View.GONE);
        } else {
            holder.dividerVotes.setVisibility(View.VISIBLE);
            holder.txtDescription.setVisibility(View.VISIBLE);
            holder.txtDescription.setText(description);
        }

        holder.txtQuery.setText(query.query);

        final List<Tag> tags = query.tags;
        if (tags == null || tags.isEmpty()) {
            holder.rvQueryTags.setVisibility(View.GONE);
            holder.dividerTags.setVisibility(View.GONE);
        } else {
            holder.setTags(tags);
            holder.dividerTags.setVisibility(View.VISIBLE);
            holder.rvQueryTags.setVisibility(View.VISIBLE);
        }

        holder.txtExecuteQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeQueryListener.onExecuteQuery(query);
            }
        });
    }

    @Override
    public int getItemCount() {
        return queries.size();
    }

    public void updateQueries(List<ListQuery> listQueries) {
        queries.addAll(listQueries);
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_list_query_title)
        TextView txtTitle;

        @BindView(R.id.txt_list_query_votes)
        TextView txtVotes;

        @BindView(R.id.txt_query_timestamp)
        TextView txtTimestamp;

        @BindView(R.id.txt_list_query_desc)
        TextView txtDescription;

        @BindView(R.id.txt_list_queries_query)
        TextView txtQuery;

        @BindView(R.id.view_divider_votes)
        View dividerVotes;

        @BindView(R.id.rv_query_tags)
        RecyclerView rvQueryTags;

        @BindView(R.id.view_divider_tags)
        View dividerTags;

        @BindView(R.id.txt_execute_query)
        TextView txtExecuteQuery;

        private final ListQueriesView.SearchTagListener searchTagListener;

        public Holder(View itemView, ListQueriesView.SearchTagListener searchTagListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.searchTagListener = searchTagListener;
            ChipsLayoutManager layoutManager = ChipsLayoutManager.newBuilder(itemView.getContext())
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_VIEW)
                    .setMaxViewsInRow(10)
                    .build();
            rvQueryTags.setLayoutManager(layoutManager);
            rvQueryTags.addItemDecoration(new MarginItemDecorator(itemView.getContext()));
        }

        void setTags(List<Tag> tags) {
            QueryTagsAdapter adapter = new QueryTagsAdapter(tags, searchTagListener);
            rvQueryTags.setAdapter(adapter);
        }
    }
}
