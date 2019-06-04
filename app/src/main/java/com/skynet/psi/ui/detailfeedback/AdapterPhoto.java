package com.skynet.psi.ui.detailfeedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Feedback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterPhoto extends RecyclerView.Adapter<AdapterPhoto.ViewHolder> {
    List<Feedback.List_imageEntity> list;
    Context context;
    ICallback iCallback;


    public AdapterPhoto(List<Feedback.List_imageEntity> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feedback.List_imageEntity item = list.get(position);
        if (item.getImg() != null && !item.getImg().isEmpty()) {
            Picasso.with(context).load(item.getImg()).fit().centerCrop().into(holder.imgShop);
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgShop)
        ImageView imgShop;
        @BindView(R.id.card)
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
