package com.homefixer.homefixer;

import android.widget.TextView;

public class Service_Provider_Holder {

    TextView user_id, service_time_start, service_time_end, experience, degree, speciality;

    public Service_Provider_Holder(TextView user_id, TextView service_time_start, TextView service_time_end, TextView experience, TextView degree, TextView speciality) {
        this.user_id = user_id;
        this.service_time_start = service_time_start;
        this.service_time_end = service_time_end;
        this.experience = experience;
        this.degree = degree;
        this.speciality = speciality;
    }

    public TextView getUser_id() {
        return user_id;
    }

    public void setUser_id(TextView user_id) {
        this.user_id = user_id;
    }

    public TextView getService_time_start() {
        return service_time_start;
    }

    public void setService_time_start(TextView service_time_start) {
        this.service_time_start = service_time_start;
    }

    public TextView getService_time_end() {
        return service_time_end;
    }

    public void setService_time_end(TextView service_time_end) {
        this.service_time_end = service_time_end;
    }

    public TextView getExperience() {
        return experience;
    }

    public void setExperience(TextView experience) {
        this.experience = experience;
    }

    public TextView getDegree() {
        return degree;
    }

    public void setDegree(TextView degree) {
        this.degree = degree;
    }

    public TextView getSpeciality() {
        return speciality;
    }

    public void setSpeciality(TextView speciality) {
        this.speciality = speciality;
    }
}
