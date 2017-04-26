package com.homefixer.homefixer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Serv_provider_adapter extends BaseAdapter {

    ArrayList<Class_Service_Provider> arrayList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public Serv_provider_adapter(ArrayList<Class_Service_Provider> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View convertView, ViewGroup parent) {

        Service_Provider_Holder data;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.all_ser_provider, null);
            data = new Service_Provider_Holder((TextView) convertView.findViewById(R.id.SP_user_id), (TextView) convertView.findViewById(R.id.SP_service_time_start),
                    (TextView) convertView.findViewById(R.id.SP_service_time_end), (TextView) convertView.findViewById(R.id.SP_experience),
                    (TextView) convertView.findViewById(R.id.SP_degree), (TextView) convertView.findViewById(R.id.SP_speciality));
            convertView.setTag(data);

        } else {
            data = (Service_Provider_Holder) convertView.getTag();
        }

        data.getUser_id().setText(arrayList.get(position).getUser_id() + "");
        data.getService_time_start().setText(arrayList.get(position).getService_time_start());
        data.getService_time_end().setText(arrayList.get(position).getService_time_end());
        data.getExperience().setText(arrayList.get(position).getExperience());
        data.getDegree().setText(arrayList.get(position).getDegree());
        data.getSpeciality().setText(arrayList.get(position).getSpeciality());

        return convertView;
    }
}
