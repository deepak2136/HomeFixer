package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ExpandbleList extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<Class_SubServices> array;
    private int SID = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_expandble, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_go:
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandble_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        final ExpandableListView expandableListView;

        try {
            Intent intent = getIntent();
            SID = intent.getIntExtra("SID", 111001);
        } catch (Exception e) {

        }

        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expandableListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                if (childPosition == 4) {
                    try {
                        ArrayList<String> temp = (ArrayList<String>) listDataChild.get(listDataHeader.get(groupPosition));
                        int sub_service_id = Integer.valueOf(temp.get(0).trim().substring(temp.get(0).trim().length() - 6));
                        Intent intent = new Intent(ExpandbleList.this, AppoinmentActivity.class);
                        intent.putExtra("sub_service_id", sub_service_id);
                        intent.putExtra("SID",Integer.valueOf(SID));
                        intent.putExtra("rate", array.get(groupPosition).getRate());
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                //Toast.makeText(ExpandbleList.this, id + " and pos " + groupPosition, Toast.LENGTH_LONG).show();
             /*   try {
                    ArrayList<String> temp = (ArrayList<String>) listDataChild.get(listDataHeader.get(groupPosition));
                    int sub_service_id = Integer.valueOf(temp.get(0).trim().substring(temp.get(0).trim().length() - 6));
                    Intent intent = new Intent(ExpandbleList.this, AppoinmentActivity.class);
                    intent.putExtra("sub_service_id", sub_service_id);
                    intent.putExtra("rate", array.get(groupPosition).getRate());
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
*/
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                // Toast.makeText(getApplicationContext(),
                //    listDataHeader.get(groupPosition) + " Expanded",
                //    Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //   Toast.makeText(getApplicationContext(),
                //        listDataHeader.get(groupPosition) + " Collapsed",
                //       Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == event.KEYCODE_BACK) {
            Intent intent = new Intent(ExpandbleList.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void prepareListData() {

        array = new ArrayList<Class_SubServices>();

        StringEntity stringEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("service_id", SID);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(ExpandbleList.this, getResources().getString(R.string.web_url) + "get_subservices_byservice", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONArray jsonArray = response.getJSONArray("d");
                    JSONObject jsonObject1;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject1 = jsonArray.getJSONObject(i);
                        array.add(new Class_SubServices(jsonObject1.getInt("service_id"), jsonObject1.getInt("sub_service_id"), jsonObject1.getInt("rate"),
                                jsonObject1.getString("category"), jsonObject1.getString("discription")));
                    }

                    for (int i = 0; i < array.size(); i++) {
                        listDataHeader.add(array.get(i).getCategory());

                        List<String> addList = new ArrayList<String>();
                        addList.add("Main Service ID :- " + array.get(i).getService_id() + "");
                        addList.add("Service ID :- " + array.get(i).getSub_service_id() + "");
                        addList.add("Description :- " + array.get(i).getDiscription());
                        addList.add("Rate :- " + array.get(i).getRate());
                        addList.add("Proceed further  >>>>> ");
                        listDataChild.put(array.get(i).getCategory(), addList);
                    }

                    listAdapter = new ExpandableListAdapter(ExpandbleList.this, listDataHeader, listDataChild);
                    expListView.setAdapter(listAdapter);

                } catch (JSONException e) {
                    Toast.makeText(ExpandbleList.this, e.getMessage() + SID, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ExpandbleList.this, "Check your code please  " + SID, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(ExpandbleList.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(ExpandbleList.this, buttonView.getId() + " ", Toast.LENGTH_LONG).show();
    }
}