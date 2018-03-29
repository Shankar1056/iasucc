package com.tutorialsbuzz.navigationdrawer.activity.model;

/**
 * Created by indglobal on 10/3/16.
 */
public class LanguageModel {
    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    String lid,language;

    public LanguageModel(String lid,String language){
        this.lid=lid;
        this.language=language;
    }
}
