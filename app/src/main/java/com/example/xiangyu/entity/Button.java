package com.example.xiangyu.entity;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Button {
    private String name;
    private String location;

    public Button(String name, String location) {
        this.name = name;
        this.location = location;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
