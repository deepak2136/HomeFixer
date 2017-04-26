package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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

public class Pandin_Appointment extends AppCompatActivity {

    ListView listView;
    ArrayList<Class_Appointment> arrayList;
    private int login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandin__appointment);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.LV_panApp);
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

        client.post(Pandin_Appointment.this, getResources().getString(R.string.web_url) + "get_panding_appointments", stringEntity, "application/json", new JsonHttpResponseHandler() {

            JSONObject jsonObject;
            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Toast.makeText(Pandin_Appointment.this, "on success got it dude", Toast.LENGTH_LONG).show();
                try {

                    JSONArray jsonArray = response.getJSONArray("d");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new Class_Appointment(
                                jsonObject.getInt("appointment_no"),
                                jsonObject.getInt("user_id"),
                                jsonObject.getInt("client_id"),
                                jsonObject.getString("order_service"),
                                jsonObject.getString("date"),
                                jsonObject.getString("time"),
                                jsonObject.getString("address"),
                                jsonObject.getString("status")
                        ));
                    }
                } catch (Exception e) {
                   // Toast.makeText(Pandin_Appointment.this, "error in add data in array list", Toast.LENGTH_LONG).show();
                   // Toast.makeText(Pandin_Appointment.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                listView.setAdapter(new Panding_Appointment_Adapter(Pandin_Appointment.this, arrayList));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Pandin_Appointment.this, "Please check code in Panding appointment activity", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Pandin_Appointment.this, "Loading", "Please Wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });


        //pandin call post method
        // paramer to method is login id of client
        //holder PNading_appoint_holder
        //PNading_appoint_adapter
    }
}
