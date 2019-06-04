package com.skynet.psi.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LocationUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.skynet.psi.R;
import com.skynet.psi.application.AppController;
import com.skynet.psi.interfaces.FragmentCallBackTitle;
import com.skynet.psi.models.Profile;
import com.skynet.psi.ui.connect.ConnectActivity;
import com.skynet.psi.ui.contact.ContactUsActivity;
import com.skynet.psi.ui.feedback.FeedbackActivity;
import com.skynet.psi.ui.listfeedback.ListFeedbackActivity;
import com.skynet.psi.ui.promotion.PromotionActivity;
import com.skynet.psi.ui.scanqr.ScannerQr;
import com.skynet.psi.ui.auth.updateProfile.ActivityProfileUpdate;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.cart.CartActivity;
import com.skynet.psi.ui.schedule.ListHistoryActivity;
import com.skynet.psi.ui.listProduct.ListProductActivity;
import com.skynet.psi.ui.listchat.ListChatActivity;
import com.skynet.psi.ui.news.NotificationActivity;
import com.skynet.psi.ui.profile.ProfileActivity;
import com.skynet.psi.ui.views.ViewpagerNotSwipe;
import com.skynet.psi.utils.AppConstant;
import com.skynet.psi.utils.CommomUtils;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import q.rorbin.badgeview.Badge;

