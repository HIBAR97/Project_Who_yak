package com.example.project_who_yak;

public class Category1_Latest {
    String notice;
    String name;
    String date;
    String rate;
    String category;

    public Category1_Latest(String notice, String name, String date , String rate, String category){
        this.notice = notice;
        this.name = name;
        this.date = date;
        this.rate = rate;
        this.category = category;
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

    public String getCategory(){ return this.category;}

    public void  setCategory(String category){this.category = category;}
}
