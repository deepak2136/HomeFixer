package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Select_Provider extends AppCompatActivity {

    ArrayList<Class_Service_Provider> arrayList;
    ListView listView;
    int sub_service_id;
    ProgressDialog progressDialog;
    Serv_provider_adapter provider_adapter;
    int provider_id = 0, provider_no;
    String target_date = null;
    boolean appointment_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__provider2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        target_date = intent.getStringExtra("target_date");

        if (target_date.length() < 2)
            appointment_state = true;
        else
            appointment_state = false;

        loadData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FBTN_go);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                provider_no = arrayList.size();
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(provider_no);
                provider_id = arrayList.get(randomInt).getUser_id();

                Intent intent = new Intent(Select_Provider.this, Confirm_Order.class);
                intent.putExtra("id", sub_service_id);
                intent.putExtra("provider_id", provider_id);
                intent.putExtra("target_date", target_date);
                startActivity(intent);
            }
        });
    }

    private void loadData() {

        final Intent intent = getIntent();
        sub_service_id = intent.getIntExtra("sub_service_id", 110030);
        listView = (ListView) findViewById(R.id.LV_servPro);
        arrayList = new ArrayList<Class_Service_Provider>();

        StringEntity stringEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sub_service_id", sub_service_id);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(Select_Provider.this, getResources().getString(R.string.web_url) + "get_service_sbid", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Toast.makeText(Select_Provider.this, "Success " + sub_service_id, Toast.LENGTH_LONG).show();
                JSONObject jsonObject1;
                try {
                    int user_id;
                    int service_id;
                    String service_area;
                    String service_time_start;
                    String service_time_end;
                    String experience;
                    String degree;
                    String speciality;

                    JSONArray jsonArray = response.getJSONArray("d");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject1 = jsonArray.getJSONObject(i);

                        user_id = jsonObject1.getInt("user_id");
                        service_id = jsonObject1.getInt("service_id");
                        sub_service_id = sub_service_id;
                        service_area = jsonObject1.getString("service_area");
                        service_time_start = jsonObject1.getString("service_time_start");
                        service_time_end = jsonObject1.getString("service_time_end");
                        experience = jsonObject1.getString("experience");
                        degree = jsonObject1.getString("degree");
                        speciality = jsonObject1.getString("speciality");

                        arrayList.add(new Class_Service_Provider(user_id, service_id, sub_service_id, service_area, service_time_start, service_time_end,
                                experience, degree, speciality));
                        // Toast.makeText(Select_Provider.this, sub_service_id+"", Toast.LENGTH_LONG).show();
                    }

                    listView.setAdapter(new Serv_provider_adapter(arrayList, Select_Provider.this));


                } catch (Exception e) {
                    Toast.makeText(Select_Provider.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
              //  Toast.makeText(Select_Provider.this, "Check your code please in Select ", Toast.LENGTH_LONG).show();
                Toast.makeText(Select_Provider.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Select_Provider.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                    if (appointment_state) {
                        Intent intent1 = new Intent(Select_Provider.this, AppointmentContact.class);
                        int id1 = arrayList.get(position).getUser_id();
                        intent1.putExtra("provider_id", id1);
                        startActivity(intent1);
                    } else {
                        Intent intent1 = new Intent(Select_Provider.this, Provider_Rate.class);
                        int id1 = arrayList.get(position).getUser_id();
                        intent1.putExtra("id", id1);
                        intent1.putExtra("sub_service_id", arrayList.get(position).getSub_service_id());

                        //for random service provider

                        //random service provider

                        //Toast.makeText(Select_Provider.this, "Clicked    " + id1, Toast.LENGTH_LONG).show();
                        startActivity(intent1);
                    }
                } catch (Exception e) {
                    Toast.makeText(Select_Provider.this, "Ckeck exception", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}