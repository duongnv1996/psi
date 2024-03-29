package com.skynet.psi.ui.profile;

import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.io.File;

import okhttp3.MultipartBody;

public interface UploadContract {

    interface Presenter extends IBasePresenter,Listener {
        void getInfor();
        void upload(File file, int type);
    }

    interface Interactor {

        void getInfor();
        void upload(File file, MultipartBody.Part part, int type);
    }

    interface View extends BaseView{

        void onSuccessGetInfor();
        void onSucessUploadAvat();

    }
    interface Listener extends OnFinishListener {

        void onSuccessGetInfor();
        void onSucessUploadAvat();
    }

}
