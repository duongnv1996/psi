package com.skynet.psi.ui.auth.signup;

import android.util.Patterns;

public class SignUpPresenter implements SignUpContract.Presenter {
    SignUpContract.View view;
    SignUpContract.Interactor interactor;

    public SignUpPresenter(SignUpContract.View view) {
        this.view = view;
        interactor = new SignUpRemoteImpl(this);
    }

    @Override
    public void signUp(String phone) {
        if (view == null) return;
        if (Patterns.PHONE.matcher(phone).matches()) {
            if (phone.length() < 10 || phone.length() > 11) {
                view.onError("Số điện thọai sai định dạng");
                return;
            }
        }

        view.showProgress();
        interactor.doSignUp( phone);

    }

    @Override
    public void signUpSuccess(String code) {
        if (view == null) return;
        view.hiddenProgress();
        view.signUpSuccess(code);
    }

    @Override
    public void onDestroyView() {
        view = null;

    }

    @Override
    public void onErrorApi(String message) {
        if (view != null) {
            view.hiddenProgress();
            view.onErrorApi(message);
        }
    }

    @Override
    public void onError(String message) {
        if (view != null) {
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {

    }

}
