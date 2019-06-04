package com.skynet.psi.ui.feedback;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.skynet.psi.R;
import com.skynet.psi.interfaces.SnackBarCallBack;
import com.skynet.psi.ui.base.BaseActivity;
import com.skynet.psi.ui.views.ProgressDialogCustom;
import com.skynet.psi.utils.AppConstant;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;


public class FeedbackActivity extends BaseActivity implements FeedbackContract.View {
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
    @BindView(R.id.imageView33)
    ImageView imageView33;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.textView77)
    TextView textView77;
    List<File> list;
    AdapterPhoto adapterPhoto;
    private FeedbackContract.Presenter presenter;
    private ProgressDialogCustom dialog;

    @Override
    protected int initLayout() {
        return R.layout.activity_make_feedback;
    }

    @Override
    protected void initVariables() {
        list = new ArrayList<>();
        adapterPhoto = new AdapterPhoto(list, this);
        rcv.setAdapter(adapterPhoto);
        presenter = new FeedbackPresenter(this);
    }

    @Override
    protected void initViews() {
        rcv.setLayoutManager(new LinearLayoutManager(FeedbackActivity.this, RecyclerView.HORIZONTAL, false));
        rcv.setHasFixedSize(true);
        dialog = new ProgressDialogCustom(this);
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

    @OnClick({R.id.imgHome, R.id.login_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                onBackPressed();
                break;
            case R.id.login_btn:
                if (list == null || list.size() < 4) {
                    showToast("Vui lòng đính kèm ít nhất 4 hình ảnh", AppConstant.NEGATIVE);
                    return;
                }
                presenter.addFeedback(editText7.getText().toString(), editText8.getText().toString(), editText9.getText().toString(),"Yêu cầu bồi thường" ,list);
                break;
        }
    }

    @OnClick({R.id.frameLayout})
    public void onViewViewClicked(View view) {
        choosePhoto();
    }

    private void choosePhoto() {

        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    EasyImage.openChooserWithDocuments(FeedbackActivity.this, "Chọn ảnh ", 0);
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
////                .captureStrategy(new CaptureStrategy(true, "com.skynet.psitech.provider","Pictures"))
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
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                if (!imageFile.exists()) {
                    Toast.makeText(FeedbackActivity.this, "File không tồn tại.", Toast.LENGTH_SHORT).show();
                    return;
                }
                CropImage.activity(Uri.fromFile(imageFile))
                        .setAspectRatio(4, 3)
//                            .setRequestedSize(800, 800, CropImageView.RequestSizeOptions.RESIZE_EXACT)
                        .start(FeedbackActivity.this);
            }


        });
//        if (requestCode == PhotoPicker.REQUEST_CODE && resultCode == RESULT_OK) {
//            if (data != null) {
//                List<String> mSelected;
//                mSelected = Matisse.obtainPathResult(data);
//                Log.d("Matisse", "mSelected: " + mSelected);
////                ArrayList<String> photos =
////                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                for (String urlPath : mSelected) {
//                    File fileImage = new File(urlPath);
//                    if (!fileImage.exists()) {
//                        Toast.makeText(this, "File không tồn tại.", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    CropImage.activity(Uri.fromFile(fileImage))
//                            .setAspectRatio(4, 3)
////                            .setRequestedSize(800, 800, CropImageView.RequestSizeOptions.RESIZE_EXACT)
//                            .start(this);
//                    break;
//                }
//            }
//
//        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                File file = new File(resultUri.getPath());
                list.add(file);
                adapterPhoto.notifyItemInserted(adapterPhoto.getItemCount());
                rcv.smoothScrollToPosition(adapterPhoto.getItemCount());
                // presenter.upload(file, 1);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }


    }

    @Override
    public void addFeedbackSucess() {
        showToast("Hệ thống đã ghi nhận phản hồi của bạn.", AppConstant.POSITIVE, new SnackBarCallBack() {
            @Override
            public void onClosedSnackBar() {
                finish();
            }
        });
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void showProgress() {
        dialog.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialog.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onError(String message) {
        showToast(message, AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }
}
