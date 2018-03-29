package com.tutorialsbuzz.navigationdrawer.activity.model;

/**
 * Created by indglobal on 12/3/16.
 */
public class NewReleaseModel {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_description() {
        return news_description;
    }

    public void setNews_description(String news_description) {
        this.news_description = news_description;
    }

    String id,news_title,news_description;

    public NewReleaseModel(String id,String news_title,String news_description){
        this.id=id;
        this.news_title=news_title;
        this.news_description=news_description;
    }
}
