package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Confirm_Order extends AppCompatActivity {

    TextView order_id, service, name, mobile_no, address, date, total, disc, ammount;
    Class_Login loginData;
    Class_SubServices serviceData;
    int price, discount, netPrice;
    TextView cancel;
    TextView confirm;
    int login_id = 0, sub_service_id = 0, provider_id = 0, orderID = 0;
    String target_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_view);

        order_id = (TextView) findViewById(R.id.orderid1);
        service = (TextView) findViewById(R.id.orderservice);
        name = (TextView) findViewById(R.id.name1);
        mobile_no = (TextView) findViewById(R.id.mobile1);
        address = (TextView) findViewById(R.id.add1);
        date = (TextView) findViewById(R.id.date1);
        ammount = (TextView) findViewById(R.id.orderprice);
        disc = (TextView) findViewById(R.id.subtotal1);
        total = (TextView) findViewById(R.id.Total1);
        confirm = (TextView) findViewById(R.id.BTN_confirm);
        cancel = (TextView) findViewById(R.id.cancel);

        Intent intent = getIntent();

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            String id = sharedPreferences.getString("login_id", "3136");
            login_id = Integer.parseInt(id.trim());
            sub_service_id = intent.getIntExtra("id", 110001);
          //  Toast.makeText(Confirm_Order.this, sub_service_id + "", Toast.LENGTH_LONG).show();
          //  Toast.makeText(Confirm_Order.this, sub_service_id + "", Toast.LENGTH_LONG).show();
          //  Toast.makeText(Confirm_Order.this, sub_service_id + "", Toast.LENGTH_LONG).show();
            provider_id = intent.getIntExtra("provider_id", 3094);
            target_date = intent.getStringExtra("target_date");
            date.setText(target_date);
            discount = intent.getIntExtra("discount", 0);
        } catch (NullPointerException e) {
            Toast.makeText(Confirm_Order.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        loadServiceData(sub_service_id);
        loadLoginData(login_id);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addOrder();
        /*        if (address.getText().length() < 10) {
                    Toast.makeText(Confirm_Order.this, "Please check code!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (orderID == -1 || orderID < 100) {
                    Toast.makeText(Confirm_Order.this, "failed add order" + orderID, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Confirm_Order.this, "order place successfully", Toast.LENGTH_LONG).show();
                    Toast.makeText(Confirm_Order.this, "order_id is" + orderID, Toast.LENGTH_LONG).show();
                }*/
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Confirm_Order.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    private void loadLoginData(int login_id) {
        StringEntity stringEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", login_id);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(Confirm_Order.this, getResources().getString(R.string.web_url) + "get_login_details", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog1;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONObject jsonObject1 = response.getJSONObject("d");
                    String temp;
                    loginData = new Class_Login();
                    temp = jsonObject1.getString("fname");
                    loginData.setFname(temp);
                    temp = jsonObject1.getString("mname");
                    loginData.setMname(temp);
                    temp = jsonObject1.getString("lname");
                    loginData.setLname(temp);
                    temp = jsonObject1.getString("address_line1");
                    loginData.setAddress_line1(temp);
                    temp = jsonObject1.getString("address_line2");
                    loginData.setAddress_line2(temp);
                    temp = jsonObject1.getString("area_name");
                    loginData.setArea_name(temp);
                    temp = jsonObject1.getString("city_name");
                    loginData.setCity_name(temp);
                    temp = jsonObject1.getString("state_name");
                    loginData.setState_name(temp);
                    temp = jsonObject1.getString("pincode");
                    loginData.setPincode(Integer.parseInt(temp));
                    temp = jsonObject1.getString("mail");
                    loginData.setMail(temp);
                    temp = jsonObject1.getString("contact_no");
                    loginData.setContact_no(temp);

                    name.setText(loginData.getMname() + " " + loginData.getFname());
                    mobile_no.setText(loginData.getContact_no());
                    address.setText(
                            loginData.getAddress_line1() + "\n" +
                                    loginData.getAddress_line2() + "\n" +
                                    loginData.getArea_name() + ", " + loginData.getPincode() + "\n" +
                                    loginData.getState_name() + " " + loginData.getCity_name()
                    );

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Confirm_Order.this, "Check your code please in login_details ", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog1 = ProgressDialog.show(Confirm_Order.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog1.isShowing())
                    progressDialog1.dismiss();
            }
        });
    }

    private void loadServiceData(int sub_service_id) {

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

        client.post(Confirm_Order.this, getResources().getString(R.string.web_url) + "get_subservice_details", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONObject jsonObject1 = response.getJSONObject("d");
                    serviceData = new Class_SubServices(jsonObject1.getInt("service_id"), jsonObject1.getInt("sub_service_id"), jsonObject1.getInt("rate"),
                            jsonObject1.getString("category"), jsonObject1.getString("discription"));

                    price = jsonObject1.getInt("rate");
                   // discount = 0;
                    discount = (int)(price * discount / 100);
                    netPrice = price - discount;
                 //   Toast.makeText(Confirm_Order.this, price + "", Toast.LENGTH_LONG).show();
                  //  Toast.makeText(Confirm_Order.this,discount + "", Toast.LENGTH_LONG).show();
                    ammount.setText(price + "");
                    disc.setText(discount + "");
                    total.setText(netPrice + "");
                    service.setText(jsonObject1.getString("category"));
                    order_id.setText("******");

                } catch (JSONException e) {
                    Toast.makeText(Confirm_Order.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Confirm_Order.this, "Check your code please  ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Confirm_Order.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }


    private void addOrder() {

        StringEntity entity = null;
        JSONObject object;
        try {
            object = new JSONObject();
            object.put("client_id", login_id);
            object.put("provider_id", provider_id);
            object.put("sub_service_id", sub_service_id);
            object.put("target_date", target_date.trim());
            //Toast.makeText(Confirm_Order.this, "taget Date " + target_date, Toast.LENGTH_LONG).show();

            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
            String dateTemp = ft.format(dNow);
            object.put("order_date", dateTemp);

            ft = new SimpleDateFormat("hh:mm:ss");
            dateTemp = ft.format(dNow);
            object.put("order_time", dateTemp);

            object.put("ammount", Integer.valueOf(ammount.getText().toString().trim()));
            object.put("discount", Integer.valueOf(disc.getText().toString().toString()));
            object.put("net_rate", Integer.valueOf(total.getText().toString().trim()));
            object.put("status", "panding");
            object.put("offer_id", 0);
            entity = new StringEntity(object.toString());
        } catch (Exception e) {
            Toast.makeText(Confirm_Order.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        AsyncHttpClient client1 = new AsyncHttpClient();
        client1.addHeader("Accept", "application/json");


        client1.post(Confirm_Order.this, getResources().getString(R.string.web_url) + "add_order", entity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    int success = response.getInt("d");
                    if (success > 0) {
                        Toast.makeText(Confirm_Order.this, "Your Order ID is " + success, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Confirm_Order.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(Confirm_Order.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Confirm_Order.this, "on failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Confirm_Order.this, "Loading", "Please wait");
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
