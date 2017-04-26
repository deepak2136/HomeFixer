package com.homefixer.homefixer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView orderid;
    public TextView name;
    public TextView mobile;
    public TextView address;
    public TextView date;
    public TextView orderitem;
    public TextView orderprice;
    public TextView subtotal;
    public TextView total;
    public TextView service;

    public OrderViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        orderid = (TextView) itemView.findViewById(R.id.orderid1);
        name = (TextView) itemView.findViewById(R.id.name1);
        mobile = (TextView) itemView.findViewById(R.id.mobile1);
        address = (TextView) itemView.findViewById(R.id.add1);
        date = (TextView) itemView.findViewById(R.id.date1);
        orderitem = (TextView) itemView.findViewById(R.id.orderitem);
        orderprice = (TextView) itemView.findViewById(R.id.orderprice);
        subtotal = (TextView) itemView.findViewById(R.id.subtotal1);
        total = (TextView) itemView.findViewById(R.id.Total1);
        service = (TextView) itemView.findViewById(R.id.orderservice);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Cou Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }

    public TextView getOrderid() {
        return orderid;
    }

    public void setOrderid(TextView orderid) {
        this.orderid = orderid;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getMobile() {
        return mobile;
    }

    public void setMobile(TextView mobile) {
        this.mobile = mobile;
    }

    public TextView getAddress() {
        return address;
    }

    public void setAddress(TextView address) {
        this.address = address;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(TextView orderitem) {
        this.orderitem = orderitem;
    }

    public TextView getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(TextView orderprice) {
        this.orderprice = orderprice;
    }

    public TextView getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(TextView subtotal) {
        this.subtotal = subtotal;
    }

    public TextView getTotal() {
        return total;
    }

    public void setTotal(TextView total) {
        this.total = total;
    }

    public TextView getService() {
        return service;
    }

    public void setService(TextView service) {
        this.service = service;
    }
}
