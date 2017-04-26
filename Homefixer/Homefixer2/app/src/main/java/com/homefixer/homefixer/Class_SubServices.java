package com.homefixer.homefixer;

public class Class_SubServices {

    int sub_service_id;
    int service_id;
    int rate;
    String category;
    String discription;

    public Class_SubServices(int sub_service_id, int service_id, int rate, String category, String discription) {
        this.sub_service_id = sub_service_id;
        this.service_id = service_id;
        this.rate = rate;
        this.category = category;
        this.discription = discription;
    }

    public int getSub_service_id() {
        return sub_service_id;
    }

    public void setSub_service_id(int sub_service_id) {
        this.sub_service_id = sub_service_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }
}
