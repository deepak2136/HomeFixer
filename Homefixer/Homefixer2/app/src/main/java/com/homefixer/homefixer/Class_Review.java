package com.homefixer.homefixer;

public class Class_Review {

    public Class_Review() {
    }

    private int review_id;
    private int login_id;
    private int reviewer_id;
    private String review;
    private float rate;
    private String date;
    private String time;

    public Class_Review(int review_id, int login_id, int reviewer_id, String review, float rate, String date, String time) {
        this.review_id = review_id;
        this.login_id = login_id;
        this.reviewer_id = reviewer_id;
        this.review = review;
        this.rate = rate;
        this.date = date;
        this.time = time;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getLogin_id() {
        return login_id;
    }

    public void setLogin_id(int login_id) {
        this.login_id = login_id;
    }

    public int getReviewer_id() {
        return reviewer_id;
    }

    public void setReviewer_id(int reviewer_id) {
        this.reviewer_id = reviewer_id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
