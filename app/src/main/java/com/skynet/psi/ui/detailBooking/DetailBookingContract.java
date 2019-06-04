package com.skynet.psi.ui.detailBooking;

import com.skynet.psi.models.Booking;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface DetailBookingContract {
    interface View extends BaseView {
        void onSucessGetDetailBooking(Booking booking);

    }

    interface Presenter extends IBasePresenter, Listener {
        void getDetailBooking(int id);
    }

    interface Interactor {
        void getDetailBooking(int id);
    }

    interface Listener extends OnFinishListener {
        void onSucessGetDetailBooking(Booking booking);
    }
}
