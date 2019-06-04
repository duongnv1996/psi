package com.skynet.psi.ui.listProduct;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    List<Product> list;
    Context context;
    CallBackProduct iCallback;
    SparseBooleanArray cache;


    public AdapterProduct(List<Product> list, Context context, CallBackProduct iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
        this.cache = new SparseBooleanArray();

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

    public interface CallBackProduct extends ICallback {
        void toggleFav(int pos, Product product, boolean toggle);
    }
}
