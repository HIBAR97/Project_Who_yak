package com.example.project_who_yak;

public class Popular {
    String notice;
    String name;
    String date;
    String rate;

    public Popular(String notice, String name, String date , String rate) {
        this.notice = notice;
        this.name = name;
        this.date = date;
        this.rate = rate;
    }

    public String getNotice() {
        return this.notice;
    }

    public void getNotice(String notice) {
        this.notice = notice;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate(){ return this.rate;}

    public void  setRate(String rate){this.rate = rate;}
}
