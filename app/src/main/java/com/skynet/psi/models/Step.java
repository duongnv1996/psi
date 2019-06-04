package com.skynet.psi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class Step {

    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("position")
    private String position;
    @Expose
    @SerializedName("time_work")
    private int time_work;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("service_id")
    private int service_id;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getTime_work() {
        return time_work;
    }

    public void setTime_work(int time_work) {
        this.time_work = time_work;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
