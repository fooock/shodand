package com.fooock.app.shodand.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.view.ExploreView;
import com.fooock.shodand.domain.model.TagCount;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
final class PopularTagAdapter extends RecyclerView.Adapter<PopularTagAdapter.Holder> {

    private final List<TagCount> tags;
    private final ExploreView.TagListener tagListener;

    PopularTagAdapter(List<TagCount> tags, ExploreView.TagListener tagListener) {
        this.tags = tags;
        this.tagListener = tagListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_popular_tags, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final TagCount tag = tags.get(position);
        holder.txtTagCount.setText(String.valueOf(tag.getCount()));
        holder.txtTagName.setText(tag.getName());
        holder.baseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagListener.onTagSelected(tag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_tags)
        RelativeLayout baseLayout;

        @BindView(R.id.txt_tag_name)
        TextView txtTagName;

        @BindView(R.id.txt_tag_count)
        TextView txtTagCount;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
