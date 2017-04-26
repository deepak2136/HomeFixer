package com.homefixer.homefixer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Review_Adapter extends BaseAdapter {

    Context mContext;
    ArrayList<Class_Review> arrayList;
    LayoutInflater mLayoutInflater;

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public Review_Adapter(Context mContext, ArrayList<Class_Review> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        Review_Holder data;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.sp_rate_item, null);
            data = new Review_Holder((TextView) convertView.findViewById(R.id.SPR_name), (TextView) convertView.findViewById(R.id.SPR_review), (TextView) convertView.findViewById(R.id.SPR_time));
            convertView.setTag(data);
        } else {
            data = (Review_Holder) convertView.getTag();
        }

        try
        {
            data.getName().setText(arrayList.get(position).getLogin_id() + "");
            data.getReview().setText(arrayList.get(position).getReview());
            data.getTime().setText(arrayList.get(position).getDate() + "  " + arrayList.get(position).getTime());

        }
        catch (NullPointerException e)
        {
            Toast.makeText(convertView.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return convertView;
    }
}
