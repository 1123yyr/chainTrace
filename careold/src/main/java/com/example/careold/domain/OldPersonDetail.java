package com.example.careold.domain;

public class OldPersonDetail {
    private String name;
    private Integer age;
    private String sex;
    private String phone;
    private String room;
    private String beTime;
    private String customerName;
    private Integer familyId;
    private String familyName;
    private String familySex;
    private String familyRelation;
    private String familyPhone;
    private String familyAddress;

    @Override
    public String toString() {
        return "OldPersonDetail{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", room='" + room + '\'' +
                ", beTime='" + beTime + '\'' +
                ", customerName='" + customerName + '\'' +
                ", familyId=" + familyId +
                ", familyName='" + familyName + '\'' +
                ", familySex='" + familySex + '\'' +
                ", familyRelation='" + familyRelation + '\'' +
                ", familyPhone='" + familyPhone + '\'' +
                ", familyAddress='" + familyAddress + '\'' +
                ", warn='" + warn + '\'' +
                ", foodLike='" + foodLike + '\'' +
                ", foodHate='" + foodHate + '\'' +
                ", drag='" + drag + '\'' +
                ", illness='" + illness + '\'' +
                ", bothdate='" + bothdate + '\'' +
                ", customerId='" + customerId + '\'' +
                ", oldId=" + oldId +
                ", deviceId=" + deviceId +
                '}';
    }

    private String warn;
    private String foodLike;
    private String foodHate;
    private String drag;
    private String illness;
    private String bothdate;
    private String customerId;
    private Integer oldId;
    private Integer deviceId;

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getOldId() {
        return oldId;
    }

    public void setOldId(Integer oldId) {
        this.oldId = oldId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBothdate() {
        return bothdate;
    }

    public void setBothdate(String bothdate) {
        this.bothdate = bothdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBeTime() {
        return beTime;
    }

    public void setBeTime(String beTime) {
        this.beTime = beTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getFamilyId() {
        return familyId;
    }

    public void setFamilyId(Integer familyId) {
        this.familyId = familyId;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilySex() {
        return familySex;
    }

    public void setFamilySex(String familySex) {
        this.familySex = familySex;
    }

    public String getFamilyRelation() {
        return familyRelation;
    }

    public void setFamilyRelation(String familyRelation) {
        this.familyRelation = familyRelation;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getFoodLike() {
        return foodLike;
    }

    public void setFoodLike(String foodLike) {
        this.foodLike = foodLike;
    }

    public String getFoodHate() {
        return foodHate;
    }

    public void setFoodHate(String foodHate) {
        this.foodHate = foodHate;
    }

    public String getDrag() {
        return drag;
    }

    public void setDrag(String drag) {
        this.drag = drag;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }
}
