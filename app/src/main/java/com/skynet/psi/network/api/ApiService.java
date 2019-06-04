package com.skynet.psi.network.api;


import com.google.gson.JsonObject;
import com.skynet.psi.models.AddressGeocoding;
import com.skynet.psi.models.Booking;
import com.skynet.psi.models.Cart;
import com.skynet.psi.models.Category;
import com.skynet.psi.models.ChatItem;
import com.skynet.psi.models.FavouriteItem;
import com.skynet.psi.models.Feedback;
import com.skynet.psi.models.History;
import com.skynet.psi.models.HomeResponse;
import com.skynet.psi.models.Message;
import com.skynet.psi.models.MyPlace;
import com.skynet.psi.models.Nearby;
import com.skynet.psi.models.Notification;
import com.skynet.psi.models.PlaceNearby;
import com.skynet.psi.models.Product;
import com.skynet.psi.models.ProductResponse;
import com.skynet.psi.models.Profile;
import com.skynet.psi.models.Promotion;
import com.skynet.psi.models.Routes;
import com.skynet.psi.models.Service;
import com.skynet.psi.models.Shop;
import com.skynet.psi.models.ShopDetail;
import com.skynet.psi.models.ShopResponse;
import com.skynet.psi.models.Term;
import com.skynet.psi.models.franchise;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by thaopt on 9/6/17.
 */

public interface ApiService {
    public static String API_ROOT = "http://powersteaminc.site/api/";

    @GET("directions/json")
    Call<ApiResponse<List<Routes>>> getDirection(
            @Query("origin") String orgin
            , @Query("destination") String destination
            , @Query("key") String key);

    @GET("place/autocomplete/json")
    Call<ApiResponse<List<MyPlace>>> getAddress(@Query("input") String input
            , @Query("types") String type
            , @Query("strictbounds") boolean strictbounds
            , @Query("location") String location
            , @Query("radius") int radius
            , @Query("key") String key);

    @GET("geocode/json")
    Call<ApiResponse<List<PlaceNearby>>> getLocation(
            @Query("address") String location
            , @Query("sensor") boolean sensor
            , @Query("key") String key
    );

    @GET("place/nearbysearch/json")
    Call<ApiResponse<List<PlaceNearby>>> getNearby(
            @Query("location") String location
            , @Query("radius") int radius
            , @Query("type") String type
            , @Query("limit") int limit
            , @Query("key") String key);

    @GET("geocode/json")
    Call<ApiResponseGeoCoding<List<AddressGeocoding>>> getAddress(
            @Query("latlng") String location
            , @Query("key") String key);

    @GET("place/nearbysearch/json")
    Call<JsonObject> getNearbyJson(
            @Query("location") String location
            , @Query("radius") int radius
            , @Query("type") String type
            , @Query("limit") int limit
            , @Query("key") String key);

    @GET("get_info.php")
    Call<ApiResponse<Profile>> getProfile(@Query("id") String uid, @Query("type") int type);

    @FormUrlEncoded
    @POST("add_car.php")
    Call<ApiResponse> updateCar(@Field("user_id") String uid, @Field("type") int type, @Field("car_name") String car_name);

    @GET("home.php")
    Call<ApiResponse<HomeResponse>> getHome(@Query("user_id") String uid);

    @GET("login.php")
    Call<ApiResponse<Profile>> login(@Query("phone") String uid, @Query("password") String password, @Query("type") int type);

    @GET("promotion.php")
    Call<ApiResponse<List<Promotion>>> getListNotification(@Query("user_id") String uid, @Query("type") int type);

    @GET("notification.php")
    Call<ApiResponse<List<Notification>>> getNotification(@Query("id") String uid, @Query("type") int type);

    @GET("service.php")
    Call<ApiResponse<List<Category>>> getListCategory();

    @GET("list_friend.php")
    Call<ApiResponse<List<Shop>>> getListFriend(@Query("user_id") String uid);

    @GET("list_shop.php")
    Call<ApiResponse<ShopResponse>> getListShop(@Query("user_id") String uid, @Query("category_id") int category_id);

    @GET("get_product.php")
    Call<ApiResponse<Nearby>> getNearbyProduct(@Query("user_id") String uid, @Query("category_id") int category_id, @Query("index") int index, @Query("lat") double lat, @Query("lng") double lng);

