package com.fooock.app.shodand.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.model.DnsType;
import com.fooock.app.shodand.view.ExploreView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
final class DnsAdapter extends RecyclerView.Adapter<DnsAdapter.Holder> {

    private static final int VIEW_TYPE_RESOLVE_DNS = 0;
    private static final int VIEW_TYPE_REVERSE_DNS = 1;

    private final List<DnsType> dnsTypes;
    private final ExploreView.DnsListener dnsListener;

    DnsAdapter(List<DnsType> dnsTypes, ExploreView.DnsListener dnsListener) {
        this.dnsTypes = dnsTypes;
        this.dnsListener = dnsListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dns, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        DnsType dnsType = dnsTypes.get(position);
        holder.txtDnsName.setText(dnsType.name);
        holder.imgDns.setImageResource(dnsType.image);
        holder.layoutBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int adapterPosition = holder.getAdapterPosition();

                if (adapterPosition == VIEW_TYPE_RESOLVE_DNS) {
                    dnsListener.onResolveDnsSelected();
                } else if (adapterPosition == VIEW_TYPE_REVERSE_DNS) {
                    dnsListener.onReverseDnsSelected();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dnsTypes.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_dns)
        LinearLayout layoutBase;

        @BindView(R.id.img_dns_image)
        ImageView imgDns;

        @BindView(R.id.txt_dns_name)
        TextView txtDnsName;

        Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
