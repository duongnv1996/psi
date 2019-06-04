package com.skynet.psi.ui.category;

import com.skynet.psi.models.Category;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface CategoryContract {
    interface View extends BaseView {
        void onSuccessGetListCategory(List<Category> list);
    }

    interface Presenter extends IBasePresenter ,Listener{
        void getCategory();
    }

    interface Interactor {
        void getCategory();

    }

    interface Listener extends OnFinishListener {
        void onSucessGetCategory(List<Category> categories);
    }
}
