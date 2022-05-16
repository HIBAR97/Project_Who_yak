package com.example.project_who_yak;

public class Remember {

    String title;
    String name;
    String date;

    public Remember(String title, String name, String date) {
        this.title = title;
        this.name = name;
        this.date = date;
    }

    public String getNotice() {
        return this.title;
    }

    public void getNotice(String notice) {
        this.title = notice;
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
}
