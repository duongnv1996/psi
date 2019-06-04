package com.skynet.psi.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class Feedback implements Parcelable {

    @Expose
    @SerializedName("list_image")
    private List<List_imageEntity> list_image;
    @Expose
    @SerializedName("active")
    private int active;
    @Expose
    @SerializedName("time_report")
    private String time_report;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("content")
    private String content;
    private String activeString;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("booking_id")
    private int booking_id;
    @Expose
    @SerializedName("tech_id")
    private int tech_id;
    @Expose
    @SerializedName("id")
    private int id;

    public String getActiveString() {
        return activeString;
    }

    public void setActiveString(String activeString) {
        this.activeString = activeString;
    }

    public List<List_imageEntity> getList_image() {
        return list_image;
    }

    public void setList_image(List<List_imageEntity> list_image) {
        this.list_image = list_image;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getTime_report() {
        return time_report;
    }

    public void setTime_report(String time_report) {
        this.time_report = time_report;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getTech_id() {
        return tech_id;
    }

    public void setTech_id(int tech_id) {
        this.tech_id = tech_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class List_imageEntity implements Parcelable {

        @Expose
        @SerializedName("active")
        private String active;
        @Expose
        @SerializedName("date")
        private String date;
        @Expose
        @SerializedName("img")
        private String img;
        @Expose
        @SerializedName("report_id")
        private String report_id;
        @Expose
        @SerializedName("id")
        private String id;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getReport_id() {
            return report_id;
        }

        public void setReport_id(String report_id) {
            this.report_id = report_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.active);
            dest.writeString(this.date);
            dest.writeString(this.img);
            dest.writeString(this.report_id);
            dest.writeString(this.id);
        }

        public List_imageEntity() {
        }

        protected List_imageEntity(Parcel in) {
            this.active = in.readString();
            this.date = in.readString();
            this.img = in.readString();
            this.report_id = in.readString();
            this.id = in.readString();
        }

        public static final Parcelable.Creator<List_imageEntity> CREATOR = new Parcelable.Creator<List_imageEntity>() {
            @Override
            public List_imageEntity createFromParcel(Parcel source) {
                return new List_imageEntity(source);
            }

            @Override
            public List_imageEntity[] newArray(int size) {
                return new List_imageEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list_image);
        dest.writeInt(this.active);
        dest.writeString(this.time_report);
        dest.writeString(this.date);
        dest.writeString(this.content);
        dest.writeString(this.activeString);
        dest.writeString(this.address);
        dest.writeInt(this.booking_id);
        dest.writeInt(this.tech_id);
        dest.writeInt(this.id);
    }

    public Feedback() {
    }

    protected Feedback(Parcel in) {
        this.list_image = in.createTypedArrayList(List_imageEntity.CREATOR);
        this.active = in.readInt();
        this.time_report = in.readString();
        this.date = in.readString();
        this.content = in.readString();
        this.activeString = in.readString();
        this.address = in.readString();
        this.booking_id = in.readInt();
        this.tech_id = in.readInt();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Feedback> CREATOR = new Parcelable.Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel source) {
            return new Feedback(source);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };
}
