package com.javapandeng.po;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    /*主键id*/
    private Integer id;
    /*内容*/
    private String content;
    /*姓名*/
    private String name;

    /*电话*/
    private String phone;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public Message() {
    }

    public Message(Integer id, String content, String name, String phone) {
        this.id = id;
        this.content = content;
        this.name = name;
        this.phone = phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
