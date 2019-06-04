package com.skynet.psi.ui.scanqr;

import com.skynet.psi.ui.base.Presenter;

public class ScanPresenter extends Presenter<ScanContract.View> implements ScanContract.Presenter {
    ScanContract.Interactor interactor;

    public ScanPresenter(ScanContract.View view) {
        super(view);
        interactor = new ScanImplRemote(this);
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

}
