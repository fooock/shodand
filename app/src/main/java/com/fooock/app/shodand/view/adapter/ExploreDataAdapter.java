package com.fooock.app.shodand.view.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.model.PopularTagRow;
import com.fooock.app.shodand.model.Row;
import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ExploreDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_POPULAR_TAGS = 0;

    private final List<Row> rowList;

    /**
     * Create this adapter with the given list of rows
     *
     * @param rows list of rows for this adapter
     */
    public ExploreDataAdapter(List<Row> rows) {
        rowList = rows;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_POPULAR_TAGS) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_explore_tags, parent, false);
            viewHolder = new TagHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_POPULAR_TAGS) {
            PopularTagRow row = (PopularTagRow) rowList.get(position);
            TagHolder tagHolder = (TagHolder) holder;
            tagHolder.updatePopularTags(row.data());
        }
    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE_POPULAR_TAGS;
    }

    /**
     * View holder for popular tags
     */
    static class TagHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_popular_tags)
        TextView txtTagName;

        @BindView(R.id.txt_data_not_found)
        TextView txtTagsNotFound;

        @BindView(R.id.rv_popular_tags)
        RecyclerView rvPopularTags;

        TagHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(
                    itemView.getContext());
            rvPopularTags.setLayoutManager(layoutManager);
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

            PopularTagAdapter popularTagAdapter = new PopularTagAdapter(tags);
            rvPopularTags.setAdapter(popularTagAdapter);
        }
    }
}
