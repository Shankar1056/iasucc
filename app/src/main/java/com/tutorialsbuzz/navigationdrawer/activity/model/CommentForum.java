package com.tutorialsbuzz.navigationdrawer.activity.model;

import java.io.Serializable;

/**
 * Created by indglobal on 2/2/16.
 */
public class CommentForum implements Serializable
{
    String id ;
    String topic ;
    String description ;
    String date;
    String created_by;
    String likes;
    private boolean selected=false;

    String reply_id;
    String idd;
    String reply ;
    String created_by_reply ;
    String date_reply;
    String commentCount;

    String favourite;

    public String getIslikes() {
        return islikes;
    }

    public void setIslikes(String islikes) {
        this.islikes = islikes;
    }

    String islikes;


    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public CommentForum(String id, String topic, String description, String date, String created_by, String likes, String favourite, String islikes)
    {

        this.id=id;
        this.topic=topic;
        this.date=date;

        this.description=description;
        this.created_by=created_by;
        this.likes=likes;
        this.favourite=favourite;
        this.islikes=islikes;
    }
    public CommentForum(String id, String topic, String description, String date, String created_by, String likes)
    {

        this.id=id;
        this.topic=topic;
        this.date=date;
        this.description=description;
        this.created_by=created_by;
        this.likes=likes;
    }
    public CommentForum(String reply_id, String reply, String created_by_reply, String date_reply, String idd)
    {

        this.reply_id=reply_id;
        this.reply=reply;
        this.created_by_reply=created_by_reply;
        this.date_reply=date_reply;
        this.idd=idd;
    }
    public CommentForum(String commentCount){
        this.commentCount=commentCount;
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getCreated_by_reply() {
        return created_by_reply;
    }

    public void setCreated_by_reply(String created_by_reply) {
        this.created_by_reply = created_by_reply;
    }

    public String getDate_reply() {
        return date_reply;
    }

    public void setDate_reply(String date_reply) {
        this.date_reply = date_reply;
    }
    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }
}