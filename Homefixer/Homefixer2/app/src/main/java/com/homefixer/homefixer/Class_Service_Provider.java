package com.homefixer.homefixer;

public class Class_Service_Provider {

    private int user_id;
    private int service_id;
    private int sub_service_id;
    private String service_area;
    private String service_time_start;
    private String service_time_end;
    private String experience;
    private String degree;
    private String speciality;

    public Class_Service_Provider(int user_id, int service_id, int sub_service_id, String service_area, String service_time_start,
                                  String service_time_end, String experience, String degree, String speciality) {
        this.user_id = user_id;
        this.service_id = service_id;
        this.sub_service_id = sub_service_id;
        this.service_area = service_area;
        this.service_time_start = service_time_start;
        this.service_time_end = service_time_end;
        this.experience = experience;
        this.degree = degree;
        this.speciality = speciality;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getSub_service_id() {
        return sub_service_id;
    }

    public void setSub_service_id(int sub_service_id) {
        this.sub_service_id = sub_service_id;
    }

    public String getService_area() {
        return service_area;
    }

    public void setService_area(String service_area) {
        this.service_area = service_area;
    }

    public String getService_time_start() {
        return service_time_start;
    }

    public void setService_time_start(String service_time_start) {
        this.service_time_start = service_time_start;
    }

    public String getService_time_end() {
        return service_time_end;
    }

    public void setService_time_end(String service_time_end) {
        this.service_time_end = service_time_end;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
