package com.skynet.psi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Service {
    @SerializedName("price")
    private double price;
    @SerializedName("detail")
    private List<Step> listStep;

    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("img")
    private String img;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("time_used")
    private long time_work;
    private boolean isChecked;

    public long getTime_work() {
        return time_work;
    }

    public void setTime_work(long time_work) {
        this.time_work = time_work;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Step> getListStep() {
        return listStep;
    }

    public void setListStep(List<Step> listStep) {
        this.listStep = listStep;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
