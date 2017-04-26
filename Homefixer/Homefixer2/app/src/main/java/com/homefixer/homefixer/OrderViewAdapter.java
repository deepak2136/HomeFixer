package com.homefixer.homefixer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewHolder> {


    private List<OrderObject> orderlist;
    private Context context;

    public OrderViewAdapter(Context context, List<OrderObject> orderlist) {
        this.orderlist = orderlist;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view, null);
        OrderViewHolder rcv = new OrderViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
       /*holder.orderid.setText(orderlist.get(position).getOrderid());
        holder.name.setText(orderlist.get(position).getName());
        holder.mobile.setText(orderlist.get(position).getMobile());
        holder.address.setText(orderlist.get(position).getAddress());
        holder.date.setText(orderlist.get(position).getDate());
        holder.orderitem.setText(orderlist.get(position).getOrderitem());
        holder.orderprice.setText(orderlist.get(position).getOrderprice());
        holder.subtotal.setText(orderlist.get(position).getSubtotal());
        holder.total.setText(orderlist.get(position).getTotal());*/
    }

    @Override
    public int getItemCount() {
        return this.orderlist.size();
    }
}
