package com.skynet.psi.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Category;
import com.skynet.psi.ui.base.BaseFragment;
import com.skynet.psi.ui.booking.BookingActivity;
import com.skynet.psi.ui.main.MainActivity;
import com.skynet.psi.utils.AppConstant;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListCategoryFragment extends BaseFragment implements CategoryContract.View, SwipeRefreshLayout.OnRefreshListener, ICallback {


    @BindView(R.id.rcvMore)
    RecyclerView rcvMore;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    private CategoryContract.Presenter presenter;

    private List<Category> listCategories;

    public static ListCategoryFragment newInstance() {
        Bundle args = new Bundle();
        ListCategoryFragment fragment = new ListCategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void doAction() {

    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
        swipe.setOnRefreshListener(this);
        rcvMore.setLayoutManager(new LinearLayoutManager(getContext()));
        tvTitle.setText(Html.fromHtml(getString(R.string.choose_service)));
    }

    @Override
    protected void initVariables() {
        presenter = new CategoryPresenter(this);
        presenter.getCategory();
    }

    @Override
    public void onSuccessGetListCategory(List<Category> categories) {
        this.listCategories = categories;
        rcvMore.setAdapter(new AdapterCategory(listCategories, getContext(), this));
    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    public void showProgress() {
        swipe.setRefreshing(true);
    }

    @Override
    public void hiddenProgress() {
        swipe.setRefreshing(false);
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpiredToken();
    }


    @Override
    public void onRefresh() {
        presenter.getCategory();
    }

    @Override
    public void onCallBack(int pos) {
        Intent i = new Intent(getActivity(), BookingActivity.class);
        i.putExtra(AppConstant.MSG, listCategories.get(pos).getId());
        startActivityForResult(i, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && data != null) {
            ((MainActivity) getActivity()).selectTab(2);
        }
    }

    @OnClick(R.id.imgHome)
    public void onViewClicked() {
        ((MainActivity) getActivity()).openMenu();

    }
}
