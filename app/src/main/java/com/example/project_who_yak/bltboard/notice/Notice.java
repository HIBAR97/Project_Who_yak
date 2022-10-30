package com.example.project_who_yak.bltboard.notice;

public class Notice {
    String boardNo;
    String title;
    String writer;
    String date;
    String category;
    String popularity;

    public Notice(String boardNo, String title, String writer, String date, String category,String popularity) {
        this.boardNo = boardNo;
        this.title = title;
        this.writer = writer;
        this.date = date;
        this.category = category;
        this.popularity = popularity;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
