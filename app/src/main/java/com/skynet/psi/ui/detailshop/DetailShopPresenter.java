package com.skynet.psi.ui.detailshop;

import com.skynet.psi.models.ShopDetail;
import com.skynet.psi.ui.base.Presenter;

public class DetailShopPresenter extends Presenter<DetailShopContract.View> implements DetailShopContract.Presenter {
    DetailShopContract.Interactor interactor;

    public DetailShopPresenter(DetailShopContract.View view) {
        super(view);
        interactor = new DetailShopImplRemote(this);
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
    public void getShop(int id) {
        if (isAvaliableView()) {
            view.showProgress();
            interactor.getShop(id);
        }
    }

    @Override
    public void toggleFav(int id, boolean toggle) {
        if (isAvaliableView()) {
            interactor.toggleFav(id,toggle);
        }
    }

    @Override
    public void onSucessGetShop(ShopDetail shopDetail) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            if (shopDetail != null) {
                view.onSucessGetShop(shopDetail);
            }
        }
    }
}
