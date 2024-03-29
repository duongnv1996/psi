package com.skynet.psi.ui.detailNotification;


import com.skynet.psi.models.Notification;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface DetailNotificationContract  {
    interface View extends BaseView {
        void onSuccessGetDetail(Notification notification);

    }

    interface Presenter extends IBasePresenter,OnFinishDetailNotificationListener{
        void getDetail(String id);
    }

    interface Interactor {
        void doGetDetail(String id);
    }

    interface OnFinishDetailNotificationListener extends OnFinishListener {
        void onSuccessGetDetail(Notification notification);
    }
}
