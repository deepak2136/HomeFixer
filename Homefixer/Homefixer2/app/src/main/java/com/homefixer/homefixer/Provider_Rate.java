package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
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

public class Provider_Rate extends AppCompatActivity {

    Button proceed;
    ListView listView;
    ArrayList<Class_Review> arrayList;
    private int id;
   // FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider__rate);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.SP_rate);
        arrayList = new ArrayList<Class_Review>();
     //   fab = (FloatingActionButton) findViewById(R.id.FBTN_go5);
      //  fab.bringToFront();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Toast.makeText(Provider_Rate.this, "In Methode Dud e   " + id, Toast.LENGTH_LONG).show();
        StringEntity stringEntity = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(Provider_Rate.this, getResources().getString(R.string.web_url) + "get_user_reviews", stringEntity, "application/json", new JsonHttpResponseHandler() {
            ProgressDialog progressDialog;
            JSONObject jsonObject;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                   // Toast.makeText(Provider_Rate.this, "Success", Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = response.getJSONArray("d");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        arrayList.add(new Class_Review(
                                jsonObject.getInt("review_id"),
                                jsonObject.getInt("login_id"),
                                jsonObject.getInt("reviewer_id"),
                                jsonObject.getString("review"),
                                (float) jsonObject.getDouble("rate"),
                                jsonObject.getString("date"),
                                jsonObject.getString("time")

                        ));
                        Toast.makeText(Provider_Rate.this, arrayList.get(i).getDate() + "    " + arrayList.size(), Toast.LENGTH_LONG).show();
                        listView.setAdapter(new Review_Adapter(Provider_Rate.this, arrayList));
                    }
                } catch (Exception e) {
                    Toast.makeText(Provider_Rate.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Provider_Rate.this, "Failed Check Code !", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Provider_Rate.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

        // call get_user_review webservice
        // adpter REview_adpter
        // holder review_holder
        //good night
    }
}
