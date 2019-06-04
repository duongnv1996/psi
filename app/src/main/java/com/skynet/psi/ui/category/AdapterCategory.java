package com.skynet.psi.ui.category;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.Viewholder> {
    List<Category> list;
    Context context;
    ICallback iCallback;

    SparseBooleanArray sparseBooleanArray;

    public AdapterCategory(List<Category> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;

        this.iCallback = iCallback;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if (list.get(position).getImg() != null && !list.get(position).getImg().isEmpty()) {
            Picasso.with(context).load(list.get(position).getImg()).fit().centerInside().into(holder.imgShop);
        }
        holder.tvNameShop.setText(list.get(position).getName());
        holder.tvContent.setText(list.get(position).getContent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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

    class Viewholder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgShop)
        ImageView imgShop;
        @BindView(R.id.tvNameShop)
        TextView tvNameShop;
        @BindView(R.id.tvContent)
        TextView tvContent;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
