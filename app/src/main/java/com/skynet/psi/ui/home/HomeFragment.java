package com.skynet.psi.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LocationUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.skynet.psi.R;
import com.skynet.psi.application.AppController;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.interfaces.ICallbackT;
import com.skynet.psi.models.Banner;
import com.skynet.psi.models.Category;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.News;
import com.skynet.psi.models.Product;
import com.skynet.psi.models.Profile;
import com.skynet.psi.models.Shop;
import com.skynet.psi.models.Suggestion;
import com.skynet.psi.models.franchise;
import com.skynet.psi.ui.Notification.NotificationActivity;
import com.skynet.psi.ui.auth.updateProfile.SearchMapAdressActivity;
import com.skynet.psi.ui.base.BaseFragment;
import com.skynet.psi.ui.cart.CartActivity;
import com.skynet.psi.ui.detailProduct.ActivityDetailProduct;
import com.skynet.psi.ui.detailshop.DetailShopActivity;
import com.skynet.psi.ui.listProduct.ListProductActivity;
import com.skynet.psi.ui.location.LocationActivity;
import com.skynet.psi.ui.main.MainActivity;
import com.skynet.psi.ui.scanqr.ScannerQr;
import com.skynet.psi.ui.search.ActivitySearch;
import com.skynet.psi.ui.shop.NearbyActivity;
import com.skynet.psi.ui.views.DialogChooseCar;
import com.skynet.psi.utils.AppConstant;
import com.skynet.psi.utils.CommomUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.lightsky.infiniteindicator.IndicatorConfiguration;
import cn.lightsky.infiniteindicator.OnPageClickListener;
import cn.lightsky.infiniteindicator.Page;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.app.Activity.RESULT_OK;
import static cn.lightsky.infiniteindicator.IndicatorConfiguration.LEFT;

public class HomeFragment extends BaseFragment implements HomeContract.View, OnMapReadyCallback {


    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imgNotification)
    ImageView imgNotification;
    @BindView(R.id.appBarLayout2)
    AppBarLayout appBarLayout2;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.imageView28)
    ImageView imageView28;
    @BindView(R.id.btnBookNow)
    TextView btnBookNow;
    private HomePresenterI presenter;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng myLatlng;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private MyPlace myPlace;
    private List<franchise> listFranchise;
    private Map<Marker, franchise> mapMarker;
    private boolean isAddFranchise = false;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(getContext(), Locale.getDefault());

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
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            setmyLatlng(location);
                        }
                    }
                });

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
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

