package com.skynet.psi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class franchise {

    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("date")
    private String date;
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
    @SerializedName("parent_id")
    private int parent_id;
    @Expose
    @SerializedName("city_id")
    private int city_id;
    @Expose
    @SerializedName("franchise_id")
    private int franchise_id;
    @Expose
    @SerializedName("id")
    private int id;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getFranchise_id() {
        return franchise_id;
    }

    public void setFranchise_id(int franchise_id) {
        this.franchise_id = franchise_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
