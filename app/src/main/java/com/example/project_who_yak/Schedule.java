package com.example.project_who_yak;

public class Schedule {


    private int schedule_id;
    private String schedule;
    private String date;



    public Schedule(int schedule_id,String schedule,String date) {
        this.schedule_id = schedule_id;
        this.schedule = schedule;
        this.date = date;
    }
    public Schedule(String schedule,String date) {
//        this.schedule_id = schedule_id;
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

    public int getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(int schedule_id) {
        this.schedule_id = schedule_id;
    }
}
