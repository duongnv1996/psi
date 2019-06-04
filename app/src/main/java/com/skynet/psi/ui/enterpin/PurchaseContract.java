package com.skynet.psi.ui.enterpin;

import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface PurchaseContract {
    interface View extends BaseView {
        void onSucessBooking();
        void onSucessSendPin(String code);

    }

    interface Presenter extends IBasePresenter ,Listener{
        void book();
        void sendPin();
    }

    interface Interactor {
        void book();
        void sendPin();

    }

    interface Listener extends OnFinishListener {
        void onSucessBooking();
        void onSucessSendPin(String code);

    }
}
