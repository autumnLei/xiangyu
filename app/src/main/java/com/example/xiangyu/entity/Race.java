package com.example.xiangyu.entity;

/**
 * Created by Administrator on 2017/6/3.
 */

public class Race {
    private int image;
    private String  text;
    public Race(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
