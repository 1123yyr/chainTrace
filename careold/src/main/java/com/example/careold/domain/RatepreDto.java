package com.example.careold.domain;

public class RatepreDto {
    private String name;
    private int oldId;
    private String sex;
    private String rateState;
    private String preState;
    private String createTime;
    private int hr;
    private int bp;

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public int getBp() {
        return bp;
    }

    public void setBp(int bp) {
        this.bp = bp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOldId() {
        return oldId;
    }

    public void setOldId(int oldId) {
        this.oldId = oldId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRateState() {
        return rateState;
    }

    public void setRateState(String rateState) {
        this.rateState = rateState;
    }

    public String getPreState() {
        return preState;
    }

    public void setPreState(String preState) {
        this.preState = preState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
