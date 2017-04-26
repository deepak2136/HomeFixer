package com.homefixer.homefixer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class offer_adapter extends BaseAdapter {

    Context context;
    ArrayList<Class_Offer> arrayList;
    LayoutInflater layoutInflater;

    public offer_adapter(Context context, ArrayList<Class_Offer> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final offer_holder data;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.offer_item, null);
            data = new offer_holder((ImageView) convertView.findViewById(R.id.offer_image));
            convertView.setTag(data);
        } else {
            data = (offer_holder) convertView.getTag();
        }

        Picasso.with(context).load("http://192.168.23.1:12312/pic/" + arrayList.get(position).getOofer_id() + ".jpg").into(data.getImage());

        data.getImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppoinmentActivity.class);
                intent.putExtra("provider_id", arrayList.get(position).getOwner_id());
                intent.putExtra("offer_id", arrayList.get(position).getOofer_id());
                intent.putExtra("discount", arrayList.get(position).getDiscounts());
                intent.putExtra("ssi",arrayList.get(position).getSub_srvice_id());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
