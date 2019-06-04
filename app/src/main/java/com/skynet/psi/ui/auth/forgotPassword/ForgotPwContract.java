package com.skynet.psi.ui.auth.forgotPassword;


import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;

public interface ForgotPwContract {
    interface View extends BaseView {
        void signUpSuccess();
    }

    interface Presenter extends IBasePresenter{
        void signUp(String email, int type);
        void signUpSuccess();
    }

    interface Interactor {
        void doSignUp(String email, int type);
    }
}
