package com.homefixer.homefixer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RateCardAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Class_RateCard> mArrayList;
    LayoutInflater mLayoutInflater;

    public RateCardAdapter(Context mContext, ArrayList<Class_RateCard> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RateCardHolder data;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.rate_item, null);
            data = new RateCardHolder((TextView) convertView.findViewById(R.id.RC_service), (TextView) convertView.findViewById(R.id.RC_sub_service), (TextView) convertView.findViewById(R.id.RC_rate),
                    (TextView) convertView.findViewById(R.id.RC_category), (TextView) convertView.findViewById(R.id.RC_discription));
            convertView.setTag(data);
        } else {
            data = (RateCardHolder) convertView.getTag();
        }

        data.getService().setText(mArrayList.get(position).getService_id() + "");
        data.getSub_service().setText(mArrayList.get(position).getSub_service_id() + "");
        data.getRate().setText(mArrayList.get(position).getRate() + "");
        data.getCategory().setText(mArrayList.get(position).getCategory());
        data.getDescription().setText(mArrayList.get(position).getDiscription());

        return convertView;
    }
}
