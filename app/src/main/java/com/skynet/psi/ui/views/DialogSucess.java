package com.skynet.psi.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

import com.skynet.psi.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by thaopt on 8/28/17.
 */

public class DialogSucess extends Dialog {


    @BindView(R.id.textView17)
    TextView textView17;
    @BindView(R.id.textView55)
    TextView textView55;
    @BindView(R.id.btnConfirm)
    TextView btnConfirm;
    private int mType = 0;
    private Context mContext;
    private DialogOneButtonClickListener mListener;
    public DialogSucess(@NonNull Context context, DialogOneButtonClickListener listener) {
        super(context);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_sucess);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        this.mListener = listener;
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        textView55.setText(Html.fromHtml(mContext.getString(R.string.sucees)));

    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        mListener.okClick();

    }

    @Override
    public void setOnCancelListener(@Nullable OnCancelListener listener) {
        super.setOnCancelListener(listener);
        mListener.okClick();

    }

    public void setType(int type) {
        this.mType = type;
    }

    @OnClick(R.id.btnConfirm)
    public void onViewClicked() {
        dismiss();
        if (mListener != null) {
            int type = 0;
            mListener.okClick();
        }
    }

    //callback
    public interface DialogOneButtonClickListener {
        void okClick();
    }


    public void setDialogOneButtonClick(DialogOneButtonClickListener listener) {
        this.mListener = listener;
    }
}
