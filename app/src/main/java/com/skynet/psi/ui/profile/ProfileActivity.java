package com.skynet.psi.ui.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;

import com.skynet.psi.R;
import com.skynet.psi.application.AppController;
import com.skynet.psi.models.Profile;
import com.skynet.psi.ui.auth.updateProfile.ActivityProfileUpdate;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.main.MainActivity;
import com.skynet.psi.ui.privacy.PrivacyActivity;
import com.skynet.psi.ui.privacy.TermActivity;
import com.skynet.psi.ui.views.Glide4Engine;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.iwf.photopicker.PhotoPicker;

public class ProfileActivity extends BaseActivity implements UploadContract.View, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.imageView13)
    ImageView imageView13;
    @BindView(R.id.imgAvt)
    CircleImageView imgAvt;
    @BindView(R.id.tvNameProfile)
    TextView tvNameProfile;
    @BindView(R.id.tvPayment)
    TextView tvPayment;
    @BindView(R.id.textView50)
    TextView textView50;
    @BindView(R.id.view15)
    View view15;
    @BindView(R.id.constraintLayout7)
    ConstraintLayout constraintLayout7;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvTitleAddress)
    TextView tvTitleAddress;
    @BindView(R.id.viewAddress)
    View viewAddress;
    @BindView(R.id.constraintLayout10)
    ConstraintLayout constraintLayout10;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvTitleEmail)
    TextView tvTitleEmail;
    @BindView(R.id.viewEmail)
    View viewEmail;
    @BindView(R.id.constraintLayout11)
    ConstraintLayout constraintLayout11;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvTitlePhone)
    TextView tvTitlePhone;
    @BindView(R.id.viewPhone)
    View viewPhone;
    @BindView(R.id.constraintLayout8)
    ConstraintLayout constraintLayout8;
    @BindView(R.id.tvPrivacy)
    TextView tvPrivacy;
    @BindView(R.id.tvTerm)
    TextView tvTerm;
    @BindView(R.id.btnLogout)
    TextView btnLogout;
    @BindView(R.id.imageView15)
    ImageView imageView15;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private UploadContract.Presenter presenter;

    @Override
    protected int initLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initVariables() {
        presenter = new UploadPresenter(this);
        presenter.getInfor();
    }

    @Override
    protected void initViews() {
        swipe.setOnRefreshListener(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }

    @Override
    public void onSuccessGetInfor() {
        Profile profile = AppController.getInstance().getmProfileUser();
        tvPhone.setText(profile.getPhone() +" (Đã xác thực)");
        tvAddress.setText(profile.getAddress());
        tvEmail.setText("Tất cả thông báo");
        tvPayment.setText("Tiền mặt");
        tvNameProfile.setText(profile.getName());
        if (profile.getAvatar() != null && !profile.getAvatar().isEmpty()) {
            Picasso.with(this).load(profile.getAvatar()).fit().centerCrop().into(imgAvt);
        }
    }

    @Override
    public void onSucessUploadAvat() {
        setResult(RESULT_OK);
    }

    @Override
    public Context getMyContext() {
        return this;
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
    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imgAvt)
    public void onClickAvt() {
        choosePhoto();
    }

    @OnClick({R.id.imageView13, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView13:
                onBackPressed();
                break;
            case R.id.btnLogout:
                logOut();
                break;
        }
    }

    @Override
    public void onRefresh() {
        presenter.getInfor();
    }

    private void choosePhoto() {
        Matisse.from(ProfileActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(9)
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(PhotoPicker.REQUEST_CODE);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
//                    PhotoPicker.builder()
//                            .setPhotoCount(1)
//                            .setShowCamera(true)
//                            .setShowGif(true)
//                            .setPreviewEnabled(false)
//                            .start(ProfileActivity.this, PhotoPicker.REQUEST_CODE);

//                    ImagePicker.create(ProfileActivity.this).single() // Activity or Fragment.
//                             .imageLoader(new ImageLoader() {
//                                 @Override
//                                 public void loadImage(String path, ImageView imageView, ImageType imageType) {
//
//                                 }
//                             })
//                            .start();
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
//
//        Matisse.from(this)
//                .choose(MimeType.ofAll())
//                .countable(true)
//                .maxSelectable(1)
////                .captureStrategy(new CaptureStrategy(true, "com.skynet.psi.provider","Pictures"))
////                .capture(true)
////                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
////                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f)
//                .imageEngine(new Glide4Engine())
//                .forResult(PhotoPicker.REQUEST_CODE);
    }

    private boolean checkPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 111:
                if (grantResults.length > 2 && grantResults[0] != PackageManager.PERMISSION_GRANTED && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    checkPermissionGranted();
                    return;
                } else {
                    choosePhoto();
                }
                return;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {

            return;
        }
//        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
//            // Get a list of picked images
//            List<Image> images = ImagePicker.getImages(data);
//            // or get a single image only
//            Image image = ImagePicker.getFirstImageOrNull(data);
//            File fileImage = new File(image.getPath());
//            if (!fileImage.exists()) {
//                Toast.makeText(this, "File không tồn tại.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            CropImage.activity(Uri.fromFile(fileImage))
//                    .setAspectRatio(4, 3)
////                            .setRequestedSize(800, 800, CropImageView.RequestSizeOptions.RESIZE_EXACT)
//                    .start(this);
//            return;
//        }
//        if (requestCode == PhotoPicker.REQUEST_CODE && resultCode == RESULT_OK) {
//            if (data != null) {
//                ArrayList<String> photos =
//                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                for (String urlPath : photos) {
//                    File fileImage = new File(urlPath);
//                    if (!fileImage.exists()) {
//                        Toast.makeText(this, "File không tồn tại.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    break;
//                }
//
////                listImage.addAll(listPickup);
////                adapterPhoto.notifyItemInserted(adapterPhoto.getItemCount() - 1);
////                recyclerView2.smoothScrollToPosition(adapterPhoto.getItemCount() - 1);
////                if (postToEdit != null && postToEdit.getPost() != null) {
////                    presenter.addPhoto(listPickup, postToEdit.getPost().getId());
////                }
//
//            }
            if (requestCode == PhotoPicker.REQUEST_CODE && resultCode == RESULT_OK) {
                if (data != null) {
                    List<String> mSelected;
                    mSelected = Matisse.obtainPathResult(data);
                    Log.d("Matisse", "mSelected: " + mSelected);
//                ArrayList<String> photos =
//                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    for (String urlPath : mSelected) {
                        File fileImage = new File(urlPath);
                        if (!fileImage.exists()) {
                            Toast.makeText(this, "File không tồn tại.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        CropImage.activity(Uri.fromFile(fileImage))
                                .setAspectRatio(4, 3)
//                            .setRequestedSize(800, 800, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                                .start(this);
                        break;
                    }
                }

            }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File file = new File(resultUri.getPath());
                Picasso.with(this).load(file).fit().centerCrop().into(imgAvt);
                presenter.upload(file, 1);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }


    }

    @OnClick({R.id.tvPrivacy, R.id.tvTerm})
    public void onViewPrivacyClicked(View view) {
        switch (view.getId()) {
            case R.id.tvPrivacy:
                startActivity(new Intent(ProfileActivity.this, PrivacyActivity.class));
                break;
            case R.id.tvTerm:
                startActivity(new Intent(ProfileActivity.this, TermActivity.class));

                break;
        }
    }

    @OnClick({R.id.constraintLayout7, R.id.constraintLayout10, R.id.constraintLayout11, R.id.constraintLayout8})
    public void onViewUpdateClicked(View view) {
        switch (view.getId()) {
            case R.id.constraintLayout7:
                break;
            case R.id.constraintLayout10:
                break;
            case R.id.constraintLayout11:
                break;
            case R.id.constraintLayout8:
                break;
        }
        startActivity(new Intent(ProfileActivity.this, ActivityProfileUpdate.class));
    }
}
