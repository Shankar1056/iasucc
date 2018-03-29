package com.tutorialsbuzz.navigationdrawer.activity.model;

import java.io.Serializable;

/**
 * Created by indglobal on 1/2/16.
 */
public class NotificationModel implements Serializable {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String title;
    String date;
    String description;

    public NotificationModel(String title, String date, String description) {

        this.title = title;
        this.date = date;
        this.description = description;

    }


}
