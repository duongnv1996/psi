package com.skynet.psi.ui.detailBooking;

import com.skynet.psi.models.Booking;
import com.skynet.psi.ui.base.Presenter;
import com.skynet.psi.ui.connect.ConnectContract;
import com.skynet.psi.ui.connect.ConnectImplRemote;


public class DetailBookingPresenter extends Presenter<DetailBookingContract.View> implements DetailBookingContract.Presenter {
    DetailBookingContract.Interactor interactor;

    public DetailBookingPresenter(DetailBookingContract.View view) {
        super(view);
        interactor = new DetailBookingImplRemote(this);
    }




    @Override
    public void onDestroyView() {
        view = null;
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


    @Override
    public void getDetailBooking(int id) {
        if(isAvaliableView()){
            view.showProgress();
            interactor.getDetailBooking(id);
        }
    }


    @Override
    public void onSucessGetDetailBooking(Booking booking) {
        if (isAvaliableView()) {
            view.hiddenProgress();
            if (booking != null) {
                view.onSucessGetDetailBooking(booking);
            }
        }
    }
}
