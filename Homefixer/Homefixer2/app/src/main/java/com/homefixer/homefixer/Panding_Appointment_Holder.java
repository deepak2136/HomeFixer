package com.homefixer.homefixer;

import android.widget.TextView;

public class Panding_Appointment_Holder {

    TextView appointment_no, user_id, client_id, order_service, datetime, address;

    Panding_Appointment_Holder() {

    }

    public Panding_Appointment_Holder(TextView appointment_no, TextView user_id, TextView client_id, TextView order_service,
                                      TextView datetime, TextView address) {
        this.appointment_no = appointment_no;
        this.user_id = user_id;
        this.client_id = client_id;
        this.order_service = order_service;
        this.datetime = datetime;
        this.address = address;
    }

    public TextView getAppointment_no() {
        return appointment_no;
    }

    public void setAppointment_no(TextView appointment_no) {
        this.appointment_no = appointment_no;
    }

    public TextView getUser_id() {
        return user_id;
    }

    public void setUser_id(TextView user_id) {
        this.user_id = user_id;
    }

    public TextView getClient_id() {
        return client_id;
    }

    public void setClient_id(TextView client_id) {
        this.client_id = client_id;
    }

    public TextView getOrder_service() {
        return order_service;
    }

    public void setOrder_service(TextView order_service) {
        this.order_service = order_service;
    }

    public TextView getDatetime() {
        return datetime;
    }

    public void setDatetime(TextView datetime) {
        this.datetime = datetime;
    }

    public TextView getAddress() {
        return address;
    }

    public void setAddress(TextView address) {
        this.address = address;
    }
}