    @GET("get_shop.php")
    Call<ApiResponse<List<Shop>>> getListShopNearby(@Query("lat") double lat, @Query("lng") double lng);

    @GET("location.php")
    Call<ApiResponse<List<franchise>>> getListFranchise();

    @GET("shop_detail.php")
    Call<ApiResponse<ShopDetail>> getDetailShop(@Query("user_id") String uid, @Query("shop_id") int shop_id);

    @FormUrlEncoded
    @GET("scan_qr.php")
    Call<ApiResponse> scanShop(@Field("user_id") String uid, @Field("shop_id") int shop_id);

    @GET("product_detail.php")
    Call<ApiResponse<Product>> getDetailProduct(@Query("user_id") String uid, @Query("product_id") int shop_id);

    @GET("promotion_detail.php")
    Call<ApiResponse<Promotion>> getDetailNotification(@Query("promotion_id") String id, @Query("type") int type, @Query("user_id") String shID);

    @GET("notification_detail.php")
    Call<ApiResponse<Notification>> getDetailNotifications(@Query("id") String id, @Query("type") int type, @Query("user_id") String shID);

    @FormUrlEncoded
    @POST("favourite_shop.php")
    Call<ApiResponse> toggleFavShop(@Field("user_id") String idUser, @Field("shop_id") int shop_id, @Field("type") int isFav);

    @FormUrlEncoded
    @POST("favourite_product.php")
    Call<ApiResponse> toggleFavProduct(@Field("user_id") String idUser, @Field("product_id") int shop_id, @Field("type") int isFav);

    @FormUrlEncoded
    @POST("feedback.php")
    Call<ApiResponse> feedback(@Field("user_id") String idUser, @Field("name") String name, @Field("phone") String phone, @Field("email") String email, @Field("content") String content);

    @GET("promotion.php")
    Call<ApiResponse<List<Promotion>>> getListPromotion(@Query("user_id") String idUser
    );

    @FormUrlEncoded
    @POST("forget_password.php")
    Call<ApiResponse> forgotPassword(@Field("phone") String phone, @Field("type") int type);

    @GET("verify_code.php")
    Call<ApiResponse<String>> sendCode(@Query("phone") String phone, @Query("type") int type);

    @GET("list_product.php")
    Call<ApiResponse<ProductResponse>> getListProduct(@Query("user_id") String user_id, @Query("index") int type);

    @GET("service_category.php")
    Call<ApiResponse<List<Service>>> getService(@Query("category_id") int category_id);

    @GET("list_favourite.php")
    Call<ApiResponse<FavouriteItem>> getListFavourite(@Query("user_id") String user_id);

    @GET("product_category.php")
    Call<ApiResponse<ProductResponse>> getListProductCategory(@Query("user_id") String user_id, @Query("index") int type, @Query("category_id") int category_id);

    @GET("history.php")
    Call<ApiResponse<List<Booking>>> getHistory(@Query("id") String user_id, @Query("type") int type, @Query("type_history") int type_history);

    @GET("schedule.php")
    Call<ApiResponse<List<Booking>>> getSchedule(@Query("id") String user_id, @Query("type") int type, @Query("type_history") int type_history);

