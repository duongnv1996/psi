package com.skynet.psi.ui.connect;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.Service;
import com.skynet.psi.ui.base.Presenter;
import com.skynet.psi.ui.booking.BookingContract;
import com.skynet.psi.ui.booking.BookingImplRemote;

import java.util.List;


public class ConnectPresenter extends Presenter<ConnectContract.View> implements ConnectContract.Presenter {
    ConnectContract.Interactor interactor;

    public ConnectPresenter(ConnectContract.View view) {
        super(view);
        interactor = new ConnectImplRemote(this);
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
    public void cancelBooking(int id) {
        interactor.cancelBooking(id);
    }

    @Override
    public void rateBooking(int id, float star) {
        interactor.rateBooking(id,star);

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
