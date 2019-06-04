package com.skynet.psi.ui.listfeedback;


import com.skynet.psi.models.Feedback;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface ListProductContract {
    interface View extends BaseView {
        void onSucessGetListProduct(List<Feedback> list, int index);

    }

    interface Presenter extends IBasePresenter,Listener{
        void getListProduct(int id);


    }

    interface Interactor {
        void getListProduct(int id);


    }

    interface Listener extends OnFinishListener {
        void onSucessGetListProduct(List<Feedback> response);

    }
}
