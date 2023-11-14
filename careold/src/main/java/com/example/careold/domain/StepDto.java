package com.example.careold.domain;

public class StepDto {

    private Integer oldId;
    private String name;
    private String sex;
    private Integer step;
    private Integer sleep;
    private String sleepSate;
    private String createTime;

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getSleep() {
        return sleep;
    }

    public void setSleep(Integer sleep) {
        this.sleep = sleep;
    }

    public String getSleepSate() {
        return sleepSate;
    }

    public void setSleepSate(String sleepSate) {
        this.sleepSate = sleepSate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
