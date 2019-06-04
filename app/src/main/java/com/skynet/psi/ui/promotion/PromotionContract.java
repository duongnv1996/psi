package com.skynet.psi.ui.promotion;


import com.skynet.psi.models.Promotion;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface PromotionContract {
    interface View extends BaseView {
        void onSucessGetPromotion(List<Promotion> list);

    }

    interface Presenter extends IBasePresenter,PromotionListener {
        void getPromotion();
    }

    interface Interactor {
        void getPromotion();

    }

    interface PromotionListener extends OnFinishListener {
        void onSucessGetPromotion(List<Promotion> list);

    }
}
