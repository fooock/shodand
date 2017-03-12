package com.fooock.app.shodand.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.view.ListQueriesView;
import com.fooock.shodand.domain.model.Tag;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
final class QueryTagsAdapter extends RecyclerView.Adapter<QueryTagsAdapter.Holder> {

    private final List<Tag> tags;
    private final ListQueriesView.SearchTagListener searchTagListener;

    QueryTagsAdapter(List<Tag> tags, ListQueriesView.SearchTagListener searchTagListener) {
        this.tags = tags;
        this.searchTagListener = searchTagListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_query_tags, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Tag tag = tags.get(position);
        holder.txtQueryTag.setText(tag.name);
        holder.txtQueryTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTagListener.onSearchTag(tag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_query_tag_name)
        TextView txtQueryTag;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
