package com.skynet.psi.ui.feedback;


import com.skynet.psi.ui.base.Presenter;

import java.io.File;
import java.util.List;

public class FeedbackPresenter extends Presenter<FeedbackContract.View> implements FeedbackContract.Presenter {
    FeedbackContract.Interactor interactor;

    public FeedbackPresenter(FeedbackContract.View view) {
        super(view);
        interactor = new FeedbackImplRemote(this);
    }



    @Override
    public void onDestroyView() {
        view = null;
    }




    @Override
    public void onErrorApi(String message) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onErrorApi(message);
        }
    }

    @Override
    public void onError(String message) {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onError(message);
        }
    }

    @Override
    public void onErrorAuthorization() {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.onErrorAuthorization();
        }
    }

    @Override
    public void addFeedback(String content, String address, String time,String type,  List<File> fileList) {
        if(isAvaliableView()){
            view.showProgress();
            interactor.addFeedback(content,address,time,type,fileList);
        }
    }

    @Override
    public void addFeedbackSucess() {
        if(isAvaliableView()){
            view.hiddenProgress();
            view.addFeedbackSucess();
        }
    }

}
