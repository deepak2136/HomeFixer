package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class Offers extends AppCompatActivity {

    ArrayList<Class_Offer> arrayList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.LV_offer);
        arrayList = new ArrayList<Class_Offer>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Offers.this, "Checking   " + position, Toast.LENGTH_LONG).show();
            }
        });

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-Type", "application/json");

        client.get(Offers.this, getResources().getString(R.string.web_url) + "get_all_offers", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;
            JSONObject jsonObject;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = response.getJSONArray("d");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String check = null;
                        try {
                            check = jsonObject.getString("title");
                            if (check.toString().equalsIgnoreCase("null") || check == null || check.isEmpty())
                                continue;
                        } catch (Exception e1) {
                            Toast.makeText(Offers.this, e1.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        arrayList.add(new Class_Offer(
                                jsonObject.getInt("offer_id"),
                                jsonObject.getInt("sub_service_id"),
                                jsonObject.getString("image"),
                                jsonObject.getString("title"),
                                jsonObject.getString("description"),
                                jsonObject.getInt("owner_id"),
                                jsonObject.getInt("service_type"),
                                jsonObject.getInt("discounts"),
                                jsonObject.getString("start_time"),
                                jsonObject.getString("end_time"),
                                jsonObject.getInt("max_customer")
                        ));
                    }
                    listView.setAdapter(new offer_adapter(Offers.this, arrayList));
                } catch (Exception e) {
                    Toast.makeText(Offers.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Offers.this, "Loading .. ", "Please wait");
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
