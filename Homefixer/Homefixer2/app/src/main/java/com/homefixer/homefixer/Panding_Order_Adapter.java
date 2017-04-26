package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Panding_Order_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Class_Order> arrayList;
    LayoutInflater layoutInflater;

    public Panding_Order_Adapter(Context context, ArrayList<Class_Order> arrayList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        Panding_Order_Holder data;
        TextView cancel;
        if (convertView == null) {

            convertView = layoutInflater.inflate(R.layout.order_item, null);
            data = new Panding_Order_Holder(
                    (TextView) convertView.findViewById(R.id.orderid2),
                    (TextView) convertView.findViewById(R.id.name2),
                    (TextView) convertView.findViewById(R.id.orderservice2),
                    (TextView) convertView.findViewById(R.id.date2),
                    (TextView) convertView.findViewById(R.id.orderprice2),
                    (TextView) convertView.findViewById(R.id.subtotal2),
                    (TextView) convertView.findViewById(R.id.Total2)
            );
            convertView.setTag(data);
        } else {
            data = (Panding_Order_Holder) convertView.getTag();
        }

        data.getDate().setText(arrayList.get(position).getTarget_date().trim());
        data.getOrder_id().setText(arrayList.get(position).getOrder_id() + "");
        data.getPrice().setText(arrayList.get(position).getAmmount() + "");
        data.getSubTotal().setText(arrayList.get(position).getAmmount() + "");
        data.getTotal().setText(arrayList.get(position).getNet_rate() + "");
        data.getService().setText(arrayList.get(position).getService_name() + "");
        data.getName().setText(arrayList.get(position).getName() + "");

        cancel = (TextView) convertView.findViewById(R.id.cancel_order);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "vvvvvvvvvvvvvvv", Toast.LENGTH_LONG).show();
                cancelOrder(arrayList.get(position).getOrder_id());
            }
        });


        return convertView;
    }

    private void cancelOrder(int order_id)
    {
        StringEntity stringEntity = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", order_id);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(context, context.getResources().getString(R.string.web_url) + "remove_order", stringEntity, "application/json", new JsonHttpResponseHandler() {

            JSONObject jsonObject;
            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

               // Toast.makeText(context, "on success got it dude", Toast.LENGTH_LONG).show();
                try {
                    String msg = response.getString("d");
                    if (msg.equalsIgnoreCase("remove successfully")) {
                        Toast.makeText(context, "canceled successfully...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,Panding_Order.class);
                        context.startActivity(intent);
                    }
                    else
                        Toast.makeText(context, "Problem to cancel order try after some time", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(context, "error in add data in array lisfailed to cancel order" + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(context, "Please check code in cancel order in order adapter activity", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(context, "Loading", "Please Wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }
}
