package com.skynet.psi.ui.promotion;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.skynet.psi.R;
import com.skynet.psi.models.Promotion;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.views.ProgressDialogCustom;
import com.skynet.psi.utils.AppConstant;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PromotionActivity extends BaseActivity implements PromotionContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvSucess)
    TextView tvSucess;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.textView54)
    TextView textView54;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.imageView27)
    ImageView imageView27;
    private ProgressDialogCustom dialogLoading;
    private PromotionContract.Presenter presenter;
    private List<Promotion> promotions;
    private Timer timer;
    private Promotion promotionSeleted;

    @Override
    protected int initLayout() {
        return R.layout.activity_promotion;
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new PromotionPresenter(this);
        presenter.getPromotion();

        code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for (Promotion promotion : promotions) {
                            if (promotion.getCode().equals(s.toString())) {
                                promotionSeleted = promotion;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvSucess.setText("Giảm "+ promotion.getValue() + "%" +" cho hoá đơn của bạn.");

                                    }
                                });
                            }
                        }
                    }
                }, 600);
            }
        });
    }

    @Override
    public void onSucessGetPromotion(List<Promotion> list) {
        this.promotions = list;

    }

    @Override
    protected void onPause() {
        super.onPause();
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
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
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
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);

    }

    @Override
    public void onErrorAuthorization() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.imgBack, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.btnSubmit:
                if(promotionSeleted == null){
                    setResult(RESULT_CANCELED);
                }else{
                    Bundle b = new Bundle();
                    b.putParcelable(AppConstant.MSG,promotionSeleted);
                    Intent i = getIntent();
                    i.putExtra(AppConstant.BUNDLE,b);
                    setResult(RESULT_OK,i);
                    finish();
                }
                break;
        }
    }
}
