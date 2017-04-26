package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class Panding_Order extends AppCompatActivity {

    ListView listView;
    ArrayList<Class_Order> arrayList;
    int login_id;
    TextView cancel;
    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panding__order);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.LV_pandindOrder);
        arrayList = new ArrayList<>();
        cancel = (TextView) findViewById(R.id.cancel_order);
        layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       /* View convertView = layoutInflater.inflate(R.layout.offer_item, null);
        cancel = (TextView) convertView.findViewById(R.id.cancel_order);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Panding_Order.this,"............dddddddddd....",Toast.LENGTH_LONG).show();
            }
        });*/

        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_speed_dial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                String extra;
                switch (menuItem.getItemId()) {
                    case R.id.MN_allOrder:
                        loadData(" ");
                        break;
                    case R.id.MN_upcomOrder:
                        extra = "  and status='panding' order by target_date desc ";
                        loadData(extra);
                        break;
                    case R.id.MN_panOrder:
                        extra = " and status='panding' ";
                        loadData(extra);
                        break;
                    case R.id.MN_assOrder:
                        extra = " order by order_date, order_time";
                        loadData(extra);
                        break;
                    case R.id.MN_desOrder:
                        extra = " order by order_date desc, order_time desc ";
                        loadData(extra);
                        break;

                }
                return false;
            }
        });

        loadData();
    }

    private void loadData() {

        arrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("login_state", "logout").equalsIgnoreCase("login"))
            login_id = Integer.valueOf(sharedPreferences.getString("login_id", "0"));

        StringEntity stringEntity = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", login_id);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(Panding_Order.this, getResources().getString(R.string.web_url) + "get_panding_order", stringEntity, "application/json", new JsonHttpResponseHandler() {

            JSONObject jsonObject;
            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Toast.makeText(Panding_Order.this, "on success got it dude", Toast.LENGTH_LONG).show();
                try {

                    JSONArray jsonArray = response.getJSONArray("d");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new Class_Order(

                                        jsonObject.getInt("order_id"),
                                        jsonObject.getInt("client_id"),
                                        jsonObject.getInt("provider_id"),
                                        jsonObject.getInt("sub_service_id"),
                                        jsonObject.getString("target_date"),
                                        jsonObject.getString("order_date"),
                                        jsonObject.getString("order_time"),
                                        jsonObject.getInt("ammount"),
                                        jsonObject.getInt("discount"),
                                        jsonObject.getInt("net_rate"),
                                        jsonObject.getString("status"),
                                        jsonObject.getInt("offer_id"),
                                        jsonObject.getString("service_name"),
                                        jsonObject.getString("name")
                                )
                        );
                    }

                } catch (Exception e) {
                  //  Toast.makeText(Panding_Order.this, "error in add data in array list", Toast.LENGTH_LONG).show();
                   // Toast.makeText(Panding_Order.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                listView.setAdapter(new Panding_Order_Adapter(Panding_Order.this, arrayList));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Panding_Order.this, "Please check code in Panding appointment activity", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Panding_Order.this, "Loading", "Please Wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }


    private void loadData(String extra) {

        arrayList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("login_state", "logout").equalsIgnoreCase("login"))
            login_id = Integer.valueOf(sharedPreferences.getString("login_id", "0"));

        StringEntity stringEntity = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", login_id);
            jsonObject.put("extra", extra);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(Panding_Order.this, getResources().getString(R.string.web_url) + "get_panding_orderExtra", stringEntity, "application/json", new JsonHttpResponseHandler() {

            JSONObject jsonObject;
            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Toast.makeText(Panding_Order.this, "on success got it dude", Toast.LENGTH_LONG).show();
                try {

                    JSONArray jsonArray = response.getJSONArray("d");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new Class_Order(

                                        jsonObject.getInt("order_id"),
                                        jsonObject.getInt("client_id"),
                                        jsonObject.getInt("provider_id"),
                                        jsonObject.getInt("sub_service_id"),
                                        jsonObject.getString("target_date"),
                                        jsonObject.getString("order_date"),
                                        jsonObject.getString("order_time"),
                                        jsonObject.getInt("ammount"),
                                        jsonObject.getInt("discount"),
                                        jsonObject.getInt("net_rate"),
                                        jsonObject.getString("status"),
                                        jsonObject.getInt("offer_id"),
                                        jsonObject.getString("service_name"),
                                        jsonObject.getString("name")
                                )
                        );
                    }

                } catch (Exception e) {
                    //Toast.makeText(Panding_Order.this, "error in add data in array list", Toast.LENGTH_LONG).show();
                   // Toast.makeText(Panding_Order.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                listView.setAdapter(new Panding_Order_Adapter(Panding_Order.this, arrayList));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Panding_Order.this, "Please check code in Panding appointment activity", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Panding_Order.this, "Loading", "Please Wait");
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
