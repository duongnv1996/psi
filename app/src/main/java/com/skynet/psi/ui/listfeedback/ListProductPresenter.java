package com.skynet.psi.ui.listfeedback;


import com.skynet.psi.models.Feedback;
import com.skynet.psi.ui.base.Presenter;

import java.util.List;

public class ListProductPresenter extends Presenter<ListProductContract.View> implements ListProductContract.Presenter {
    ListProductContract.Interactor interactor;

    public ListProductPresenter(ListProductContract.View view) {
        super(view);
        interactor = new ListProductImplRemote(this);
    }

    @Override
    public void getListProduct(int id) {
        if (isAvaliableView()) {
            view.showProgress();
            interactor.getListProduct(id);
        }
    }


    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onSucessGetListProduct(List<Feedback> response) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            if (response != null) {
                if (response != null) {
                    view.onSucessGetListProduct(response,0);
                }
            }
        }
    }



    @Override
    public void onErrorApi(String message) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onErrorApi(message);
        }
    }

    @Override
    public void onError(String message) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onErrorAuthorization();
        }
    }
}
