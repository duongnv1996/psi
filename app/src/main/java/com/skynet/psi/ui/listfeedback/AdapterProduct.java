package com.skynet.psi.ui.listfeedback;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Feedback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    List<Feedback> list;
    Context context;
    ICallback iCallback;
    SparseBooleanArray cacheWaiting;
    SparseBooleanArray cacheDone;


    public AdapterProduct(List<Feedback> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
        this.cacheWaiting = new SparseBooleanArray();
        this.cacheDone = new SparseBooleanArray();
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getActive()) {
                case 1: {
                    list.get(i).setActiveString("Chờ phản hồi");
                    cacheWaiting.put(i, true);

                    break;
                }
                case 2: {
                    list.get(i).setActiveString("Đã giải quyết");
                    cacheDone.put(i, true);
                    break;

                }
                default:{
                    list.get(i).setActiveString("Chờ phản hồi");
                    cacheWaiting.put(i, true);

                    break;
                }

            }

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
        holder.tvTime.setText("#" + list.get(position).getBooking_id());
        holder.circleImageView4.setText(list.get(position).getTime_report());
        holder.tvAddress.setText(list.get(position).getAddress());
        holder.tvStatus.setText(list.get(position).getActiveString());
        if (cacheWaiting.get(position)) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.orage));
        } else if (cacheDone.get(position)) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.circleImageView4)
        TextView circleImageView4;
        @BindView(R.id.textView60)
        TextView textView60;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvAddress)
        TextView tvAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
