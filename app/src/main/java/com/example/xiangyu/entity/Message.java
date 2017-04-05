package com.example.xiangyu.entity;

/**
 * Created by Administrator on 2017/3/27.
 */

public class Message {

    private int iamgeId;

    private String text;

    public Message(int iamgeId, String text) {
        this.iamgeId = iamgeId;
        this.text = text;
    }

    public int getIamgeId() {
        return iamgeId;
    }

    public String getText() {
        return text;
    }
}
