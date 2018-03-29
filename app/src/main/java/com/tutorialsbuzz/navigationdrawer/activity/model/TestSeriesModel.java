package com.tutorialsbuzz.navigationdrawer.activity.model;

import java.io.Serializable;

/**
 * Created by indglobal on 8/2/16.
 */
public class TestSeriesModel implements Serializable{


    String id,name,category,description,doc_url,price,publisher,created_date,how_it_works,table_of_content,reviewid,rating,review,reviewed_by,date;

    public TestSeriesModel(String id, String name, String category, String description, String doc_url, String price, String publisher, String created_date
            , String how_it_works, String table_of_content){

        this.id=id;
        this.name=name;
        this.category=category;
        this.description=description;
        this.doc_url=doc_url;
        this.price=price;
        this.publisher=publisher;
        this.created_date=created_date;
        this.how_it_works=how_it_works;
        this.table_of_content=table_of_content;

    }
    public TestSeriesModel(String id, String reviewid, String rating, String review, String reviewed_by, String date){



        this.id=id;
        this.reviewid=reviewid;
        this.rating=rating;
        this.review=review;
        this.reviewed_by=reviewed_by;
        this.date=date;
    }
    public TestSeriesModel(String id, String name, String category, String description, String doc_url, String price, String publisher, String created_date
            , String how_it_works, String table_of_content, String reviewid, String rating, String review, String reviewed_by, String date){


        this.name=name;
        this.category=category;
        this.description=description;
        this.doc_url=doc_url;
        this.price=price;
        this.publisher=publisher;
        this.created_date=created_date;
        this.how_it_works=how_it_works;
        this.table_of_content=table_of_content;
        this.id=id;
        this.reviewid=reviewid;
        this.rating=rating;
        this.review=review;
        this.reviewed_by=reviewed_by;
        this.date=date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getHow_it_works() {
        return how_it_works;
    }

    public void setHow_it_works(String how_it_works) {
        this.how_it_works = how_it_works;
    }

    public String getTable_of_content() {
        return table_of_content;
    }

    public void setTable_of_content(String table_of_content) {
        this.table_of_content = table_of_content;
    }

    public String getReviewid() {
        return reviewid;
    }

    public void setReviewid(String reviewid) {
        this.reviewid = reviewid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReviewed_by() {
        return reviewed_by;
    }

    public void setReviewed_by(String reviewed_by) {
        this.reviewed_by = reviewed_by;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
