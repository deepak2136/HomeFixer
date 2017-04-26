package com.homefixer.homefixer;

import android.widget.TextView;

public class RateCardHolder {

    TextView service, sub_service, rate, category, description;

    public RateCardHolder(TextView service, TextView sub_service, TextView rate, TextView category, TextView description) {
        this.service = service;
        this.sub_service = sub_service;
        this.rate = rate;
        this.category = category;
        this.description = description;
    }

    RateCardHolder() {

    }

    public TextView getService() {
        return service;
    }

    public void setService(TextView service) {
        this.service = service;
    }

    public TextView getSub_service() {
        return sub_service;
    }

    public void setSub_service(TextView sub_service) {
        this.sub_service = sub_service;
    }

    public TextView getRate() {
        return rate;
    }

    public void setRate(TextView rate) {
        this.rate = rate;
    }

    public TextView getCategory() {
        return category;
    }

    public void setCategory(TextView category) {
        this.category = category;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }
}
