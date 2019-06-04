package com.skynet.psi.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;
import com.skynet.psi.R;
import com.skynet.psi.interfaces.ICallback;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

public class DialogConnection {
    Context context;
    private Dialog mProgressDialog;
    TextView tvTitle;
    CardView cardView;

    public DialogConnection(@NonNull Context context,  final ICallback iCallback) {
        this.context = context;
        LayoutInflater factory = LayoutInflater.from(context);
        mProgressDialog = new Dialog(context, android.R.style.Theme_Light);
        mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = factory.inflate(R.layout.dialog_call, null, false);
        RippleBackground ripple = view.findViewById(R.id.content);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               hideDialog();
               iCallback.onCallBack(0);
           }
       });
        cardView = (CardView) view.findViewById(R.id.cardView);
        mProgressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgressDialog.setContentView(view);
        mProgressDialog.setCancelable(false);
        ripple.startRippleAnimation();
    }

    public void setText(String text) {
        tvTitle.setText(text);
    }

    public void showDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    public void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void hideBtnCancel() {
        cardView.setVisibility(View.GONE);
    }
}
