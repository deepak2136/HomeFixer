package com.homefixer.homefixer;

public class Class_Appointment {

    private int appointment_no;
    private int user_id;
    private int client_id;
    private String order_service;
    private String date;
    private String time;
    private String address;
    private String state;

    public Class_Appointment(int appointment_no, int user_id, int client_id, String order_service,
                             String date, String time, String address, String state) {
        this.appointment_no = appointment_no;
        this.user_id = user_id;
        this.client_id = client_id;
        this.order_service = order_service;
        this.date = date;
        this.time = time;
        this.address = address;
        this.state = state;
    }

    Class_Appointment() {

    }

    public int getAppointment_no() {
        return appointment_no;
    }

    public void setAppointment_no(int appointment_no) {
        this.appointment_no = appointment_no;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getOrder_service() {
        return order_service;
    }

    public void setOrder_service(String order_service) {
        this.order_service = order_service;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
