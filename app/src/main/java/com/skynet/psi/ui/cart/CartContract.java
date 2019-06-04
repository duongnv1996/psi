package com.skynet.psi.ui.cart;

import com.skynet.psi.models.Cart;
import com.skynet.psi.models.Promotion;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface CartContract  {
    interface View extends BaseView {
        void onSucessGetCart(Cart cart);
        void onSucessCheckPromotion(Promotion promotion);
    }
    interface Presenter extends IBasePresenter,Listener{
        void getCart();
        void deleteItem(int idProduct);
        void updateItem(int idProduct,int number,String note);
        void checkPromotion(String promo);
    }
    interface Interactor{
        void getCart();
        void deleteItem(int idProduct);
        void updateItem(int idProduct,int number,String note);
        void checkPromotion(String promo);

    }
    interface Listener extends OnFinishListener{
        void onSucessGetCart(Cart cart);
        void onSucessCheckPromotion(Promotion promotion);
    }
}
