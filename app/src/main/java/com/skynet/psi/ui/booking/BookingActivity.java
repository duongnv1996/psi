package com.skynet.psi.ui.booking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.R;
import com.skynet.psi.application.AppController;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.Promotion;
import com.skynet.psi.models.Service;
import com.skynet.psi.models.Step;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.connect.ConnectActivity;
import com.skynet.psi.ui.payment.PaymentActivity;
import com.skynet.psi.ui.promotion.PromotionActivity;
import com.skynet.psi.ui.views.AlertDialogCustom;
import com.skynet.psi.ui.views.DialogSucess;
import com.skynet.psi.ui.views.ProgressDialogCustom;
import com.skynet.psi.utils.AppConstant;
import com.skynet.psi.utils.DateTimeUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookingActivity extends BaseActivity implements BookingContract.View, ICallback {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.textView54)
    TextView textView54;
    @BindView(R.id.rcvService)
    RecyclerView rcvService;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvprice)
    TextView tvprice;
    @BindView(R.id.rcvStep)
    RecyclerView rcvStep;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.textView56)
    TextView textView56;
    @BindView(R.id.textView58)
    TextView textView58;
    @BindView(R.id.layoutTime)
    ConstraintLayout layoutTime;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.textView57)
    TextView textView57;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.layoutDate)
    ConstraintLayout layoutDate;
    @BindView(R.id.constraintLayout14)
    ConstraintLayout constraintLayout14;
    @BindView(R.id.textView61)
    TextView tvPromotion;
    @BindView(R.id.textView62)
    TextView textView62;
    @BindView(R.id.note)
    EditText note;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.spinner)
    Spinner spinner;
    DatePickerDialog datePickerDialog;
    private AdapterService adapterService;
    private List<Service> listService;
    Calendar date;
    private BookingContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    private Service serviceSeleted;
    private MyPlace place;
    private Promotion promotion;
    Booking booking = new Booking();
    private AdapterStep stepAdapter;
    List<Step> listStep = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_booking;
    }

    @Override
    protected void initVariables() {
        date = Calendar.getInstance();
        stepAdapter = new AdapterStep(listStep, this);
        rcvStep.setAdapter(stepAdapter);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DATE, dayOfMonth);
                tvDate.setText(DateTimeUtil.convertTimeToString(date.getTimeInMillis(), "dd/MM/yyyy"));
                onViewClicked();
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        tvDate.setText(DateTimeUtil.convertTimeToString(date.getTimeInMillis(), "dd/MM/yyyy"));
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new BookingPresenter(this);
        presenter.getListProduct(getIntent().getIntExtra(AppConstant.MSG, 1));
        if (AppController.getInstance().getmSetting().getFloat(AppConstant.LAT, 0) != 0) {
            LatLng latLng = new LatLng(AppController.getInstance().getmSetting().getFloat(AppConstant.LAT, 0),
                    AppController.getInstance().getmSetting().getFloat(AppConstant.LNG, 0));
            if(AppController.getInstance().getFranchiseName()!=null){
                place = new MyPlace();
                place.setLng(latLng.latitude);
                place.setLat(latLng.latitude);
                place.setDescription(AppController.getInstance().getFranchiseName());
                place.setName(AppController.getInstance().getFranchiseName());
                tvAddress.setText(place.getDescription());
            }else
            presenter.getAddressFromLatlng(latLng, new Geocoder(this, Locale.getDefault()));
        }
    }

    @Override
    protected void initViews() {
        rcvService.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvService.setHasFixedSize(true);
        rcvStep.setLayoutManager(new LinearLayoutManager(this));
        rcvStep.setHasFixedSize(true);

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

    @OnClick({R.id.imgBack, R.id.tvAddress, R.id.layoutDate, R.id.btnSubmit, R.id.textView61})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.tvAddress:
                break;
            case R.id.layoutDate:
                datePickerDialog.show();
                break;
            case R.id.btnSubmit:
                if (place != null) {
                    booking.setAddress(place.getDescription());
                    booking.setLat(place.getLat());
                    booking.setLng(place.getLng());
                }
                booking.setUserId(AppController.getInstance().getmProfileUser().getId());
                if (serviceSeleted != null)
                    booking.setService_id(serviceSeleted.getId());
                else {
                    showToast("Vui lòng chọn loại dịch vụ!", AppConstant.NEGATIVE);
                    return;
                }
                booking.setLocationID(0);
                if (promotion != null) {
                    booking.setIdPromotion(promotion.getId());
                    double priceCoupon = promotion.getValue() * serviceSeleted.getPrice() / 100;
                    tvprice.setText(String.format("%,.0fđ", serviceSeleted.getPrice() - priceCoupon));
                } else {
                    booking.setIdPromotion(0);

                }

                booking.setDate_working(DateTimeUtil.convertTimeToString(date.getTimeInMillis(), "dd/MM/yyyy"));
                booking.setHour_working(DateTimeUtil.convertTimeToString(date.getTimeInMillis(), "hh:mm"));
                booking.setNote(note.getText().toString());
                booking.setType_bike(AppController.getInstance().getmProfileUser().getType_bike());
                booking.setRepeat_type(spinner.getSelectedItemPosition());
                Intent i = new Intent(BookingActivity.this, PaymentActivity.class);
                startActivityForResult(i, 10000);
                break;
            case R.id.textView61:
                startActivityForResult(new Intent(BookingActivity.this, PromotionActivity.class), 1000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000 && resultCode == RESULT_OK && data != null) {
            booking.setMethod_payment(data.getIntExtra(AppConstant.MSG, 1));
            booking.setLocationID(AppController.getInstance().getLocation_id());
            if (promotion != null) {
                double priceCoupon = promotion.getValue() * serviceSeleted.getPrice() / 100;
                tvprice.setText(String.format("%,.0fđ", serviceSeleted.getPrice() - priceCoupon));
            }
            presenter.book(booking);
            return;
        }
        if (resultCode == RESULT_OK && data != null) {
            Bundle b = data.getBundleExtra(AppConstant.BUNDLE);
            promotion = b.getParcelable(AppConstant.MSG);
            if (promotion != null) {
                tvPromotion.setText(promotion.getCode() + " - Giảm " + promotion.getValue() + "%");
                if (serviceSeleted != null) {
                    double priceCoupon = promotion.getValue() * serviceSeleted.getPrice() / 100;
                    tvprice.setText(String.format("%,.0fđ", serviceSeleted.getPrice() - priceCoupon));
                }
            }
        }
    }

    @Override
    public void onSucessGetListService(List<Service> list) {
        listService = list;
        adapterService = new AdapterService(list, this, this);
        rcvService.setAdapter(adapterService);
    }

    @Override
    public void onGetAddressFromLatlngSuccess(MyPlace place) {
        this.place = place;
        tvAddress.setText(place.getDescription());
    }

    @Override
    public void onSucessBook(Booking booking) {
        new DialogSucess(this, new DialogSucess.DialogOneButtonClickListener() {
            @Override
            public void okClick() {
                Intent i = new Intent(BookingActivity.this, ConnectActivity.class);
                Bundle b = new Bundle();
                b.putParcelable(AppConstant.MSG, booking);
                i.putExtra(AppConstant.BUNDLE, b);
                startActivity(i);
                finish();
            }
        }).show();
    }

    @Override
    public Context getMyContext() {
        return null;
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

    }

    @Override
    public void onCallBack(int pos) {
        this.serviceSeleted = listService.get(pos);
        tvName.setText(serviceSeleted.getName());
        tvprice.setText(String.format("%,.0fđ", serviceSeleted.getPrice()));
        if (promotion != null) {
            double priceCoupon = promotion.getValue() * serviceSeleted.getPrice() / 100;
            tvprice.setText(String.format("%,.0fđ", serviceSeleted.getPrice() - priceCoupon));
        }
//        textView58.setText(serviceSeleted.getTime_work() + "s");
        int [] timeWork = splitToComponentTimes(BigDecimal.valueOf  (serviceSeleted.getTime_work()));
        textView58.setText(String.format("%02d phút %02d giây",timeWork[1],timeWork[2]));
        listStep.clear();
        stepAdapter.notifyDataSetChanged();
        if (serviceSeleted.getListStep() != null) {
          listStep.addAll(serviceSeleted.getListStep());
          adapterService.notifyDataSetChanged();
        }

    }
    public static int[] splitToComponentTimes(BigDecimal biggy)
    {
        long longVal = biggy.longValue();
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours , mins , secs};
        return ints;
    }
    @OnClick(R.id.layoutTime)
    public void onViewClicked() {
        AlertDialogCustom.showDialogDateTime(this, new AlertDialogCustom.CallBack() {
            @Override
            public void onCallBack(Calendar start) {
                textView58.setText(String.format(getString(R.string.format_time_ship), start.get(Calendar.HOUR_OF_DAY),
                        start.get(Calendar.MINUTE)));
            }
        }, date).show();
    }
}
