package com.skynet.psi.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.Cart;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.connect.ConnectActivity;
import com.skynet.psi.ui.detailBooking.ActivityBookingDetail;
import com.skynet.psi.ui.views.ProgressDialogCustom;
import com.skynet.psi.utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListHistoryActivity extends BaseActivity implements ICallback, XRecyclerView.LoadingListener, ListProductContract.View {
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.tvTitleToolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.rcv)
    XRecyclerView rcv;
    private static int TYPE_LOADMORE = 1;
    private static int TYPE_REFREESH = 0;
    @BindView(R.id.radDaily)
    RadioButton radDaily;
    @BindView(R.id.radWeek)
    RadioButton radWeek;
    @BindView(R.id.radMonth)
    RadioButton radMonth;
    @BindView(R.id.radGroup)
    RadioGroup radGroup;

    private int requestType;
    private int index = 0;
    private List<Booking> list;
    private AdapterProduct adapter;
    ListProductContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    boolean isFirstShow = true;

    @Override
    protected int initLayout() {
        return R.layout.activity_history;
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
        rcv.setPullRefreshEnabled(true);
        rcv.setLoadingMoreEnabled(false);
        rcv.setAdapter(adapter);
        rcv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rcv.setRefreshProgressStyle(ProgressStyle.BallPulse);
        rcv.setLimitNumberToCallLoadMore(10);
        rcv.setLoadingListener(this);

        presenter = new ListProductPresenter(this);
//        onRefresh();
        radDaily.toggle();

    }

    @Override
    protected void initViews() {
        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                list.clear();
                adapter.notifyDataSetChanged();
                onRefresh();
            }
        });
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
        if (list.get(pos).getActive() >= 4) {
            Intent i = new Intent(ListHistoryActivity.this, ActivityBookingDetail.class);
            i.putExtra(AppConstant.MSG, list.get(pos).getBooking_id());
            startActivityForResult(i, 1000);
            return;
        }
        Intent i = new Intent(ListHistoryActivity.this, ConnectActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(AppConstant.MSG, list.get(pos));
        i.putExtra(AppConstant.BUNDLE, b);
        startActivityForResult(i, 1000);
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
        if (radGroup.getCheckedRadioButtonId() == radDaily.getId()) {
            index = 1;
        } else if (radGroup.getCheckedRadioButtonId() == radWeek.getId()) {
            index = 2;

        } else {
            index = 3;
        }
        presenter.getListProduct(index);
//        presenter.getCart();
    }

    @Override
    public void onLoadMore() {
        requestType = TYPE_LOADMORE;
        presenter.getListProduct(index);
    }

    @Override
    public void onSucessGetListProduct(List<Booking> list, int index) {
        if (requestType == TYPE_REFREESH) {
            this.list.clear();
        }
        if (!list.isEmpty()) {
            this.list.addAll(list);
            adapter.clearCache();
            adapter.notifyDataSetChanged();
        } else {
            rcv.setNoMore(true);
        }
        this.index = index;
        rcv.loadMoreComplete();
        rcv.refreshComplete();
    }

    @Override
    public void onSucessGetCart(Cart cart) {
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
