package com.skynet.psi.ui.detailBooking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.skynet.psi.R;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.Image;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.feedback.FeedbackActivity;
import com.skynet.psi.ui.views.ProgressDialogCustom;
import com.skynet.psi.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityBookingDetail extends BaseActivity implements DetailBookingContract.View {
    @BindView(R.id.imageView32)
    ImageView imageView32;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.imgService)
    ImageView imgService;
    @BindView(R.id.tvNameService)
    TextView tvNameService;
    @BindView(R.id.tvIdService)
    TextView tvIdService;
    @BindView(R.id.textView72)
    TextView textView72;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.rcvStep)
    RecyclerView rcvStep;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.textView75)
    TextView textView75;
    @BindView(R.id.avt2)
    CircleImageView avt2;
    @BindView(R.id.tvNameTech2)
    TextView tvNameTech2;
    @BindView(R.id.tvTech3)
    TextView tvTech3;
    @BindView(R.id.tvRate2)
    TextView tvRate2;
    @BindView(R.id.textView49)
    TextView textView49;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.rcvPhoto)
    RecyclerView rcvPhoto;
    @BindView(R.id.textView71)
    TextView textView71;
    @BindView(R.id.textView74)
    TextView textView74;
    @BindView(R.id.btnFeedback)
    Button btnFeedback;
    @BindView(R.id.layoutContentBooking)
    ConstraintLayout layoutContentBooking;

    private DetailBookingContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    private Booking booking;

    @Override
    protected int initLayout() {
        return R.layout.activity_detail_booking;
    }

    @Override
    protected void initVariables() {
        presenter = new DetailBookingPresenter(this);
        dialogLoading = new ProgressDialogCustom(this);
        presenter.getDetailBooking(getIntent().getIntExtra(AppConstant.MSG, 0));
    }

    @Override
    protected void initViews() {
        rcvStep.setLayoutManager(new LinearLayoutManager(this));
        rcvStep.setHasFixedSize(true);
        rcvPhoto.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvStep.setHasFixedSize(true);
        rcvStep.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnFeedback)
    public void onViewClicked() {
        Intent i  = new Intent(ActivityBookingDetail.this, FeedbackActivity.class);
        i.putExtra("id",booking.getBooking_id());
        startActivity(i);
    }

    @OnClick(R.id.imageView32)
    public void onViewBackClicked() {
        onBackPressed();
    }

    @Override
    public void onSucessGetDetailBooking(Booking booking) {
        this.booking = booking;
        tvTime.setText(booking.getDate_working() + " " + booking.getHour_working());
        tvNameService.setText(booking.getService_name());
        tvIdService.setText("#PSI" + booking.getBooking_id());
        if (booking.getService_image() != null && !booking.getService_image().isEmpty()) {
            Picasso.with(this).load(booking.getService_image()).fit().centerCrop().into(imgService);
        }
        tvPrice.setText(String.format("%,.0fvnđ", booking.getPrice()));
        rcvStep.setAdapter(new AdapterStep(booking.getService_detail(), this));

        if (booking.getActive() == 4) {
            tvStatus.setText("Đã hoàn thành");
            if (booking.getTech() != null && booking.getTech().getAvatar() != null && !booking.getTech().getAvatar().isEmpty()) {
                Picasso.with(this).load(booking.getTech().getAvatar()).fit().centerCrop().into(avt2);
                tvNameTech2.setText(booking.getTech().getName());
                tvRate2.setText(booking.getTech().getRating() + "");
            }
            tvAddress.setText(booking.getAddress());
            if (booking.getList_image() != null && !booking.getList_image().isEmpty())  {
                ArrayList<String> list = new ArrayList<>();
                for (Image i : booking.getList_image()){
                    list.add(i.getImg());
                }
                rcvPhoto.setAdapter(new AdapterPhoto(list, this));
            }
        } else if (booking.getActive() >= 5) {
            tvStatus.setText("Đã huỷ");
            layoutContentBooking.setVisibility(View.GONE);
            tvStatus.setBackgroundResource(R.drawable.ic_faill);
            tvStatus.setTextColor(Color.parseColor("#FF1313"));
        }
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void showProgress() {
        dialogLoading.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogLoading.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }
}
