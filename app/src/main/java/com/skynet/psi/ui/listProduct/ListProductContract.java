package com.skynet.psi.ui.listProduct;

import com.skynet.psi.models.Cart;
import com.skynet.psi.models.Product;
import com.skynet.psi.models.ProductResponse;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface ListProductContract {
    interface View extends BaseView {
        void onSucessGetListProduct(List<Product> list,int index);        void onSucessGetCart(Cart cart);

    }

    interface Presenter extends IBasePresenter ,Listener{
        void getListProduct(int id);
        void toggleFav(int id, boolean toggle);        void getCart();

    }

    interface Interactor {
        void getListProduct(int id);
        void toggleFav(int id, boolean toggle);        void getCart();

    }

    interface Listener extends OnFinishListener {
        void onSucessGetListProduct(ProductResponse response);
        void onSucessGetCart(Cart cart);

    }
}
