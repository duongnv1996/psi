package com.skynet.mumgo.ui.history;

import com.skynet.mumgo.application.AppController;
import com.skynet.mumgo.models.Cart;
import com.skynet.mumgo.models.History;
import com.skynet.mumgo.models.Profile;
import com.skynet.mumgo.network.api.ApiResponse;
import com.skynet.mumgo.network.api.ApiService;
import com.skynet.mumgo.network.api.ApiUtil;
import com.skynet.mumgo.network.api.CallBackBase;
import com.skynet.mumgo.ui.base.Interactor;
import com.skynet.mumgo.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ListProductImplRemote extends Interactor implements ListProductContract.Interactor {
    ListProductContract.Listener listener;

    public ListProductImplRemote(ListProductContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getListProduct(int index) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().getHistory(profile.getId(),AppConstant.TYPE_USER, index).enqueue(new CallBackBase<ApiResponse<List<History>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<History>>> call, Response<ApiResponse<List<History>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessGetListProduct(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<History>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
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
    public void toggleFav(int id, boolean toggle) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().toggleFavProduct(profile.getId(), id, toggle ? 1 : 2).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {

            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }
}