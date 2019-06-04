package com.skynet.psi.ui.auth.updateProfile;




import com.skynet.psi.models.Profile;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.io.File;

import okhttp3.MultipartBody;

public interface ProfileContract {
    interface View extends BaseView {
        void onSuccessGetInfor();
        void onSuccessUpdatedAvatar();
        void onSuccessUpdate();


    }
    interface Presenter extends IBasePresenter,OnFinishProfileListener{
        void getInfor();
        void uploadAvatar(File file);
        void update(String name, String email, String address);

    }

    interface Interactor {
        void doGetInfor(String profileInfor);
        void doUpdateAvatar(File file, MultipartBody.Part part);
        void update(String name, String email, String address);

    }

    interface OnFinishProfileListener extends OnFinishListener {
        void getInforSuccess(Profile profile);
        void notFoundInfor();
        void onSuccessUpdate();
        void onSuccessUpdatedAvatar();

    }
}
