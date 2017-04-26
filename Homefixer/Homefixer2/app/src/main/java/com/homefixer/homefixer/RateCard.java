package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Intent;
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
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RateCard extends AppCompatActivity {

    ArrayList<Class_RateCard> arrayList;
    ListView listView;
    int service_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_card);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        arrayList = new ArrayList<Class_RateCard>();
        listView = (ListView) findViewById(R.id.RC_list);
        Intent intent = getIntent();
        service_id = intent.getIntExtra("service_id", 111001);
       // Toast.makeText(RateCard.this, service_id + "", Toast.LENGTH_LONG).show();

        StringEntity stringEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("service_id", service_id);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            Toast.makeText(RateCard.this, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(RateCard.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(RateCard.this, getResources().getString(R.string.web_url) + "get_all_subService", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

               // Toast.makeText(RateCard.this, "Success" + "", Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = response.getJSONArray("d");
                    JSONObject jsonObject1;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject1 = jsonArray.getJSONObject(i);
                        arrayList.add(new Class_RateCard(jsonObject1.getInt("sub_service_id"), service_id, jsonObject1.getInt("rate"),
                                jsonObject1.getString("category"), jsonObject1.getString("discription")));
                    }
                    Toast.makeText(RateCard.this, arrayList.size() + "", Toast.LENGTH_LONG).show();
                    listView.setAdapter(new RateCardAdapter(RateCard.this, arrayList));
                } catch (JSONException e) {
                    Toast.makeText(RateCard.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(RateCard.this, "Check your code please  ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(RateCard.this, "Loading", "Please wait");
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
