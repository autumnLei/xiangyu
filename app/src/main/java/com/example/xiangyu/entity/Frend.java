package com.example.xiangyu.entity;

/**
 * Created by Administrator on 2017/6/3.
 */

public class Frend {
    private int avatar;
    private String name;
    private String type;
    private String text;

    public Frend(int avatar, String name, String type, String text){
        this.avatar = avatar;
        this.name = name;
        this.type = type;
        this.text = text;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
