package com.skynet.psi.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Technical implements Parcelable {

    @Expose
    @SerializedName("distance")
    private double distance;
    @Expose
    @SerializedName("lng")
    private double lng;
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("avatar")
    private String avatar;
    @Expose
    @SerializedName("booking_id")
    private int booking_id;
    @Expose
    @SerializedName("rating")
    private int rating;
    @Expose
    @SerializedName("id")
    private String id; @Expose
    @SerializedName("phone")
    private String phone;

    public int getRating() {
        return rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Technical() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.distance);
        dest.writeDouble(this.lng);
        dest.writeDouble(this.lat);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeInt(this.booking_id);
        dest.writeInt(this.rating);
        dest.writeString(this.id);
        dest.writeString(this.phone);
    }

    protected Technical(Parcel in) {
        this.distance = in.readDouble();
        this.lng = in.readDouble();
        this.lat = in.readDouble();
        this.name = in.readString();
        this.avatar = in.readString();
        this.booking_id = in.readInt();
        this.rating = in.readInt();
        this.id = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<Technical> CREATOR = new Creator<Technical>() {
        @Override
        public Technical createFromParcel(Parcel source) {
            return new Technical(source);
        }

        @Override
        public Technical[] newArray(int size) {
            return new Technical[size];
        }
    };
}
