package com.skynet.psi.ui.booking;

import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.skynet.psi.application.AppController;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.Cart;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.ProductResponse;
import com.skynet.psi.models.Profile;
import com.skynet.psi.models.Service;
import com.skynet.psi.network.api.ApiResponse;
import com.skynet.psi.network.api.ApiService;
import com.skynet.psi.network.api.ApiUtil;
import com.skynet.psi.network.api.CallBackBase;
import com.skynet.psi.ui.base.Interactor;
import com.skynet.psi.ui.listProduct.ListProductContract;
import com.skynet.psi.utils.AppConstant;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BookingImplRemote extends Interactor implements BookingContract.Interactor {
    BookingContract.Listener listener;

    public BookingImplRemote(BookingContract.Listener listener) {
        this.listener = listener;
    }

    @Override
    public ApiService createService() {
        return ApiUtil.createNotTokenApi();
    }

    @Override
    public void getListProduct(int index) {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            listener.onErrorAuthorization();
            return;
        }
        getmService().getService( index).enqueue(new CallBackBase<ApiResponse<List<Service>>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<List<Service>>> call, Response<ApiResponse<List<Service>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getCode() == AppConstant.CODE_API_SUCCESS) {
                        listener.onSucessGetListProduct(response.body().getData());
                    } else {
                        listener.onError(response.body().getMessage());
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<List<Service>>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }
    @Override
    public void getAddressFromLatlng(LatLng latLng, Geocoder geocoder) {
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if(addresses.size()==0) return;
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            MyPlace place = new MyPlace();
            place.setLat(latLng.latitude);
            place.setLng(latLng.longitude);
            place.setName(knownName);
            String[] splitAddress = address.split(",");
            if (splitAddress.length > 2)
                place.setDescription(splitAddress[0] + ", " + splitAddress[1]);
            else
                place.setDescription(address);
            listener.onGetAddressFromLatlngSuccess(place);
        } catch (IOException e) {
            e.printStackTrace();
            listener.onError(e.getMessage());
        }
    }

    @Override
    public void book(Booking booking) {
        getmService().booking(booking.getAddress(),booking.getDate_working(),booking.getHour_working(),booking.getIdPromotion(),booking.getLat(),booking.getLng(),
                booking.getLocationID(),booking.getMethod_payment(),booking.getNote(),booking.getRepeat_type(),booking.getService_id(),booking.getType_bike(),booking.getUserId()).enqueue(new CallBackBase<ApiResponse<Booking>>() {
            @Override
            public void onRequestSuccess(Call<ApiResponse<Booking>> call, Response<ApiResponse<Booking>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    listener.onSucessBook(response.body().getData());
                }else{
                    listener.onError(response.message());
                }
            }

            @Override
            public void onRequestFailure(Call<ApiResponse<Booking>> call, Throwable t) {
                listener.onErrorApi(t.getMessage());
            }
        });
    }

}