//                CommomUtils.addMarker(getContext().getDrawable(R.drawable.ic_target),mMap,myLatlng);
                mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {
                        myLatlng = mMap.getCameraPosition().target;
                        presenter.getAddressFromLatlng(myLatlng, geocoder);
                    }
                });

                if (listFranchise != null && !isAddFranchise) {
                    for (franchise f : listFranchise) {
                        Marker m = CommomUtils.addMarker(getMyContext().getDrawable(R.drawable.ic_pin), mMap, new LatLng(f.getLat(), f.getLng()));
                        mapMarker.put(m, f);
                        isAddFranchise = true;
                    }
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (mapMarker.containsKey(marker)) {
                            franchise f = mapMarker.get(marker);
                            myPlace = new MyPlace();
                            myPlace.setAddress(f.getName());
                            myPlace.setName(f.getName());
                            myPlace.setLat(f.getLat());
                            myPlace.setLng(f.getLng());
                            tvAddress.setText(myPlace.getAddress());
                            myLatlng = new LatLng(f.getLat(), f.getLng());
                            AppController.getInstance().setLocation_id(f.getId());
                            AppController.getInstance().setFranchiseName(f.getName());
                            AppController.getInstance().getmSetting().put(AppConstant.LAT, (float) myLatlng.latitude);
                            AppController.getInstance().getmSetting().put(AppConstant.LNG, (float) myLatlng.longitude);
                            new DialogChooseCar(getContext(), new DialogChooseCar.DialogOneButtonClickListener() {
                                @Override
                                public void okClick(int type, String name) {
                                    presenter.updateCar(type, name);
                                    ((MainActivity) getActivity()).selectTab(1);
                                }
                            }).show();
                        }
                        return true;
                    }
                });
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
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
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
        mapMarker = new HashMap<>();
        presenter = new HomePresenterI(this);
        presenter.getFranchise();
    }


    private void bindData() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile != null) {

        } else {
            showDialogExpiredToken();
            return;
        }
    }

    private void setupChart(Profile profile) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void doAction() {

    }

    @Override
    public void onResume() {
        super.onResume();
        AppController.getInstance().setLocation_id(0);
        AppController.getInstance().setFranchiseName(null);
        if(!LocationUtils.isGpsEnabled() || !LocationUtils.isLocationEnabled()){
            new MaterialDialog.Builder(getMyContext()).title("GPS thiết bị").content("Chung tôi nhận thấy GPS của thiết bị đang bị tắt. Vui lòng bật GPS để tiếp tục sử dụng dịch vụ.").positiveText("Cài đặt").positiveColor(Color.BLACK).onPositive(new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent,1002);
                }
            }).show();
        }
    }


    @Override
    public void onSuccessGetInfor() {
        bindData();
    }

    @Override
    public void onGetAddressFromLatlngSuccess(MyPlace place) {
        this.myPlace = place;
        tvAddress.setText(place.getDescription());
        AppController.getInstance().setLocation_id(0);
        AppController.getInstance().setFranchiseName(null);
    }

    @Override
    public void onSucessGetFranchise(List<franchise> list) {
        this.listFranchise = list;
        if (mMap != null && !isAddFranchise) {
            for (franchise f : listFranchise) {
                Marker m = CommomUtils.addMarker(getMyContext().getDrawable(R.drawable.ic_pin), mMap, new LatLng(f.getLat(), f.getLng()));
                mapMarker.put(m, f);
                isAddFranchise = true;
            }
        }
    }

    @Override
    public Context getMyContext() {
        return getContext();
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
        showDialogExpiredToken();
    }


    @OnClick({R.id.imgHome, R.id.imgNotification, R.id.tvAddress, R.id.btnBookNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                ((MainActivity) getActivity()).openMenu();
                break;
            case R.id.imgNotification:
                startActivity(new Intent(getActivity(), NotificationActivity.class));
                break;
            case R.id.tvAddress:
                startActivityForResult(new Intent(getActivity(), SearchMapAdressActivity.class), 1001);
                break;
            case R.id.btnBookNow:
                new DialogChooseCar(getContext(), new DialogChooseCar.DialogOneButtonClickListener() {
                    @Override
                    public void okClick(int type, String name) {
                        presenter.updateCar(type, name);
                        ((MainActivity) getActivity()).selectTab(1);
                    }
                }).show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null) {
            Bundle b = data.getBundleExtra(AppConstant.BUNDLE);
            if (b != null) {
                com.skynet.psi.models.Place place = b.getParcelable(AppConstant.MSG);
                if (place != null) {
                    MyPlace myPlace = new MyPlace();
                    myPlace.setName(place.getAddress());
                    myPlace.setDescription(place.getAddress());
                    myPlace.setLat(place.getLatLng().latitude);
                    myPlace.setLng(place.getLatLng().longitude);
                    this.myPlace = myPlace;
                    tvAddress.setText(place.getAddress());
                    myLatlng = place.getLatLng();
                    AppController.getInstance().getmSetting().put(AppConstant.LAT, (float) myLatlng.latitude);
                    AppController.getInstance().getmSetting().put(AppConstant.LNG, (float) myLatlng.longitude);
                    new DialogChooseCar(getContext(), new DialogChooseCar.DialogOneButtonClickListener() {
                        @Override
                        public void okClick(int type, String name) {
                            presenter.updateCar(type, name);
                            ((MainActivity) getActivity()).selectTab(1);
                        }
                    }).show();
                }
            }
        }
        if (requestCode == 10) {
            if (resultCode == getActivity().RESULT_OK) {
                com.google.android.gms.location.places.Place place = PlacePicker.getPlace(data, getMyContext());
                String toastMsg = String.format("Place: %s", place.getName());
                MyPlace myPlace = new MyPlace();
                myPlace.setName(place.getName().toString());
                myPlace.setDescription(place.getName().toString());
                myPlace.setLat(place.getLatLng().latitude);
                myPlace.setLng(place.getLatLng().longitude);
                myLatlng = place.getLatLng();
                AppController.getInstance().getmSetting().put(AppConstant.LAT, (float) myLatlng.latitude);
                AppController.getInstance().getmSetting().put(AppConstant.LNG, (float) myLatlng.longitude);
                this.myPlace = myPlace;
                new DialogChooseCar(getContext(), new DialogChooseCar.DialogOneButtonClickListener() {
                    @Override
                    public void okClick(int type, String name) {
                        presenter.updateCar(type, name);
                        ((MainActivity) getActivity()).selectTab(1);
                    }
                }).show();
            }
            return;
        }
        if (requestCode == 1002 && resultCode == RESULT_OK) {
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
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                setmyLatlng(location);
                            }
                        }
                    });
        }
    }
}
