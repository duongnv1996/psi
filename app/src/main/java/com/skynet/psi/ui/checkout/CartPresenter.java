package com.skynet.psi.ui.checkout;

import com.skynet.psi.application.AppController;
import com.skynet.psi.models.Cart;
import com.skynet.psi.ui.base.Presenter;

public class CartPresenter extends Presenter<CartContract.View> implements CartContract.Presenter {
    CartContract.Interactor interactor;

    public CartPresenter(CartContract.View view) {
        super(view);
        interactor = new CartImplRemote(this);
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
    public void getCart() {
        if(isAvaliableView()){
            view.showProgress();
            interactor.getCart();
        }
    }

    @Override
    public void updateInfo(String name, String email, String phone, String address) {
        if(isAvaliableView()){
            interactor.updateInfo(name,email,phone,address);
        }
    }

    @Override
    public void updateTimeShip(String time) {
        if(isAvaliableView()){
            interactor.updateTimeShip(time);
        }
    }


    @Override
    public void onSucessGetCart(Cart list) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            if (list != null) {
                AppController.getInstance().setCart(list);
                view.onSucessGetCart(list);
            }
        }
    }

}
