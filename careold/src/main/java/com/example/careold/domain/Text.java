package com.example.careold.domain;

public class Text {

    private int textId;
    private String title;
    private String textMain;
    private String time;
    private int picId;

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int testId) {
        this.textId = testId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextMain() {
        return textMain;
    }

    public void setTextMain(String testMian) {
        this.textMain = testMian;
    }
}
