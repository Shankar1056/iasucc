package com.tutorialsbuzz.navigationdrawer.activity.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by indglobal on 12/19/2015.
 */
public class ScienceImageBean implements Parcelable
{

    String title;
    String image;
    String description;
    String date;
    public ScienceImageBean(String title, String image, String description,String date)
    {

        this.title=title;
        this.image=image;
        this.description=description;
        this.date=date;

    }


    protected ScienceImageBean(Parcel in) {
        title = in.readString();
        image = in.readString();
        description = in.readString();
        date = in.readString();
    }



    public static final Creator<ScienceImageBean> CREATOR = new Creator<ScienceImageBean>() {
        @Override
        public ScienceImageBean createFromParcel(Parcel in) {
            return new ScienceImageBean(in);
        }

        @Override
        public ScienceImageBean[] newArray(int size) {
            return new ScienceImageBean[size];
        }
    };


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(date);
    }
}


