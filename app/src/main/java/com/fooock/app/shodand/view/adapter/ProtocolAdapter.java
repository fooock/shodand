package com.fooock.app.shodand.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fooock.app.shodand.R;
import com.fooock.app.shodand.view.ProtocolView;
import com.fooock.shodand.domain.model.Protocol;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ProtocolAdapter extends RecyclerView.Adapter<ProtocolAdapter.Holder> {

    private final ProtocolView.ProtocolClickListener listener;
    private final List<Protocol> protocols;

    public ProtocolAdapter(List<Protocol> protocols, ProtocolView.ProtocolClickListener listener) {
        this.protocols = protocols;
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_protocol, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Protocol protocol = protocols.get(position);
        holder.txtProtocolName.setText(protocol.name);
        holder.layoutBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProtocolSelected(protocol);
            }
        });
    }

    @Override
    public int getItemCount() {
        return protocols.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_protocol_row)
        LinearLayout layoutBase;

        @BindView(R.id.txt_protocol_name)
        TextView txtProtocolName;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
