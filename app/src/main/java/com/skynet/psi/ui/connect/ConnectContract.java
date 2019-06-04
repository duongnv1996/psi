package com.skynet.psi.ui.connect;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.Service;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface ConnectContract {
    interface View extends BaseView {
        void onSucessGetDetailBooking(Booking booking);

    }

    interface Presenter extends IBasePresenter, Listener {
        void getDetailBooking(int id);
        void cancelBooking(int id);
        void rateBooking(int id,float star);
    }

    interface Interactor {
        void getDetailBooking(int id);
        void cancelBooking(int id);
        void rateBooking(int id,float star);

    }

    interface Listener extends OnFinishListener {
        void onSucessGetDetailBooking(Booking booking);
    }
}
