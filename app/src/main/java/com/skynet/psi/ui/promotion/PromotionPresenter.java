package com.skynet.psi.ui.promotion;


import com.skynet.psi.models.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionPresenter implements PromotionContract.Presenter{
    PromotionContract.View view;
    PromotionContract.Interactor interactor;

    public PromotionPresenter(PromotionContract.View view) {
        this.view = view;
        interactor = new PromotionRemoteImpl(this);
    }

    @Override
    public void getPromotion() {
        view.showProgress();
        interactor.getPromotion();
    }


    @Override
    public void onDestroyView() {
        view = null;

    }

    @Override
    public void onSucessGetPromotion(List<Promotion> list) {
        if (view == null) return;
        view.hiddenProgress();
        if (list == null) list = new ArrayList<>();
        view.onSucessGetPromotion(list);
    }



    @Override
    public void onErrorApi(String message) {
        if (view != null) {
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onError(String message) {
        if (view != null) {
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {

    }

}
