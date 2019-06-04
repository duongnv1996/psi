package com.skynet.psi.ui.auth.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.skynet.psi.R;
import com.skynet.psi.ui.auth.verifyaccount.VerifyAccountActivity;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.views.ProgressDialogCustom;
import com.skynet.psi.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huy on 3/15/2018.
 */

public class ActivitySignUp extends BaseActivity implements SignUpContract.View {


    @BindView(R.id.username_txt)
    EditText usernameTxt;
    @BindView(R.id.imageView9)
    ImageView imageView9;
    @BindView(R.id.imageView23)
    ImageView imageView23;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.pass_txt)
    EditText passTxt;
    @BindView(R.id.email_txt)
    EditText emailTxt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.tvSignup)
    Button tvSignup;


    private SignUpContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int initLayout() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new SignUpPresenter(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }


    @Override
    public void signUpSuccess(String code) {
//        Toast.makeText(getContext(), R.string.signup_success, Toast.LENGTH_SHORT).show();
//        // ((AccountActivity) getActivity()).layoutPrivacy.setVisibility(View.GONE);
//        finishFragment();

        Intent i = new Intent(ActivitySignUp.this, VerifyAccountActivity.class);
        i.putExtra("phone", usernameTxt.getText().toString());
        i.putExtra("name", passTxt.getText().toString());
        i.putExtra("email", emailTxt.getText().toString());
        i.putExtra("code", code);
        startActivity(i);
    }


    @Override
    public void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void showProgress() {
        dialogLoading.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogLoading.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
//        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onErrorAuthorization() {

    }


    @OnClick({R.id.login_btn, R.id.tvSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:

                String phone = usernameTxt.getText().toString();
                presenter.signUp(phone);
                break;
            case R.id.tvSignup:
                finish();
                break;
        }
    }

    @OnClick(R.id.icBack)
    public void onViewBackClicked() {
        onBackPressed();
    }
}
