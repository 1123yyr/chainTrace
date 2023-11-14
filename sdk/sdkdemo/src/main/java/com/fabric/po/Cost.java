package com.fabric.po;

public class Cost {

    private String role;
    private String money;
    private String orderstatus;
    private String orderid;
    private String name;
    private String time;
    private String oldid;
    private String thing;
    private String TID;
    private String TTime;
    private String IsDelete;

    @Override
    public String toString() {
        return "Cost{" +
                "role='" + role + '\'' +
                ", money='" + money + '\'' +
                ", orderstatus='" + orderstatus + '\'' +
                ", orderid='" + orderid + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", oldid='" + oldid + '\'' +
                ", thing='" + thing + '\'' +
                ", TID='" + TID + '\'' +
                ", TTime='" + TTime + '\'' +
                ", IsDelete='" + IsDelete + '\'' +
                '}';
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOldid() {
        return oldid;
    }

    public void setOldid(String oldid) {
        this.oldid = oldid;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
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
