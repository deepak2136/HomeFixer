package com.homefixer.homefixer;

import android.widget.TextView;


public class OrderObject {

    private int order_id;
    private int client_id;
    private int provider_id;
    private int sub_service_id;
    private String order_date;
    private String target_date;
    private String order_time;
    private int ammount;
    private int discount;
    private int net_rate;
    private String status;
    private int offer_id;

    OrderObject() {

    }

    public OrderObject(int order_id, int client_id, int provider_id, int sub_service_id, String order_date, String target_date,
                       String order_time, int ammount, int discount, int net_rate, String status, int offer_id) {
        this.order_id = order_id;
        this.client_id = client_id;
        this.provider_id = provider_id;
        this.sub_service_id = sub_service_id;
        this.order_date = order_date;
        this.target_date = target_date;
        this.order_time = order_time;
        this.ammount = ammount;
        this.discount = discount;
        this.net_rate = net_rate;
        this.status = status;
        this.offer_id = offer_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(int provider_id) {
        this.provider_id = provider_id;
    }

    public int getSub_service_id() {
        return sub_service_id;
    }

    public void setSub_service_id(int sub_service_id) {
        this.sub_service_id = sub_service_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getTarget_date() {
        return target_date;
    }

    public void setTarget_date(String target_date) {
        this.target_date = target_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getNet_rate() {
        return net_rate;
    }

    public void setNet_rate(int net_rate) {
        this.net_rate = net_rate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }
}