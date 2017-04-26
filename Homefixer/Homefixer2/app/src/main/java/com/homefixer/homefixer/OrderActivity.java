package com.homefixer.homefixer;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private LinearLayoutManager lLayout;
    private GoogleApiClient client;
    private int login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(null);

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        if (sharedPreferences.getString("login_state", "logout").equalsIgnoreCase("login"))
            login_id = sharedPreferences.getInt("login_id", 0);

        List<OrderObject> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(OrderActivity.this);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        OrderViewAdapter rcAdapter = new OrderViewAdapter(OrderActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private List<OrderObject> getAllItemList() {

        List<OrderObject> allItems = new ArrayList<OrderObject>();


        return allItems;
    }
}


