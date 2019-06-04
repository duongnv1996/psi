package com.skynet.psi.ui.scanqr;

import com.skynet.psi.application.AppController;
import com.skynet.psi.models.Profile;
import com.skynet.psi.network.api.ApiResponse;
import com.skynet.psi.network.api.ApiService;
import com.skynet.psi.network.api.ApiUtil;
import com.skynet.psi.network.api.CallBackBase;
import com.skynet.psi.ui.base.Interactor;
import com.skynet.psi.utils.AppConstant;

import retrofit2.Call;
import retrofit2.Response;

public class ScanImplRemote extends Interactor implements ScanContract.Interactor {
    ScanContract.Listener listener;

    public ScanImplRemote(ScanContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }


    @Override
    public void getShop(int id) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().scanShop(profile.getId(), id).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                    } else {
                        listener.onErrorApi(response.message());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {

            }
        });
    }

}
