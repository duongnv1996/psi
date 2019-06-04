package com.skynet.psi.ui.checkout;

import com.skynet.psi.application.AppController;
import com.skynet.psi.models.Cart;
import com.skynet.psi.models.Profile;
import com.skynet.psi.network.api.ApiResponse;
import com.skynet.psi.network.api.ApiService;
import com.skynet.psi.network.api.ApiUtil;
import com.skynet.psi.network.api.CallBackBase;
import com.skynet.psi.ui.base.Interactor;
import com.skynet.psi.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class CartImplRemote extends Interactor implements CartContract.Interactor {
    CartContract.Listener listener;

    public CartImplRemote(CartContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }


    @Override
    public void getCart() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().getCart(profile.getId()).enqueue(new CallBackBase<ApiResponse<Cart>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Cart>> call, Response<ApiResponse<Cart>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        AppController.getInstance().setCart(response.body().getData());
                        listener.onSucessGetCart(response.body().getData());
                    } else {
                        listener.onError(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Cart>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }

    @Override
    public void updateInfo(String name, String email, String phone, String address) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().updateInforCart(profile.getId(),name,phone,email,address).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {

                    } else {
                        listener.onError(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }

    @Override
    public void updateTimeShip(String time) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().updateTimeShip(profile.getId(),time).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                    } else {
                        listener.onError(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                listener.onErrorApi(t.getMessage());

            }
        });
    }

}
