package com.fooock.app.shodand.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.model.ServicesType;
import com.fooock.app.shodand.view.ExploreView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
final class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.Holder> {

    private static final int TYPE_SEARCH_BY_IP = 0;
    private static final int TYPE_GET_SUMMARY_INFO = 1;
    private static final int TYPE_SEARCH_SERVICES = 2;

    private final List<ServicesType> servicesTypes;
    private final ExploreView.ServiceListener serviceListener;

    ServicesAdapter(List<ServicesType> servicesTypes, ExploreView.ServiceListener serviceListener) {
        this.servicesTypes = servicesTypes;
        this.serviceListener = serviceListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_services, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final ServicesType service = servicesTypes.get(position);
        holder.txtServiceName.setText(service.titleService);
        holder.imgServiceType.setImageResource(service.imgService);
        holder.layoutBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int adapterPosition = holder.getAdapterPosition();

                if (adapterPosition == TYPE_SEARCH_BY_IP) {
                    serviceListener.onSearchByIp();
                } else if (adapterPosition == TYPE_GET_SUMMARY_INFO) {
                    serviceListener.onSearchSummaryInfo();
                } else if (adapterPosition == TYPE_SEARCH_SERVICES) {
                    serviceListener.onSearchServices();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicesTypes.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_service_base)
        LinearLayout layoutBase;

        @BindView(R.id.img_service_type)
        ImageView imgServiceType;

        @BindView(R.id.txt_service_name)
        TextView txtServiceName;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