public class MainActivity extends BaseActivity implements OptionBottomSheet.MoreOptionCallback, ContactContract.View, FragmentCallBackTitle {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.viewpager)
    ViewpagerNotSwipe viewpager;
    @BindView(R.id.bnve)
    RadioGroup radGroup;
    @BindView(R.id.layoutRootViewpager)
    FrameLayout layoutRootViewpager;
    @BindView(R.id.imgAvatarProfile)
    CircleImageView imgAvatarProfile;
    @BindView(R.id.tvNameProfile)
    TextView tvNameProfile;
    @BindView(R.id.nav_home)
    LinearLayout navHome;
    @BindView(R.id.nav_fav)
    LinearLayout navFav;
    @BindView(R.id.nav_cart)
    LinearLayout navCart;
    @BindView(R.id.nav_history)
    LinearLayout navHistory;
    @BindView(R.id.nav_message)
    LinearLayout navMessage;
    @BindView(R.id.nav_news)
    LinearLayout navNews;
    @BindView(R.id.nav_help)
    LinearLayout navHelp;
    @BindView(R.id.nav_customer)
    LinearLayout navCustomer;
    @BindView(R.id.tvTitleToolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.imgRight)
    ImageView imgRight;
    private AdapterMainViewpager adapter;
    private boolean doubleBackToExitPressedOnce;
    private Badge badge;
    private ContactContract.Presenter presenter;
    private OptionBottomSheet optionBottomSheet;
    private OptionBottomSheet bottomAddFriendRequest;
    private String userIdRequestFriend;
    private OptionBottomSheet.MoreOptionCallback addFriendCallback = new OptionBottomSheet.MoreOptionCallback() {
        @Override
        public void onMoreOptionCallback() {
            if (userIdRequestFriend != null) {
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {
//        showDialogExpired();
        bottomAddFriendRequest = new OptionBottomSheet(this, addFriendCallback);
        presenter = new ContactPresenter(this);
        optionBottomSheet = new OptionBottomSheet(this, this);
        presenter.updateToken();
        radGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                imgRight.setImageResource(R.drawable.ic_qrcode);
                switch (checkedId) {
                    case R.id.btmNewest: {
                        viewpager.setCurrentItem(0);
                        break;
                    }
                    case R.id.btmShop: {
                        viewpager.setCurrentItem(1);
                        break;
                    }
                    case R.id.btmCategory: {
//                        viewpager.setCurrentItem(2);
                        // setTilte("Ngành hàng");
//                        startActivity(new Intent(MainActivity.this, com.skynet.psi.ui.Notification.NotificationActivity.class));
                        if(AppController.getInstance().getBooking()!=null){
                            Intent i = new Intent(MainActivity.this, ConnectActivity.class);
                            Bundle b = new Bundle();
                            b.putParcelable(AppConstant.MSG, AppController.getInstance().getBooking());
                            i.putExtra(AppConstant.BUNDLE, b);
                            startActivityForResult(i, 1000);
                        }else{
                            startActivity(new Intent(MainActivity.this, com.skynet.psi.ui.history.ListHistoryActivity.class));
                        }
                        break;
                    }
                    case R.id.btmFav: {
//                        viewpager.setCurrentItem(3);
                        //   setTilte("Yêu thích");
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        break;
                    }
                }
            }
        });
        bindUserData();
        radGroup.check(R.id.btmNewest);
    }

    @Override
    public void onSocketConnected() {
        super.onSocketConnected();

    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, null, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        ;
        viewpager.setAdapter(new AdapterMainViewpager(getSupportFragmentManager()));
        viewpager.setPagingEnabled(false);

//        viewpager.setCurrentItem(1);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        int count = AppController.getInstance().getmProfileUser().getMessage() + AppController.getInstance().getmProfileUser().getNoty();
//        if (count > 0)
//            addBadgeAt(2, count);
//        else if (badge != null)
//            badge.hide(true);

        if ((AppController.getInstance().getmProfileUser().getName().isEmpty() || AppController.getInstance().getmProfileUser().getEmail().isEmpty()) && !AppController.getInstance().getmSetting().getBoolean("show")) {
            startActivityForResult(new Intent(MainActivity.this, ActivityProfileUpdate.class), 1001);
        }
    }
    public void selectTab(int index){
        viewpager.setCurrentItem(index);
    }

    private void addBadgeAt(int position, int number) {
        // add badge
//        if (badge == null)
//            badge = new QBadgeView(this)
//                    .setBadgeNumber(number)
//                    .setGravityOffset(12, 2, true)
//                    .bindTarget(bnve.getBottomNavigationItemView(position))
//                    .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//                        @Override
//                        public void onDragStateChanged(int dragState, Badge badge, View targetView) {
////                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
////                            Toast.makeText(BadgeViewActivity.this, R.string.tips_badge_removed, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        else
//            badge.setBadgeNumber(number);
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


    private void bindUserData() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile != null) {
            tvNameProfile.setText(profile.getName());
            if (profile.getAvatar() != null && !profile.getAvatar().isEmpty()) {
                Picasso.with(this).load(profile.getAvatar()).fit().centerCrop().into(imgAvatarProfile);
            }
        } else {
            return;
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Nhấn BACK 2 lần để thoát", Toast.LENGTH_SHORT).show();
            selectTab(0);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        }
    }

    @Override
    public void onMoreOptionCallback() {
//        if (bnve.getCurrentItem() == 1)

    }


    @Override
    public Context getMyContext() {
        return null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

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

    @Override
    public void setTilte(String title) {
        tvTitleToolbar.setText(title);
    }

    @OnClick({R.id.nav_home, R.id.nav_fav, R.id.nav_cart, R.id.nav_history, R.id.nav_message, R.id.nav_news, R.id.nav_help, R.id.nav_setting,  R.id.nav_share,  R.id.nav_noti})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nav_home:
                radGroup.check(R.id.btmShop);
                break;
            case R.id.nav_fav:
//                radGroup.check(R.id.btmFav);
                break;
            case R.id.nav_cart:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_history:
                startActivity(new Intent(MainActivity.this, ListHistoryActivity.class));

                break;
            case R.id.nav_message:
                startActivity(new Intent(MainActivity.this, com.skynet.psi.ui.history.ListHistoryActivity.class));
                break;
            case R.id.nav_news:
                startActivity(new Intent(MainActivity.this, com.skynet.psi.ui.history.ListHistoryActivity.class));


                break;
            case R.id.nav_help:
        //                startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                CommomUtils.dialPhoneNumber(this,"19001000");
                break;
                case R.id.nav_noti:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));

                break;
            case R.id.nav_setting:
                startActivityForResult(new Intent(MainActivity.this, ListFeedbackActivity.class), 1000);
                break;

            case R.id.nav_share:
                logOut();
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);

    }

    public void openMenu() {
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick({R.id.imgHome, R.id.imgRight, R.id.imageView9})
    public void onViewToolbarClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.imgRight:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            startActivity(new Intent(MainActivity.this, ScannerQr.class));
                        } else {

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

                break;
            case R.id.imageView9:
                drawerLayout.closeDrawer(Gravity.LEFT);
                break;
        }
    }

    @OnClick({R.id.imgAvatarProfile, R.id.tvNameProfile})
    public void onViewProfileClicked(View view) {
        startActivityForResult(new Intent(MainActivity.this, ProfileActivity.class), 1000);
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            bindUserData();
        }

    }
}
