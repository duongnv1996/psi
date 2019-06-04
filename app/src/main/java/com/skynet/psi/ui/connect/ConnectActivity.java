package com.skynet.psi.ui.connect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.skynet.psi.R;
import com.skynet.psi.application.AppController;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.Technical;
import com.skynet.psi.network.socket.SocketConstants;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.booking.AdapterStep;
import com.skynet.psi.ui.chatting.ChatActivity;
import com.skynet.psi.ui.views.DialogConnection;
import com.skynet.psi.utils.AppConstant;
import com.skynet.psi.utils.CommomUtils;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ConnectActivity extends BaseActivity implements ConnectContract.View, OnMapReadyCallback, ICallback {

    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.appBarLayout2)
    AppBarLayout appBarLayout2;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvTimeArrive)
    TextView tvTimeArrive;
    @BindView(R.id.avt)
    CircleImageView avt;
    @BindView(R.id.tvNameTech)
    TextView tvNameTech;
    @BindView(R.id.tvTech)
    TextView tvTech;
    @BindView(R.id.tvRate)
    TextView tvRate;
    @BindView(R.id.imgCall)
    ImageView imgCall;
    @BindView(R.id.imgChat)
    ImageView imgChat;
    @BindView(R.id.layoutTop)
    ConstraintLayout layoutTop;
    @BindView(R.id.imgService)
    CircleImageView imgService;
    @BindView(R.id.textView59)
    TextView tvNameService;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.imgGift)
    ImageView imgGift;
    @BindView(R.id.imgCar)
    ImageView imgCar;
    @BindView(R.id.tvNameCar)
    TextView tvNameCar;
    @BindView(R.id.textView64)
    TextView tvPayment;
    @BindView(R.id.layoutBottom)
    ConstraintLayout layoutBottom;
    @BindView(R.id.imgOnGoingService)
    CircleImageView imgOnGoingService;
    @BindView(R.id.tvOnGoingNameService)
    TextView tvOnGoingNameService;
    @BindView(R.id.imgOnGoingGift)
    ImageView imgOnGoingGift;
    @BindView(R.id.imgOnGoingAvt)
    CircleImageView imgOnGoingAvt;
    @BindView(R.id.tvOnGoingNameTech)
    TextView tvOnGoingNameTech;
    @BindView(R.id.tvTech2)
    TextView tvTech2;
    @BindView(R.id.tvOnGoingRate)
    TextView tvOnGoingRate;
    @BindView(R.id.tvOnGoingTime)
    TextView tvOnGoingTime;
    @BindView(R.id.rcvOnGoing)
    RecyclerView rcvOnGoing;
    @BindView(R.id.tvOnGoingPayment)
    TextView tvOnGoingPayment;
    @BindView(R.id.layoutOnGoingBottom)
    ConstraintLayout layoutOnGoingBottom;
    @BindView(R.id.layoutSucess)
    ConstraintLayout layoutSucess;
    @BindView(R.id.imageView31)
    ImageView imageView31;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.imgSucessAvt)
    CircleImageView imgSucessAvt;
    @BindView(R.id.textView65)
    TextView textView65;
    @BindView(R.id.tvSucessTime)
    TextView tvSucessTime;
    @BindView(R.id.tvSucessTech)
    TextView tvSucessTech;
    @BindView(R.id.tvSucessNameTech)
    TextView tvSucessNameTech;
    @BindView(R.id.tvSucessRate)
    TextView tvSucessRate;
    @BindView(R.id.textView67)
    TextView textView67;
    @BindView(R.id.ratingSucess)
    RatingBar ratingSucess;
    @BindView(R.id.textView66)
    TextView textView66;
    @BindView(R.id.textView68)
    TextView tvSucessPrice;
    @BindView(R.id.textView69)
    TextView textView69;
    private ConnectContract.Presenter presenter;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng myLatlng;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private MyPlace myPlace;
    private Booking booking;
    private DialogConnection dialogConnect;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String msg = intent.getStringExtra(AppConstant.MSG);
                Booking booking = new Gson().fromJson(msg, Booking.class);
                if (booking != null && ConnectActivity.this.booking != null && booking.getBooking_id() == ConnectActivity.this.booking.getBooking_id()) {
                    onRefesh(booking.getBooking_id());
                }
            }
        }
    };

    private void onRefesh(int booking_id) {
        presenter.getDetailBooking(booking_id);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_connect;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapLoaded() {
                if (myLatlng != null)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatlng, 15));
