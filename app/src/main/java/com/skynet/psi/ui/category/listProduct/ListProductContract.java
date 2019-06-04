package com.skynet.psi.ui.category.listProduct;

import com.skynet.psi.models.Cart;
import com.skynet.psi.models.Nearby;
import com.skynet.psi.models.Product;
import com.skynet.psi.models.Shop;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface ListProductContract {
    interface View extends BaseView {
        void onSucessGetListProduct(List<Product> listProduct, int index);
        void onSucessGetCart(Cart cart);
        void onSucessGetListShop(List<Shop> list);
        void onSucessGetListFriendShop(List<Shop> list);
    }

    interface Presenter extends IBasePresenter ,Listener{
        void getListProduct(int id,int idcate,double lat,double lng);
        void toggleFav(int id, boolean toggle);        void getCart();

    }

    interface Interactor {
        void getListProduct(int id,int idCate,double lat,double lng);
        void toggleFav(int id, boolean toggle);        void getCart();

    }

    interface Listener extends OnFinishListener {
        void onSucessGetListProduct(Nearby response);        void onSucessGetCart(Cart cart);

    }
}
