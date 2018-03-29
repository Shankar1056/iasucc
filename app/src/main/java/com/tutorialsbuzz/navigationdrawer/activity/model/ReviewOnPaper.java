package com.tutorialsbuzz.navigationdrawer.activity.model;

/**
 * Created by igcs-27 on 10/2/16.
 */
public class ReviewOnPaper {

    String id ;
    String papername ;
    String byName ;
    String rating;
    String created_by;
    String review_count;

    public ReviewOnPaper(String id, String papername, String byName, String rating, String created_by, String review_count) {
        this.id = id;
        this.papername = papername;

        this.rating = rating;
        this.created_by = created_by;
        this.review_count = review_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPapername() {
        return papername;
    }

    public void setPapername(String papername) {
        this.papername = papername;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getByName() {
        return byName;
    }

    public void setByName(String byName) {
        this.byName = byName;
    }

    public String getReview_count() {
        return review_count;
    }

    public void setReview_count(String review_count) {
        this.review_count = review_count;
    }
}