//                mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                CommomUtils.addMarker(getContext().getDrawable(R.drawable.ic_target),mMap,myLatlng);
                mMap.getUiSettings().setZoomGesturesEnabled(false);
                mMap.getUiSettings().setAllGesturesEnabled(false);
                CommomUtils.addMarker(getDrawable(R.drawable.ic_target), mMap, myLatlng);
                if (booking != null && booking.getListTechnical() != null) {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(new LatLng(booking.getLat(), booking.getLng()));
                    for (Technical technical : booking.getListTechnical()) {
                        LatLng latLng = new LatLng(technical.getLat(), technical.getLng());
                        builder.include(latLng);
                        CommomUtils.addMarker(getDrawable(R.drawable.ic_pin), mMap, latLng);
                    }
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getMyContext());
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
                                AppController.getInstance().getmSetting().put("latlng", new Gson().toJson(myLatlng));

                            }
                        }
                    });
        }
    }

    @Override
    protected void initVariables() {
        this.dialogConnect = new DialogConnection(this, this);
        presenter = new ConnectPresenter(this);
        booking = getIntent().getBundleExtra(AppConstant.BUNDLE).getParcelable(AppConstant.MSG);
        if (booking != null) {
            presenter.getDetailBooking(booking.getBooking_id());
            AppController.getInstance().setBooking(booking);
        }
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this, Locale.getDefault());
        rcvOnGoing.setLayoutManager(new LinearLayoutManager(this));
        rcvOnGoing.setHasFixedSize(true);
        if (ActivityCompat.checkSelfPermission(getMyContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMyContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            }
            return;
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getMyContext());
        if (ActivityCompat.checkSelfPermission(getMyContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getMyContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            }
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            setmyLatlng(location);
                        }
                    }
                });
    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }

    private void bindData() {
        myLatlng = new LatLng(booking.getLat(), booking.getLng());
        if (booking.getActive() == 0 || booking.getActive() == 1) {
            //todo finding tech
            dialogConnect.showDialog();
        } else {
            if (dialogConnect != null) dialogConnect.hideDialog();
            switch (booking.getActive()) {
                case 1: {
                    tvStatus.setText("Chờ xác nhận");
                    layoutTop.setVisibility(View.VISIBLE);
                    layoutBottom.setVisibility(View.GONE);
                    break;
                }
                case 2: {
                    tvStatus.setText("Kỹ thuật đã nhận");
                    layoutTop.setVisibility(View.VISIBLE);
                    layoutBottom.setVisibility(View.VISIBLE);
                    break;
                }
                case 3: {
                    tvStatus.setText("Đang thực hiện");
                    layoutTop.setVisibility(View.GONE);
                    layoutBottom.setVisibility(View.GONE);
                    layoutOnGoingBottom.setVisibility(View.VISIBLE);
                    break;
                }
                case 4: {
                    tvStatus.setText("Hoàn thành");
                    layoutTop.setVisibility(View.GONE);
                    layoutBottom.setVisibility(View.GONE);
                    layoutOnGoingBottom.setVisibility(View.GONE);
                    layoutSucess.setVisibility(View.VISIBLE);
                    break;
                }
                case 5: {
                    tvStatus.setText("Khách hàng huỷ");
                    break;
                }
                case 6: {
                    tvStatus.setText("Kỹ thuật huỷ");
                    break;
                }
                case 7: {
                    tvStatus.setText("Hệ thống huỷ");
                    break;
                }
            }

        }
        if (booking.getTech() != null) {
            bindTech(booking.getTech());
        }

        tvNameService.setText(booking.getService_name());
        tvOnGoingNameService.setText(booking.getService_name());
        if (booking.getService_image() != null && !booking.getService_image().isEmpty()) {
            Picasso.with(this).load(booking.getService_image()).fit().centerCrop().into(imgService);
            Picasso.with(this).load(booking.getService_image()).fit().centerCrop().into(imgOnGoingService);
        }
        switch (booking.getType_bike()) {
            case 1: {
                tvNameCar.setText("Bike");
                imgCar.setImageResource(R.drawable.ic_bike);
                break;
            }
            case 2: {
                tvNameCar.setText("Sedan");
                imgCar.setImageResource(R.drawable.ic_car_sedan);
                break;
            }
            case 3: {
                tvNameCar.setText("SUV");
                imgCar.setImageResource(R.drawable.ic_suv);
                break;
            }
        }

        if (booking.getIdPromotion() == 0) {
            imgGift.setVisibility(View.GONE);
            imgOnGoingGift.setVisibility(View.GONE);
        }

        switch (booking.getMethod_payment()) {
            case 2: {
                tvPayment.setText("Thanh toán qua thẻ ngân hàng");
                tvOnGoingPayment.setText("Thanh toán qua thẻ ngân hàng");
                break;
            }
            case 3: {
                tvPayment.setText("Thanh toán qua ví điện tử");
                tvOnGoingPayment.setText("Thanh toán qua ví điện tử");
                break;
            }
            case 1: {
                tvPayment.setText("Thanh toán bằng tiền mặt");
                tvOnGoingPayment.setText("Thanh toán bằng tiền mặt");
                break;
            }
        }


        tvOnGoingTime.setText(booking.getHour_working());
        tvSucessTime.setText(booking.getTime_working());
        tvSucessPrice.setText(String.format("%,.0fvnđ", booking.getPrice()));
        tvPrice.setText(String.format("%,.0fvnđ", booking.getPrice()));
        rcvOnGoing.setAdapter(new AdapterConnectStep(booking.getService_detail(),this));
    }

    private void bindTech(Technical tech) {
        tvNameTech.setText(tech.getName());
        tvSucessNameTech.setText(tech.getName());
        tvOnGoingNameTech.setText(tech.getName());
        tvOnGoingRate.setText(tech.getRating() + "");
        tvSucessRate.setText(tech.getRating() + "");
        tvRate.setText(tech.getRating() + "");
        if (tech.getAvatar() != null && !tech.getAvatar().isEmpty()) {
            Picasso.with(this).load(tech.getAvatar()).fit().centerCrop().into(avt);
            Picasso.with(this).load(tech.getAvatar()).fit().centerCrop().into(imgOnGoingAvt);
            Picasso.with(this).load(tech.getAvatar()).fit().centerCrop().into(imgSucessAvt);
        }
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        IntentFilter i = new IntentFilter();
        i.addAction(SocketConstants.SOCKET_BOOKING);
        registerReceiver(this.receiver, i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hiddenProgress() {
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

    private void setmyLatlng(Location location) {
        myLatlng = new LatLng(location.getLatitude(), location.getLongitude());
        AppController.getInstance().getmSetting().put(AppConstant.LAT, (float) myLatlng.latitude);
        AppController.getInstance().getmSetting().put(AppConstant.LNG, (float) myLatlng.longitude);
//        if (mMarker == null)
//            mMarker = CommomUtils.addMarker(getDrawable(R.drawable.ic_pin), mMap, myLatlng);

    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }


    @OnClick({R.id.imgHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                onBackPressed();
                break;
        }
    }


    @Override
    public void onCallBack(int pos) {
        if (booking != null)
            presenter.cancelBooking(booking.getBooking_id());
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onSucessGetDetailBooking(Booking booking) {
        this.booking = booking;
        bindData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imgCall, R.id.imgChat})
    public void onViewActionClicked(View view) {
        if (booking != null && booking.getTech() != null) {
            switch (view.getId()) {
                case R.id.imgCall:
                    CommomUtils.dialPhoneNumber(this, booking.getTech().getPhone());
                    break;
                case R.id.imgChat:
                    //todo chat here
                    Intent i = new Intent(this, ChatActivity.class);
                    Bundle b = new Bundle();
                    b.putParcelable(AppConstant.INTENT, booking.getTech());
                    b.putParcelable("user", AppController.getInstance().getmProfileUser());
                    b.putInt("idPost", booking.getBooking_id());
                    b.putString("avt", AppController.getInstance().getmProfileUser().getType() == 1 ? booking.getTech().getAvatar() : AppController.getInstance().getmProfileUser().getAvatar());
                    i.putExtra(AppConstant.BUNDLE, b);
                    startActivityForResult(i, 1000);
                    break;
            }
        }
    }

    @OnClick(R.id.btnConfirm)
    public void onViewClicked() {
        if (booking != null)
            presenter.rateBooking(booking.getBooking_id(), ratingSucess.getRating());
        setResult(RESULT_OK);
        finish();
    }
}
