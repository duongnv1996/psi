package com.skynet.psi.ui.detailhistory;

import com.skynet.psi.models.History;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface HistoryContract {
    interface View extends BaseView {
        void onSucessGetCart(History history);
        void onSucessCancel();

    }
    interface Presenter extends IBasePresenter,Listener{
        void getHistory(int id);
        void cancle(int id);
    }
    interface Interactor{
        void getHistory(int id);
        void cancle(int id);

    }
    interface Listener extends OnFinishListener{
        void onSucessGetCart(History history);
        void onSucessCancel();
    }
}
