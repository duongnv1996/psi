package com.skynet.psi.ui.booking;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Product;
import com.skynet.psi.models.Service;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterService extends RecyclerView.Adapter<AdapterService.ViewHolder> {
    List<Service> list;
    Context context;
    ICallback iCallback;
    SparseBooleanArray cache;
    ViewHolder oldHoder;
    int oldPost;

    public AdapterService(List<Service> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
        this.cache = new SparseBooleanArray();
        for (int i = 0; i < list.size(); i++) {
            cache.put(i, list.get(i).isChecked());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.get(position).getImg() != null && !list.get(position).getImg().isEmpty()) {
            Picasso.with(context).load(list.get(position).getImg()).into(holder.imgPhoto);
        }
        holder.imgPhoto.setAlpha(cache.get(position) ? 1 : 0.2f);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
                holder.imgPhoto.setAlpha(1.f);
                list.get(position).setChecked(true);
                cache.put(position, true);
                if (oldHoder != null) {
                    oldHoder.imgPhoto.setAlpha(0.2f);
                    list.get(oldPost).setChecked(false);
                    cache.put(oldPost, false);
                }
                oldHoder = holder;
                oldPost = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgPhoto)
        ImageView imgPhoto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
