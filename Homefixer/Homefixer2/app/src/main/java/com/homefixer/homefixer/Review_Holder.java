package com.homefixer.homefixer;

import android.widget.TextView;

public class Review_Holder {

    TextView name, review, time;

    public Review_Holder(TextView name, TextView review, TextView time) {
        this.name = name;
        this.review = review;
        this.time = time;
    }

    Review_Holder() {

    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getReview() {
        return review;
    }

    public void setReview(TextView review) {
        this.review = review;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }
}
