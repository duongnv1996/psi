package com.skynet.psi.ui.splash;


import com.skynet.psi.models.Profile;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface SlideContract  {
    interface View extends BaseView {
        void onSuccessGetInfor();

    }
    interface PresenterI extends IBasePresenter,OnFinishListener {
       void getInfor();
       void getInforSuccess(Profile profile);
       void notFoundInfor();
    }

    interface Interactor {
        void doGetInfor(String profileInfor);
    }
}
