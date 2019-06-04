package com.skynet.psi.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.Spannable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.skynet.psi.R;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by thaopt on 8/28/17.
 */

public class DialogChooseCar extends Dialog {

    @BindView(R.id.textView17)
    TextView textView17;
    @BindView(R.id.radBike)
    RadioButton radBike;
    @BindView(R.id.radSedan)
    RadioButton radSedan;
    @BindView(R.id.radSUV)
    RadioButton radSUV;
    @BindView(R.id.radGroup)
    RadioGroup radGroup;
    @BindView(R.id.edtNameCar)
    EditText edtNameCar;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    private int mType = 0;
    private Context mContext;
    private DialogOneButtonClickListener mListener;


    public DialogChooseCar(@NonNull Context context, DialogOneButtonClickListener listener) {
        super(context);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_car);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        this.mListener = listener;
        ButterKnife.bind(this);
        initView();
        radBike.toggle();
    }

    private void initView() {

    }


    public void setType(int type) {
        this.mType = type;
    }

    @OnClick(R.id.btnConfirm)
    public void onViewClicked() {
        dismiss();
        if (mListener != null) {
            int type = 0;
            switch (radGroup.getCheckedRadioButtonId()) {
                case R.id.radBike: {
                    type = 0;
                    break;
                }
                case R.id.radSedan: {
                    type = 1;

                    break;
                }
                case R.id.radSUV: {
                    type = 2;

                    break;
                }
            }
            mListener.okClick(type, edtNameCar.getText().toString());
        }
    }

    //callback

    public interface DialogOneButtonClickListener {
        void okClick(int type, String name);
    }


    public void setDialogOneButtonClick(DialogOneButtonClickListener listener) {
        this.mListener = listener;
    }
}
