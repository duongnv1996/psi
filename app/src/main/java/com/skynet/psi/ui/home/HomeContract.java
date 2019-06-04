package com.skynet.psi.ui.home;


import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.models.Banner;
import com.skynet.psi.models.Category;
import com.skynet.psi.models.HomeResponse;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.News;
import com.skynet.psi.models.Product;
import com.skynet.psi.models.ProductResponse;
import com.skynet.psi.models.Profile;
import com.skynet.psi.models.Suggestion;
import com.skynet.psi.models.franchise;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

import java.util.List;

public interface HomeContract {
    interface View extends BaseView {
        void onSuccessGetInfor();
        void onGetAddressFromLatlngSuccess(MyPlace place);
        void onSucessGetFranchise(List<franchise> list);


    }

    interface PresenterI extends IBasePresenter, Listener {
        void getInfor();
        void getAddressFromLatlng(LatLng latLng, Geocoder geocoder);
        void updateCar(int type,String name);
        void getFranchise();
    }

    interface Interactor {
        void doGetInfor(String profileInfor);
        void getAddressFromLatlng(LatLng latLng, Geocoder geocoder);
        void updateCar(int type,String name);
        void getFranchise();

    }

    interface Listener extends OnFinishListener {
        void onSuccessGetInfor(Profile profile);
        void onGetAddressFromLatlngSuccess(MyPlace place);
        void onSucessGetFranchise(List<franchise> list);
    }
}
