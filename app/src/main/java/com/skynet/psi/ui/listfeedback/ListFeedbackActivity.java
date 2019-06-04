package com.skynet.psi.ui.listfeedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Feedback;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.detailfeedback.DetailFeedbackActivity;
import com.skynet.psi.ui.views.ProgressDialogCustom;
import com.skynet.psi.utils.AppConstant;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListFeedbackActivity extends BaseActivity implements ICallback, XRecyclerView.LoadingListener, ListProductContract.View {
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.tvTitleToolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.rcv)
    XRecyclerView rcv;
    private static int TYPE_LOADMORE = 1;
    private static int TYPE_REFREESH = 0;

    private int requestType;
    private int index = 0;
    private List<Feedback> list;
    private AdapterProduct adapter;
    ListProductContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    boolean isFirstShow = true;

    @Override
    protected int initLayout() {
        return R.layout.activity_list_feedback;
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        rcv.setLayoutManager(layoutManager);
        rcv.setHasFixedSize(true);
        list = new ArrayList<>();
        adapter = new AdapterProduct(list, this, this);
        rcv.setPullRefreshEnabled(false);
        rcv.setLoadingMoreEnabled(false);
        rcv.setAdapter(adapter);
        rcv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rcv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        rcv.setLimitNumberToCallLoadMore(10);
        rcv.setLoadingListener(this);
        presenter = new ListProductPresenter(this);
        onRefresh();

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

    @OnClick({R.id.imgHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                onBackPressed();
                break;

        }
    }


    @Override
    public void onCallBack(int pos) {
        Intent i = new Intent(ListFeedbackActivity.this, DetailFeedbackActivity.class);
        Bundle b  =  new Bundle();
        b.putParcelable(AppConstant.MSG,list.get(pos));
        i.putExtra(AppConstant.BUNDLE,b);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        requestType = TYPE_REFREESH;
        presenter.getListProduct(index);
//        presenter.getCart();
    }

    @Override
    public void onLoadMore() {
        requestType = TYPE_LOADMORE;
        presenter.getListProduct(index);
    }

    @Override
    public void onSucessGetListProduct(List<Feedback> list, int index) {
        if (requestType == TYPE_REFREESH) {
            this.list.clear();
        }
        if (!list.isEmpty()) {
            this.list.addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            rcv.setNoMore(true);
        }
        this.index = index;
        rcv.loadMoreComplete();
        rcv.refreshComplete();
    }


    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void showProgress() {
        if (isFirstShow) {
            dialogLoading.showDialog();
            isFirstShow = false;
        }
    }

    @Override
    public void hiddenProgress() {
        dialogLoading.hideDialog();

    }

    @Override
    public void onErrorApi(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onErrorAuthorization() {

    }
}
