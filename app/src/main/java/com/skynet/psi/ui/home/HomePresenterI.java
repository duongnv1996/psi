package com.skynet.psi.ui.home;


import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.application.AppController;
import com.skynet.psi.models.HomeResponse;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.ProductResponse;
import com.skynet.psi.models.Profile;
import com.skynet.psi.models.franchise;
import com.skynet.psi.utils.AppConstant;

import java.util.List;

public class HomePresenterI implements HomeContract.PresenterI {
    HomeContract.View view;
    HomeContract.Interactor interactor;

    public HomePresenterI(HomeContract.View view) {
        this.view = view;
        interactor = new HomeRemoteImpl(this);
    }

    @Override
    public void getInfor() {

        String profileStr = AppController.getInstance().getmSetting().getString(AppConstant.KEY_PROFILE, "");
        if (profileStr.isEmpty()) {
            view.onError("not found");
        } else {
            view.showProgress();
            interactor.doGetInfor(profileStr);
        }

    }

    @Override
    public void onSuccessGetInfor(Profile profile) {
        if (view == null) return;
        view.hiddenProgress();
        AppController.getInstance().setmProfileUser(profile);
        view.onSuccessGetInfor();
    }


    @Override
    public void getAddressFromLatlng(LatLng latLng, Geocoder geocoder) {
        view.showProgress();
        interactor.getAddressFromLatlng(latLng, geocoder);
    }

    @Override
    public void updateCar(int type, String name) {
        interactor.updateCar(type, name);
    }

    @Override
    public void getFranchise() {
        interactor.getFranchise();
    }

    @Override
    public void onGetAddressFromLatlngSuccess(MyPlace place) {
        view.showProgress();
        view.onGetAddressFromLatlngSuccess(place);
    }

    @Override
    public void onSucessGetFranchise(List<franchise> list) {
        view.onSucessGetFranchise(list);
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onErrorApi(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onError("not found");

    }

    @Override
    public void onError(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onError("not found");

    }

    @Override
    public void onErrorAuthorization() {
        if (view == null) return;
        view.hiddenProgress();
        view.onError("not found");

    }


}
