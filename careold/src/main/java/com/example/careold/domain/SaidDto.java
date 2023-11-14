package com.example.careold.domain;

public class SaidDto {
    private Integer saidId;
    private String said;
    private String back;
    private String saidTime;
    private String backTime;
    private String  personName;
    private String touPic;
    private String title;

    public Integer getSaidId() {
        return saidId;
    }

    public void setSaidId(Integer saidId) {
        this.saidId = saidId;
    }

    public String getSaid() {
        return said;
    }

    public void setSaid(String said) {
        this.said = said;
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

    public String getTouPic() {
        return touPic;
    }

    public void setTouPic(String touPic) {
        this.touPic = touPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
