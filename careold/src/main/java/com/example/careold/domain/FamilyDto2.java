package com.example.careold.domain;

public class FamilyDto2 {

    private Integer familyId;
    private String familyName;
    private String familySex;
    private String familyAddress;
    private String familyPhone;
    private String familyPassword;
    private String familyTouPic;

    public String getFamilyTouPic() {
        return familyTouPic;
    }

    public void setFamilyTouPic(String familyTouPic) {
        this.familyTouPic = familyTouPic;
    }

    public String getFamilyPassword() {
        return familyPassword;
    }

    public void setFamilyPassword(String familyPassword) {
        this.familyPassword = familyPassword;
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

    public String getFamilyAddress() {
        return familyAddress;
    }

    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }
}
