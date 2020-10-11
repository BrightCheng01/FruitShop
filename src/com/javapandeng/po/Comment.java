package com.javapandeng.po;

import java.io.Serializable;
import java.util.Date;

/*评价类*/
public class Comment implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer itemId;

    private String content;

    private Date addTime;

    private  User user;

    public Comment() {
    }

    public Comment(Integer id, Integer userId, Integer itemId, String content, Date addTime, User user) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.content = content;
        this.addTime = addTime;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
