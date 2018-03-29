package com.tutorialsbuzz.navigationdrawer.activity.model;

/**
 * Created by indglobal on 25/3/16.
 */
public class BannerModel {
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String image,url;
    public BannerModel(String image,String url){
        this.image=image;
        this.url=url;
    }
}
