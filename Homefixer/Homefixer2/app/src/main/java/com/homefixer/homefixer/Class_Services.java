package com.homefixer.homefixer;

public class Class_Services {

    int service_id;
    String name;
    String details;

    public int getService_id() {
        return service_id;
    }

    public Class_Services(int service_id, String name, String details) {
        this.service_id = service_id;
        this.name = name;
        this.details = details;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
