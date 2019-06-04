package com.skynet.psi.ui.history;

import android.content.Context;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Booking;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    List<Booking> list;
    Context context;
    ICallback iCallback;
    SparseBooleanArray cacheAlreadyBook;
    SparseBooleanArray cacheWaiting;
    SparseBooleanArray cacheDone;
    SparseBooleanArray cacheCancle;


    public AdapterProduct(List<Booking> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
        this.cacheAlreadyBook = new SparseBooleanArray();
        this.cacheWaiting = new SparseBooleanArray();
        this.cacheDone = new SparseBooleanArray();
        this.cacheCancle = new SparseBooleanArray();
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getActive()) {
                case 1: {
                    list.get(i).setActiveString("Chờ xác nhận");
                    cacheAlreadyBook.put(i, true);
                    break;
                }
                case 2: {
                    list.get(i).setActiveString("Kỹ thuật đã nhận");
                    cacheWaiting.put(i, true);

                    break;
                }
                case 3: {
                    list.get(i).setActiveString("Đang thực hiện");
                    cacheDone.put(i, true);
                    break;
                }
                case 4: {
                    list.get(i).setActiveString("Hoàn thành");
                    cacheCancle.put(i, true);
                    break;
                }
                case 5: {
                    list.get(i).setActiveString("Khách hàng huỷ");
                    cacheCancle.put(i, true);
                    break;
                }  case 6: {
                    list.get(i).setActiveString("Kỹ thuật huỷ");
                    cacheCancle.put(i, true);
                    break;
                }case 7: {
                    list.get(i).setActiveString("Hệ thống huỷ");
                    cacheCancle.put(i, true);
                    break;
                }
            }
            switch (list.get(i).getRepeat_type()) {
                case 0: {
                    list.get(i).setRepeatString("");
                    break;
                }
                case 1: {
                    list.get(i).setRepeatString(" Hàng ngày");
                    break;
                }
                case 2: {
                    list.get(i).setRepeatString("Hàng tuần");
                    break;
                }
                case 3: {
                    list.get(i).setRepeatString("Hàng tháng");
                    break;
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
        if(list.get(position).getService_image() !=  null && !list.get(position).getService_image().isEmpty()){
            Picasso.with(context).load(list.get(position).getService_image()).fit().centerCrop().into(holder.imageView29);
        }
        holder.tvTime.setText(list.get(position).getService_name());
        holder.tvAddress2.setText(Html.fromHtml(String.format(context.getString(R.string.format_price_s),list.get(position).getPrice())));
        holder.textView60.setText(list.get(position).getDate_working());
        holder.tvStatus.setText(list.get(position).getActiveString());
        if (cacheAlreadyBook.get(position)) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.blue));
        } else if (cacheWaiting.get(position)) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.orage));
        } else if (cacheDone.get(position)) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
        } else if (cacheCancle.get(position)) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.blue));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearCache() {
        for (int i = 0; i < list.size(); i++) {
            switch (list.get(i).getActive()) {
                case 1: {
                    list.get(i).setActiveString("Chờ xác nhận");
                    cacheAlreadyBook.put(i, true);
                    break;
                }
                case 2: {
                    list.get(i).setActiveString("Kỹ thuật đã nhận");
                    cacheWaiting.put(i, true);

                    break;
                }
                case 3: {
                    list.get(i).setActiveString("Đang thực hiện");
                    cacheDone.put(i, true);
                    break;
                }
                case 4: {
                    list.get(i).setActiveString("Hoàn thành");
                    cacheCancle.put(i, true);
                    break;
                }
                case 5: {
                    list.get(i).setActiveString("Khách hàng huỷ");
                    cacheCancle.put(i, true);
                    break;
                }  case 6: {
                    list.get(i).setActiveString("Kỹ thuật huỷ");
                    cacheCancle.put(i, true);
                    break;
                }case 7: {
                    list.get(i).setActiveString("Hệ thống huỷ");
                    cacheCancle.put(i, true);
                    break;
                }
            }
            switch (list.get(i).getRepeat_type()) {
                case 0: {
                    list.get(i).setRepeatString("");
                    break;
                }
                case 1: {
                    list.get(i).setRepeatString(" Hàng ngày");
                    break;
                }
                case 2: {
                    list.get(i).setRepeatString("Hàng tuần");
                    break;
                }
                case 3: {
                    list.get(i).setRepeatString("Hàng tháng");
                    break;
                }
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvStatus)
        TextView tvStatus;
        @BindView(R.id.textView60)
        TextView textView60;
        @BindView(R.id.textView63)
        TextView tvTime;
        @BindView(R.id.tvAddress2)
        TextView tvAddress2;    @BindView(R.id.imageView29)
        ImageView imageView29;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
