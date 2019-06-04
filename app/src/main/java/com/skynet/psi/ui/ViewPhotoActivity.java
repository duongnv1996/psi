package com.skynet.psi.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.jsibbold.zoomage.ZoomageView;

import com.skynet.psi.R;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewPhotoActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.img)
    ZoomageView img;

    @Override
    protected int initLayout() {
        return R.layout.activity_view_photo;
    }

    @Override
    protected void initVariables() {
        String message = getIntent().getStringExtra(AppConstant.MSG);
        if(message.contains("http")) {
            Picasso.with(this).load(message).into(img);
        }else{
            File file = new File(message);
            if(file.exists())
                Picasso.with(this).load(file).into(img);
        }
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

    @OnClick(R.id.back)
    public void onViewClicked() {
        onBackPressed();
    }
}