    @GET("verify_booking.php")
    Call<ApiResponse<String>> sendCodeBooking(@Query("phone") String phone);
    @FormUrlEncoded
    @POST("booking.php")
    Call<ApiResponse<Booking>> booking(@Field("address") String address,@Field("date_working") String date_working,@Field("hour_working") String hour_working,
                                       @Field("id_promotion") int id_promotion,@Field("lat") double lat,@Field("lng") double lng,
                                       @Field("location_id") int location,@Field("method_payment") int medthod_payment,@Field("note") String note,
                                       @Field("repeat_type") int repeat,@Field("service_id") int service_type,@Field("type_bike") int type_bike,@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse<Profile>> signUp(@Field("phone") String phone, @Field("password") String password, @Field("email") String email, @Field("name") String name);
    @Multipart
    @POST("feedback.php")
    Call<ApiResponse> addFeeback(@Part("user_id") RequestBody tech_id,  @Part("content") RequestBody content,
                                 @Part("address") RequestBody address,  @Part("time_feedback") RequestBody time_report, @Part("type_feedback") RequestBody type_feedback, @Part List<MultipartBody.Part> listFile);

    @GET("list_feedback.php")
    Call<ApiResponse<List<Feedback>>> getListFeedback(@Query("user_id") String uid);

    @FormUrlEncoded
    @POST("delete_product_cart.php")
    Call<ApiResponse<Cart>> deteleItemFromCart(@Field("user_id") String user_id, @Field("product_id") int product_id);

    @GET("check_promotion.php")
    Call<ApiResponse<Cart>> addPromoToCart(@Query("user_id") String user_id, @Query("code") String code);

    @FormUrlEncoded
    @POST("edit_product_cart.php")
    Call<ApiResponse<Cart>> updateItemFromCart(@Field("user_id") String user_id, @Field("product_id") int product_id, @Field("number") int number, @Field("note") String note);

    @FormUrlEncoded
    @POST("add_cart.php")
    Call<ApiResponse<Cart>> addToCart(@Field("user_id") String idUser, @Field("product_id") int productId, @Field("number") int number, @Field("note") String note);

    @FormUrlEncoded
    @POST("update_info.php")
    Call<ApiResponse> updateInforCart(@Field("user_id") String idUser, @Field("name") String name, @Field("phone") String phone,
                                      @Field("email") String email, @Field("address") String address);

    @FormUrlEncoded
    @POST("update_time_booking.php")
    Call<ApiResponse> updateTimeShip(@Field("user_id") String idUser, @Field("time_ship") String name);

    @GET("cart_info.php")
    Call<ApiResponse<Cart>> getCart(@Query("user_id") String idUser);

    @GET("booking_detail.php")
    Call<ApiResponse<History>> getHistory(@Query("id") int idUser);

    @GET("booking_detail.php")
    Call<ApiResponse<Booking>> getDetailBooking(@Query("id") int idUser);

    @FormUrlEncoded
    @POST("status_booking.php")
    Call<ApiResponse> cancelBooking(@Field("id") int idHis, @Field("active") int active);

    @FormUrlEncoded
    @POST("rating.php")
    Call<ApiResponse> rating(@Field("booking_id") int idHis, @Field("star") float active);

    @GET("term.php")
    Call<ApiResponse<Term>> getTerm();

    @GET("list_chat.php")
    Call<ApiResponse<List<ChatItem>>> getListChat(@Query("id") String phone, @Query("type") int type);

    @GET("privacy.php")
    Call<ApiResponse<Term>> getPrivacy();

    @Multipart
    @POST("update_profile.php")
    Call<ApiResponse> updateInfor(@PartMap() Map<String, okhttp3.RequestBody> partMap);


    @Multipart
    @POST("upload_file.php")
    Call<ApiResponse<Message>> uploadFile(@Part("user_id") RequestBody user_id, @Part("chat_id") RequestBody chat_id, @Part("type") RequestBody type, @Part MultipartBody.Part listFile);

    @Multipart
    @POST("upload_image.php")
    Call<ApiResponse<List<Message>>> uploadImages(@Part("user_id") RequestBody user_id, @Part("chat_id") RequestBody chat_id, @Part("type") RequestBody type, @Part List<MultipartBody.Part> listFile);

    @Multipart
    @POST("update_avatar.php")
    Call<ApiResponse<String>> uploadAvatar(@Part MultipartBody.Part image, @PartMap() Map<String, RequestBody> partMap);

    @FormUrlEncoded
    @POST("update_fcm.php")
    Call<ApiResponse> updateFCM(@Field("id") String u_id, @Field("token_fcm") String tokenFCM, @Field("type") int type);

    @GET("content_chat.php")
    Call<ApiResponse<ChatItem>> getListMessageBetween(@Query("user_id") int uiId, @Query("tech_id") int id_host, @Query("type") int type);

    @FormUrlEncoded
    @POST("chat.php")
    Call<ApiResponse<Message>> sendMessageTo(@Field("id_post") int id_post, @Field("user_id") int idUser,
                                             @Field("tech_id") int idShop, @Field("time") String time,
                                             @Field("content") String content, @Field("type") int typeUser,
                                             @Field("attach") int attach);


}
