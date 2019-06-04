package com.skynet.psi.ui.scanqr;

import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface ScanContract {
    interface View extends BaseView {
    }

    interface Presenter extends IBasePresenter ,Listener{
        void getShop(int id);
    }

    interface Interactor {
        void getShop(int id);
    }

    interface Listener extends OnFinishListener {
    }
}
