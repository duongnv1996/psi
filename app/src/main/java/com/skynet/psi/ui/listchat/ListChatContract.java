package com.skynet.psi.ui.listchat;

import com.skynet.psi.models.ChatItem;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface ListChatContract {
    interface View extends BaseView{
        void onSucessGetListChat(List<ChatItem> list);
        void onSucessConfirmChat();
    }
    interface Presenter extends IBasePresenter,Listener{
        void getListChat();
        void confirmHired(int idPost, String idHost, String idRent);
        void deleteChat(String idHost, int idPost);
    }
    interface Interactor {
        void getListChat();
        void confirmHired(int idPost, String idHost, String idRent);
        void deleteChat(String idHost, int idPost);
    }
    interface Listener extends OnFinishListener{
        void onSucessGetListChat(List<ChatItem> list);
        void onSucessConfirmChat();
        void onSucessDelete();
    }
}
