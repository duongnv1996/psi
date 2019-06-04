package com.skynet.psi.ui.location;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.models.AddressGeocoding;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.network.api.ApiResponseGeoCoding;
import com.skynet.psi.network.api.ApiService;
import com.skynet.psi.network.api.ApiUtil;
import com.skynet.psi.network.api.CallBackBase;
import com.skynet.psi.ui.base.Interactor;
import com.skynet.psi.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LocationImplRemote extends Interactor implements LocationContract
        .Interactor {
    LocationContract.Listener listener;

    public LocationImplRemote(LocationContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.getAPIPLACE();
    }

    @Override
    public void getMyAddress(LatLng latLng) {
        getmService().getAddress(latLng.latitude + "," + latLng.longitude, AppConstant.GG_KEY).enqueue(new CallBackBase<ApiResponseGeoCoding<List<AddressGeocoding>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponseGeoCoding<List<AddressGeocoding>>> call, Response<ApiResponseGeoCoding<List<AddressGeocoding>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && !response.body().getPlaceNearBy().isEmpty()) {
                        AddressGeocoding addressGeocoding = response.body().getPlaceNearBy().get(0);
                        MyPlace myPlace = new MyPlace();
                        myPlace.setAddress(addressGeocoding.getAddress());
                        myPlace.setLat(latLng.latitude);
                        myPlace.setLng(latLng.longitude);
                        listener.onSuccessGetMyAddress(myPlace);
                    } else {
                        listener.onError(response.body().getCode());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponseGeoCoding<List<AddressGeocoding>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}
