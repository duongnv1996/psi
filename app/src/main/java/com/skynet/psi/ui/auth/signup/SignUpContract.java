package com.skynet.psi.ui.auth.signup;


import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;

public interface SignUpContract {
    interface View extends BaseView {
        void signUpSuccess(String code);

    }

    interface Presenter extends IBasePresenter {
        void signUp(String phone    );

        void signUpSuccess(String code);



    }

    interface Interactor {
        void doSignUp(String phone);

    }
}
