package com.skynet.psi.ui.feedback;


import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.io.File;
import java.util.List;

public interface FeedbackContract {
    interface View extends BaseView {
        void addFeedbackSucess();
    }

    interface Presenter extends IBasePresenter, Listener {
        void addFeedback( String content, String address, String time,  String type, List<File> fileList);
    }

    interface Interactor {
        void addFeedback( String content, String address, String time,  String type, List<File> fileList);

    }

    interface Listener extends OnFinishListener {
        void addFeedbackSucess();
    }
}
