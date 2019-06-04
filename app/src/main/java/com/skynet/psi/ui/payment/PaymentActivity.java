package com.skynet.psi.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.skynet.psi.R;
import com.skynet.psi.models.Booking;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentActivity extends BaseActivity {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.imgBack)
    ImageView imgBack;
    @BindView(R.id.textView54)
    TextView textView54;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.radWallet)
    RadioButton radWallet;
    @BindView(R.id.radBank)
    RadioButton radBank;
    @BindView(R.id.radCash)
    RadioButton radCash;
    Booking booking;

    @Override
    protected int initLayout() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
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
                Intent i = new Intent();
                i.putExtra(AppConstant.MSG, 1);
                setResult(RESULT_OK,i);
                finish();
                break;
        }
    }
}
