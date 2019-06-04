package com.skynet.psi.ui.home;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.skynet.psi.application.AppController;
import com.skynet.psi.models.HomeResponse;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.ProductResponse;
import com.skynet.psi.models.Profile;
import com.skynet.psi.models.franchise;
import com.skynet.psi.network.api.ApiResponse;
import com.skynet.psi.network.api.ApiService;
import com.skynet.psi.network.api.ApiUtil;
import com.skynet.psi.network.api.CallBackBase;
import com.skynet.psi.ui.base.Interactor;
import com.skynet.psi.utils.AppConstant;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRemoteImpl extends Interactor implements HomeContract.Interactor {
    HomeContract.Listener presenter;

    public HomeRemoteImpl(HomeContract.Listener presenter) {
        this.presenter = presenter;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getAddressFromLatlng(LatLng latLng, Geocoder geocoder) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() == 0) return;
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            MyPlace place = new MyPlace();
            place.setLat(latLng.latitude);
            place.setLng(latLng.longitude);
            place.setName(knownName);
            String[] splitAddress = address.split(",");
            if (splitAddress.length > 2)
                place.setDescription(splitAddress[0] + ", " + splitAddress[1]);
            else
                place.setDescription(address);
            presenter.onGetAddressFromLatlngSuccess(place);
        } catch (IOException e) {
            e.printStackTrace();
            presenter.onError(e.getMessage());
        }
    }

    @Override
    public void updateCar(int type, String name) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            presenter.onErrorAuthorization();
            return;
        }
        getmService().updateCar(profile.getId(), type, name).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void getFranchise() {
        getmService().getListFranchise().enqueue(new Callback<ApiResponse<List<franchise>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<franchise>>> call, Response<ApiResponse<List<franchise>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    presenter.onSucessGetFranchise(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<franchise>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void doGetInfor(String idUser) {
        Profile profile = new Gson().fromJson(idUser, Profile.class);
        if (profile == null) {
            presenter.onErrorAuthorization();
            return;
        }
        getmService().getProfile(profile.getId(), AppConstant.TYPE_USER).enqueue(new CallBackBase<ApiResponse<Profile>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Profile>> call, final Response<ApiResponse<Profile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS && response.body().getData() != null) {
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                presenter.onSuccessGetInfor(response.body().getData());
//                                AppController.getInstance().setListBanner(response.body().getData().getBanners());
                            }
                        }, 2000);
                    } else {
                        presenter.onErrorAuthorization();
                    }
                } else {
                    presenter.onErrorAuthorization();
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Profile>> call, Throwable t) {
                presenter.onErrorAuthorization();

            }
        });
    }

}
