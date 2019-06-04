package com.skynet.psi.ui.main;

import com.blankj.utilcode.util.LogUtils;
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

public class ContactImplRemote extends Interactor implements ContactContract.Interactor {
    ContactContract.Listener listener;

    public ContactImplRemote(ContactContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }



    @Override
    public void updateToken() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        LogUtils.e("update token firebase ",AppController.getInstance().getmSetting().getString(AppConstant.KEY_TOKEN_FCM));
        getmService().updateFCM(profile.getU_id(), AppController.getInstance().getmSetting().getString(AppConstant.KEY_TOKEN_FCM),AppConstant.TYPE_USER).enqueue(new CallBackBase<ApiResponse>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse> call, Response<ApiResponse> response) {

            }

            @Override
            public void onRequestFailure(Call<ApiResponse> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }


}
