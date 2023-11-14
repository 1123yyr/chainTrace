package com.example.careold.domain;

public class Produce {
    private int pid;
    private String paccount;
    private String ppassword;
    private String pname;//企业名字
    private String pemail;
    private String ptime;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPaccount() {
        return paccount;
    }

    public void setPaccount(String paccount) {
        this.paccount = paccount;
    }

    public String getPpassword() {
        return ppassword;
    }

    public void setPpassword(String ppassword) {
        this.ppassword = ppassword;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
}
