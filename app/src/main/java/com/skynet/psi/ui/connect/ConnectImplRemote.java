package com.skynet.psi.ui.connect;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.application.AppController;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.Profile;
import com.skynet.psi.models.Service;
import com.skynet.psi.network.api.ApiResponse;
import com.skynet.psi.network.api.ApiService;
import com.skynet.psi.network.api.ApiUtil;
import com.skynet.psi.network.api.CallBackBase;
import com.skynet.psi.ui.base.Interactor;
import com.skynet.psi.ui.booking.BookingContract;
import com.skynet.psi.utils.AppConstant;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ConnectImplRemote extends Interactor implements ConnectContract.Interactor {
    ConnectContract.Listener listener;

    public ConnectImplRemote(ConnectContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }


    @Override
    public void getDetailBooking(int id) {
        getmService().getDetailBooking(id).enqueue(new CallBackBase<ApiResponse<Booking>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Booking>> call, Response<ApiResponse<Booking>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessGetDetailBooking(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Booking>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public void cancelBooking(int id) {
        getmService().cancelBooking(id, 5).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {

            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void rateBooking(int id, float star) {
        getmService().rating(id, star).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {

            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }
}