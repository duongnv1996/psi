package com.skynet.psi.ui.location;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.ui.base.BaseView;
import com.skynet.psi.ui.base.IBasePresenter;
import com.skynet.psi.ui.base.OnFinishListener;

public interface LocationContract {
    interface View extends BaseView {
        void onSuccessGetMyAddress(MyPlace response);
    }

    interface Presenter extends IBasePresenter, Listener {
        void getMyAddress(LatLng latLng);
    }

    interface Interactor {
        void getMyAddress(LatLng latLng);

    }

    interface Listener extends OnFinishListener {
        void onSuccessGetMyAddress(MyPlace response);
    }
}
