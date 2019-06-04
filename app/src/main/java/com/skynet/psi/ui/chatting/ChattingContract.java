package com.skynet.psi.ui.chatting;


import com.skynet.psi.models.Message;
import com.skynet.psi.models.Post;
import com.skynet.psi.network.socket.SocketClient;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface ChattingContract {
    interface View extends BaseView {
        void onSuccessGetMessages(List<Message> list, Post post);
        void onSuccessSendMessage(Message mess);

    }

    interface Presenter extends IBasePresenter,ChattingListener{
        void getMessages(int udId, int hostId, int idPost);
        void sendMessage(int idPost, int idUser, int hostId, String content, SocketClient socketClient, int attach);

    }

    interface Interactor {
        void getMessages(int udId, int idShop, int idPost);
        void sendMessage(int idPost, int idUser, int idShop, String content, String time, int attach);

    }

    interface ChattingListener extends OnFinishListener {
        void onSuccessGetMessages(List<Message> list, Post post);
        void onSuccessSendMessage(Message message);

    }
}
