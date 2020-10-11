package com.javapandeng.po;

import java.io.Serializable;
import java.util.Date;

public class News implements Serializable {

    /*主键id*/
    private Integer id;
    /*内容*/
    private String content;
    /*发布时间*/
    private Date addTime;

    private String name;

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", addTime=" + addTime +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public News() {
    }

    public News(Integer id, String content, Date addTime) {
        this.id = id;
        this.content = content;
        this.addTime = addTime;
    }
}
