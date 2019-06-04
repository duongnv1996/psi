package com.skynet.psi.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Booking implements Parcelable {
    @SerializedName("user_id")
    String userId;
    String activeString;
    String repeatString;
    @SerializedName("address")
    String address;
    @SerializedName("lat")
    double lat;
    @SerializedName("lng")
    double lng;
    @SerializedName("price")
    double price;
    @SerializedName("location_id")
    int locationID;
    @SerializedName("id_promotion")
    int idPromotion;
    @SerializedName("date_working")
    String date_working;
    @SerializedName("hour_working")
    String hour_working;
    @SerializedName("note")
    String note;
    @SerializedName("service_name")
    String service_name;
    @SerializedName("service_image")
    String service_image;
    @SerializedName("type_bike")
    int type_bike;
    @SerializedName("method_payment")
    int method_payment;
    @SerializedName("repeat_type")
    int repeat_type;
    @Expose
    @SerializedName("service_id")
    private int service_id;
    @Expose
    @SerializedName(value = "booking_id", alternate = "id")
    private int booking_id;

    @SerializedName("active")
    private int active;

    @SerializedName("tech")
    private Technical tech;
    @SerializedName("service_detail")
    private List<Step> service_detail;
    @SerializedName("time_working")
    private String time_working;
    @SerializedName("list_image")
    private List<Image> list_image;

    public String getTime_working() {
        return time_working;
    }

    public void setTime_working(String time_working) {
        this.time_working = time_working;
    }

    public List<Image> getList_image() {
        return list_image;
    }

    public void setList_image(List<Image> list_image) {
        this.list_image = list_image;
    }

    public List<Step> getService_detail() {
        return service_detail;
    }

    public void setService_detail(List<Step> service_detail) {
        this.service_detail = service_detail;
    }

    public String getActiveString() {
        return activeString;
    }

    public void setActiveString(String activeString) {
        this.activeString = activeString;
    }

    public String getRepeatString() {
        return repeatString;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRepeatString(String repeatString) {
        this.repeatString = repeatString;
    }

    public Technical getTech() {
        return tech;
    }

    public void setTech(Technical tech) {
        this.tech = tech;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_image() {
        return service_image;
    }

    public void setService_image(String service_image) {
        this.service_image = service_image;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Expose
    @SerializedName("list_tech")
    private List<Technical> listTechnical;

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public List<Technical> getListTechnical() {
        return listTechnical;
    }

    public void setListTechnical(List<Technical> listTechnical) {
        this.listTechnical = listTechnical;
    }

    public String getUserId() {
        return userId;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public int getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }

    public String getDate_working() {
        return date_working;
    }

    public void setDate_working(String date_working) {
        this.date_working = date_working;
    }

    public String getHour_working() {
        return hour_working;
    }

    public void setHour_working(String hour_working) {
        this.hour_working = hour_working;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getType_bike() {
        return type_bike;
    }

    public void setType_bike(int type_bike) {
        this.type_bike = type_bike;
    }

    public int getMethod_payment() {
        return method_payment;
    }

    public void setMethod_payment(int method_payment) {
        this.method_payment = method_payment;
    }

    public int getRepeat_type() {
        return repeat_type;
    }

    public void setRepeat_type(int repeat_type) {
        this.repeat_type = repeat_type;
    }

    public Booking() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.activeString);
        dest.writeString(this.repeatString);
        dest.writeString(this.address);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
        dest.writeDouble(this.price);
        dest.writeInt(this.locationID);
        dest.writeInt(this.idPromotion);
        dest.writeString(this.date_working);
        dest.writeString(this.hour_working);
        dest.writeString(this.note);
        dest.writeString(this.service_name);
        dest.writeString(this.service_image);
        dest.writeInt(this.type_bike);
        dest.writeInt(this.method_payment);
        dest.writeInt(this.repeat_type);
        dest.writeInt(this.service_id);
        dest.writeInt(this.booking_id);
        dest.writeInt(this.active);
        dest.writeParcelable(this.tech, flags);
        dest.writeList(this.service_detail);
        dest.writeString(this.time_working);
        dest.writeTypedList(this.list_image);
        dest.writeTypedList(this.listTechnical);
    }

    protected Booking(Parcel in) {
        this.userId = in.readString();
        this.activeString = in.readString();
        this.repeatString = in.readString();
        this.address = in.readString();
        this.lat = in.readDouble();
        this.lng = in.readDouble();
        this.price = in.readDouble();
        this.locationID = in.readInt();
        this.idPromotion = in.readInt();
        this.date_working = in.readString();
        this.hour_working = in.readString();
        this.note = in.readString();
        this.service_name = in.readString();
        this.service_image = in.readString();
        this.type_bike = in.readInt();
        this.method_payment = in.readInt();
        this.repeat_type = in.readInt();
        this.service_id = in.readInt();
        this.booking_id = in.readInt();
        this.active = in.readInt();
        this.tech = in.readParcelable(Technical.class.getClassLoader());
        this.service_detail = new ArrayList<Step>();
        in.readList(this.service_detail, Step.class.getClassLoader());
        this.time_working = in.readString();
        this.list_image = in.createTypedArrayList(Image.CREATOR);
        this.listTechnical = in.createTypedArrayList(Technical.CREATOR);
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel source) {
            return new Booking(source);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };
}
