package com.example.careold.domain;

public class Cost {
    @Override
    public String toString() {
        return "Cost{" +
                "orderid=" + orderid +
                ", oldid=" + oldid +
                ", thing='" + thing + '\'' +
                ", time='" + time + '\'' +
                ", money=" + money +
                ", name='" + name + '\'' +
                ", orderstatus='" + orderstatus + '\'' +
                '}';
    }

    private int orderid;
    private int oldid;
    private String thing;
    private String time;
    private int money;
    private String name;
    private String orderstatus;

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Cost() {
    }

    public Cost(int oldid, String thing, String time, int money, String name) {
        this.orderid = orderid;
        this.oldid = oldid;
        this.thing = thing;
        this.time = time;
        this.money = money;
        this.name = name;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public int getOldid() {
        return oldid;
    }

    public void setOldid(int oldid) {
        this.oldid = oldid;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
