package com.skynet.psi.ui.main;

import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface ContactContract {
    interface View extends BaseView {

    }

    interface Presenter extends IBasePresenter ,Listener{
        void updateToken();

    }

    interface Interactor {
        void updateToken();
    }

    interface Listener extends OnFinishListener {

    }
}
