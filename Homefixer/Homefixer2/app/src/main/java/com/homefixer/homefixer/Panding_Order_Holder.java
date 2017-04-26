package com.homefixer.homefixer;

import android.widget.EditText;
import android.widget.TextView;

public class Panding_Order_Holder {

    TextView order_id, service, name, date, price, subTotal, total;

    Panding_Order_Holder() {

    }


    public Panding_Order_Holder(TextView order_id, TextView service, TextView name, TextView date, TextView price, TextView subTotal, TextView total) {
        this.order_id = order_id;
        this.service = service;
        this.name = name;
        this.date = date;
        this.price = price;
        this.subTotal = subTotal;
        this.total = total;
    }

    public TextView getOrder_id() {
        return order_id;
    }

    public void setOrder_id(TextView order_id) {
        this.order_id = order_id;
    }

    public TextView getService() {
        return service;
    }

    public void setService(TextView service) {
        this.service = service;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getPrice() {
        return price;
    }

    public void setPrice(TextView price) {
        this.price = price;
    }

    public TextView getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(TextView subTotal) {
        this.subTotal = subTotal;
    }

    public TextView getTotal() {
        return total;
    }

    public void setTotal(TextView total) {
        this.total = total;
    }
}
