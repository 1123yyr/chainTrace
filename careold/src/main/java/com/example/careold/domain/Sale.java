package com.example.careold.domain;

public class Sale {
    private int sid;
    private String saccount;
    private String spassword;
    private String sname;
    private String semail;
    private String stime;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSaccount() {
        return saccount;
    }

    public void setSaccount(String saccount) {
        this.saccount = saccount;
    }

    public String getSpassword() {
        return spassword;
    }

    public void setSpassword(String spassword) {
        this.spassword = spassword;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }
}
