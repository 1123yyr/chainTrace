package com.fabric.po;

public class Trace {

    private String traid;
    private String pname;
    private String paddress;
    private String ptime;//企业名称
    private String pperson;
    private String tperson;
    private String tbeginaddress;
    private String tarriveaddress;
    private String tname;
    private String sperson;
    private String stime;
    private String saddress;
    private String sname;
    private String role;
    private String TID;
    private String TTime;
    private String IsDelete;

    @Override
    public String toString() {
        return "Trace{" +
                "traid='" + traid + '\'' +
                ", pname='" + pname + '\'' +
                ", paddress='" + paddress + '\'' +
                ", ptime='" + ptime + '\'' +
                ", pperson='" + pperson + '\'' +
                ", tperson='" + tperson + '\'' +
                ", tbeginaddress='" + tbeginaddress + '\'' +
                ", tarriveaddress='" + tarriveaddress + '\'' +
                ", tname='" + tname + '\'' +
                ", sperson='" + sperson + '\'' +
                ", stime='" + stime + '\'' +
                ", saddress='" + saddress + '\'' +
                ", sname='" + sname + '\'' +
                ", role='" + role + '\'' +
                ", TID='" + TID + '\'' +
                ", TTime='" + TTime + '\'' +
                ", IsDelete='" + IsDelete + '\'' +
                '}';
    }

    public String getTraid() {
        return traid;
    }

    public void setTraid(String traid) {
        this.traid = traid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getPperson() {
        return pperson;
    }

    public void setPperson(String pperson) {
        this.pperson = pperson;
    }

    public String getTperson() {
        return tperson;
    }

    public void setTperson(String tperson) {
        this.tperson = tperson;
    }

    public String getTbeginaddress() {
        return tbeginaddress;
    }

    public void setTbeginaddress(String tbeginaddress) {
        this.tbeginaddress = tbeginaddress;
    }

    public String getTarriveaddress() {
        return tarriveaddress;
    }

    public void setTarriveaddress(String tarriveaddress) {
        this.tarriveaddress = tarriveaddress;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getSperson() {
        return sperson;
    }

    public void setSperson(String sperson) {
        this.sperson = sperson;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getSaddress() {
        return saddress;
    }

    public void setSaddress(String saddress) {
        this.saddress = saddress;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTID() {
        return TID;
    }

    public void setTID(String TID) {
        this.TID = TID;
    }

    public String getTTime() {
        return TTime;
    }

    public void setTTime(String TTime) {
        this.TTime = TTime;
    }

    public String getIsDelete() {
        return IsDelete;
    }

    public void setIsDelete(String isDelete) {
        IsDelete = isDelete;
    }
}
