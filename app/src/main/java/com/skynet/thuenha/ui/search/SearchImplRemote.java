package com.skynet.thuenha.ui.search;

import com.google.gson.Gson;
import com.skynet.thuenha.application.AppController;
import com.skynet.thuenha.models.Address;
import com.skynet.thuenha.models.HomeResponse;
import com.skynet.thuenha.models.Post;
import com.skynet.thuenha.network.api.ApiResponse;
import com.skynet.thuenha.network.api.ApiService;
import com.skynet.thuenha.network.api.ApiUtil;
import com.skynet.thuenha.network.api.CallBackBase;
import com.skynet.thuenha.network.api.ExceptionHandler;
import com.skynet.thuenha.ui.base.Interactor;
import com.skynet.thuenha.utils.AppConstant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SearchImplRemote extends Interactor implements SearchContract.Interactor {
    SearchContract.Listener listener;
    Address district;

    public SearchImplRemote(SearchContract.Listener listener) {
        this.listener = listener;
        String json = AppController.getInstance().getmSetting().getString(AppConstant.district);
        if (json != null && !json.isEmpty())
            district = new Gson().fromJson(json, Address.class);
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getAllPostByService(int idService) {

        getmService().getListPost(idService, district != null ? district.getId() : 1).enqueue(new CallBackBase<ApiResponse<List<Post>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Post>>> call, Response<ApiResponse<List<Post>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS ) {
                        listener.onSucessGetPost(response.body().getData());
                    } else {
                        new ExceptionHandler<List<Post>>(listener, response.body()).excute();
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Post>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }

    @Override
    public void queryPostByService(int idService, String query) {

        getmService().searchListPost(idService, district != null ? district.getId() : 1, query).enqueue(new CallBackBase<ApiResponse<List<Post>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Post>>> call, Response<ApiResponse<List<Post>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS ) {
                        listener.onSucessGetPost(response.body().getData());
                    } else {
                        new ExceptionHandler<List<Post>>(listener, response.body()).excute();
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Post>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
}