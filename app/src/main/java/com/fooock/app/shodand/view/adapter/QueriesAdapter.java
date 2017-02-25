package com.fooock.app.shodand.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.model.QueryType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
final class QueriesAdapter extends RecyclerView.Adapter<QueriesAdapter.Holder> {

    private final List<QueryType> queryTypes;

    QueriesAdapter(List<QueryType> queryTypes) {
        this.queryTypes = queryTypes;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_query_type, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        QueryType queryType = queryTypes.get(position);
        holder.imgQueryType.setImageResource(queryType.image);
        holder.txtQueryTypeTitle.setText(queryType.name);
    }

    @Override
    public int getItemCount() {
        return queryTypes.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_query_type)
        ImageView imgQueryType;

        @BindView(R.id.txt_query_type_title)
        TextView txtQueryTypeTitle;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
