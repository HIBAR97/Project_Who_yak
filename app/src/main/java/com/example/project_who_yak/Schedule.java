package com.example.project_who_yak;

public class Schedule {

    private String schedule;
    private String date;


    public Schedule(String schedule,String date) {
        this.schedule = schedule;
        this.date = date;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
