package com.skynet.psi.ui.detailfeedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;
import com.skynet.psi.models.Feedback;
import com.skynet.psi.ui.ViewPhotoActivity;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.utils.AppConstant;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailFeedbackActivity extends BaseActivity implements ICallback {


    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.tvTitleToolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.textView54)
    TextView textView54;
    @BindView(R.id.rcv)
    RecyclerView rcv;
    @BindView(R.id.imageView30)
    ImageView imageView30;
    @BindView(R.id.editText7)
    EditText editText7;
    @BindView(R.id.textView70)
    TextView textView70;
    @BindView(R.id.editText8)
    EditText editText8;
    @BindView(R.id.editText9)
    EditText editText9;
    @BindView(R.id.textView76)
    TextView textView76;
    @BindView(R.id.textView73)
    TextView textView73;
    @BindView(R.id.textView77)
    TextView textView77;

    private Feedback feedback;
    private List<Feedback.List_imageEntity> listPhoto;

    @Override
    protected int initLayout() {
        return R.layout.activity_detail_feedback;
    }

    @Override
    protected void initVariables() {
        feedback = getIntent().getBundleExtra(AppConstant.BUNDLE).getParcelable(AppConstant.MSG);
        if (feedback != null) {
            editText7.setText(feedback.getTime_report());
            editText8.setText(feedback.getAddress());
            editText9.setText(feedback.getContent());
            textView77.setText("#" + feedback.getBooking_id() + " - Mã công việc");
            listPhoto = feedback.getList_image();
            if (listPhoto != null)
                rcv.setAdapter(new AdapterPhoto(listPhoto, this, this));
        }
    }

    @Override
    protected void initViews() {
        rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcv.setHasFixedSize(true);
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
        Intent i = new Intent(DetailFeedbackActivity.this, ViewPhotoActivity.class);
        i.putExtra(AppConstant.MSG, listPhoto.get(pos).getImg());
        startActivity(i);
    }
}
