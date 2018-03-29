package com.tutorialsbuzz.navigationdrawer.activity.model;

/**
 * Created by indglobal on 19/3/16.
 */
public class MySeriesModel {

    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    String name;
    String price;
    String publisher;
    String created_date;
    String order_id;

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    String transaction_status;
    public MySeriesModel(String id, String name, String price, String publisher, String created_date, String order_id,String transaction_status){



        this.id=id;
        this.name=name;
        this.price=price;
        this.publisher=publisher;
        this.created_date=created_date;
        this.order_id=order_id;
        this.transaction_status=transaction_status;
    }

}
