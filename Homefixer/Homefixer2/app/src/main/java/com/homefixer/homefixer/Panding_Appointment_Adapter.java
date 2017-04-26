package com.homefixer.homefixer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Panding_Appointment_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Class_Appointment> arrayList;
    LayoutInflater layoutInflater;

    public Panding_Appointment_Adapter(Context context, ArrayList<Class_Appointment> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        Panding_Appointment_Holder data;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.panapp_item, null);
            data = new Panding_Appointment_Holder((TextView) convertView.findViewById(R.id.PAP_appNo),
                    (TextView) convertView.findViewById(R.id.PAP_uid),
                    (TextView) convertView.findViewById(R.id.PAP_cid),
                    (TextView) convertView.findViewById(R.id.PAP_ordSer),
                    (TextView) convertView.findViewById(R.id.PAP_dateTime),
                    (TextView) convertView.findViewById(R.id.PAP_add)
            );
            convertView.setTag(data);
        } else {
            data = (Panding_Appointment_Holder) convertView.getTag();
        }

        data.getAddress().setText(arrayList.get(position).getAddress());
        data.getDatetime().setText(arrayList.get(position).getDate() + " " + arrayList.get(position).getTime());
        data.getOrder_service().setText(arrayList.get(position).getOrder_service());
        data.getClient_id().setText(arrayList.get(position).getClient_id() + "");
        data.getUser_id().setText(arrayList.get(position).getUser_id() + "");
        data.getAppointment_no().setText(arrayList.get(position).getAppointment_no() + "");

        return convertView;
    }
}
