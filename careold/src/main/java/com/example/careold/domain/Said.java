package com.example.careold.domain;

public class Said {
    private int saidId;
    private String said;
    private int textId;
    private String back;
    private String saidTime;
    private String backTime;
    private String  personName;
    private int niceCount;
    private String saidPhone;
    private String touPic;

    public String getTouPic() {
        return touPic;
    }

    public void setTouPic(String touPic) {
        this.touPic = touPic;
    }

    public String getSaidPhone() {
        return saidPhone;
    }

    public void setSaidPhone(String saidPhone) {
        this.saidPhone = saidPhone;
    }

    public int getNiceCount() {
        return niceCount;
    }

    public void setNiceCount(int niceCount) {
        this.niceCount = niceCount;
    }

    public int getSaidId() {
        return saidId;
    }

    public void setSaidId(int saidId) {
        this.saidId = saidId;
    }

    public String getSaid() {
        return said;
    }

    public void setSaid(String said) {
        this.said = said;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getSaidTime() {
        return saidTime;
    }

    public void setSaidTime(String saidTime) {
        this.saidTime = saidTime;
    }

    public String getBackTime() {
        return backTime;
    }

    public void setBackTime(String backTime) {
        this.backTime = backTime;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
