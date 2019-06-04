package com.skynet.psi.ui.splash;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.skynet.psi.R;
import com.skynet.psi.ui.auth.AuthActivity;
import com.skynet.psi.ui.auth.login.LoginActivity;
import com.skynet.psi.ui.category.listProduct.ListProductActivity;
import com.skynet.psi.ui.main.MainActivity;
import com.skynet.psi.ui.views.ProgressDialogCustom;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity implements SlideContract.View {


    @BindView(R.id.imageView)
    ImageView imageView;
    private ProgressDialogCustom dialogCustom;
    private SlideContract.PresenterI presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        dialogCustom = new ProgressDialogCustom(this);
        presenter = new SlidePresenterI(this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                if (!AppController.getInstance().getmSetting().getBoolean("isShown", false)) {
//////                    startActivity(new Intent(SplashActivity.this, ShowcaseActivity.class));
////                    return;
////                } else {
////                }
//            }
//        }, 500);
//        Profile profile =  new Profile();
////        profile.setId("3ed572a6-0278-11e9-b7ce-005056b2404b");  //son
////        profile.setId("b307b18c-09af-11e9-b7ce-005056b2404b");  //tram
//        profile.setId("663ccc4b-0278-11e9-b7ce-005056b2404b");  //tam
//        AppController.getInstance().setmProfileUser(profile);
        presenter.getInfor();

//        getDialogProgress().hideDialog();
//        MainApplication.getInstance().setDay(day);


//        Spannable wordtoSpan = new SpannableString("VINENGLISH");
//        wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#ecac1d")), 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tvName.setText(wordtoSpan);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            tvName.setText(Html.fromHtml(getString(R.string.splash_name), Html.FROM_HTML_MODE_LEGACY));
//        } else {
//            tvName.setText(Html.fromHtml(getString(R.string.splash_name)));
//        }
    }


    @Override
    public void onSuccessGetInfor() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void showProgress() {
        dialogCustom.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogCustom.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//        finish();
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void onErrorAuthorization() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//        finish();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
