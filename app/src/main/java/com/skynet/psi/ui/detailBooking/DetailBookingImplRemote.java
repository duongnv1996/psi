package com.skynet.psi.ui.detailBooking;

import com.skynet.psi.models.Booking;
import com.skynet.psi.network.api.ApiResponse;
import com.skynet.psi.network.api.ApiService;
import com.skynet.psi.network.api.ApiUtil;
import com.skynet.psi.network.api.CallBackBase;
import com.skynet.psi.ui.base.Interactor;
import com.skynet.psi.ui.connect.ConnectContract;
import com.skynet.psi.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class DetailBookingImplRemote extends Interactor implements DetailBookingContract.Interactor {
    DetailBookingContract.Listener listener;

    public DetailBookingImplRemote(DetailBookingContract.Listener listener) {
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

}