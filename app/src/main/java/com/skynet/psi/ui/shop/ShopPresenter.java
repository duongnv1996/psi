package com.skynet.psi.ui.shop;

import com.skynet.psi.models.Shop;
import com.skynet.psi.ui.base.Presenter;

import java.util.List;

public class ShopPresenter extends Presenter<ShopContract.View> implements ShopContract.Presenter {
    ShopContract.Interactor interactor;

    public ShopPresenter(ShopContract.View view) {
        super(view);
        interactor = new ShopImplRemote(this);
    }


    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onErrorApi(String message) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            view.onErrorApi(message);
        }
    }

    @Override
    public void onError(String message) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {
        if (isAvaliableView()) {
            view.hiddenProgress();
            view.onErrorAuthorization();
        }
    }


    @Override
    public void getListShop(int type) {
        if (isAvaliableView()) {
            view.showProgress();
            interactor.getListShop(type);
        }
    }

    @Override
    public void getListFriend() {
        if (isAvaliableView()) {
            view.showProgress();
            interactor.getListFriend(1);
        }
    }

    @Override
    public void getListShopNearby(double lat, double lng) {
        if (isAvaliableView()) {
            view.showProgress();
            interactor.getListShopNearby(lat, lng);
        }
    }


    @Override
    public void onSuccessGetListShop(List<Shop> shopResponse) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            if (shopResponse != null) {
                view.onSuccessGetListShop(shopResponse);
            }
        }
    }


}
