package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Feedback extends AppCompatActivity {

    EditText to, msg, sub;
    Button send, home;
    Class_Login loginData;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        to = (EditText) findViewById(R.id.FB_to);
        msg = (EditText) findViewById(R.id.FB_feedback);
        sub = (EditText) findViewById(R.id.FB_subject);
        send = (Button) findViewById(R.id.FB_submit);
        home = (Button) findViewById(R.id.FB_home);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    protected void sendEmail() {

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String login_id = sharedPreferences.getString("login_id", "3094");
        loadLoginData(Integer.valueOf(login_id.trim()));


        Log.i("Send email", "");
        String[] TO = {to.getText().toString().trim(), "deepakprajapati589@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, sub.getText().toString().trim());
        emailIntent.putExtra(Intent.EXTRA_TEXT, data + msg.getText().toString().trim());

        entryFeedback(Integer.parseInt(login_id.trim()), msg.getText().toString().trim());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Feedback.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
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

        client.post(Feedback.this, getResources().getString(R.string.web_url) + "get_login_details", stringEntity, "application/json", new JsonHttpResponseHandler() {

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
                    data = loginData.getMname() + " " + loginData.getLname() + "\n" + loginData.getAddress_line1() + "\n" +
                            loginData.getAddress_line2() + "\n" + loginData.getArea_name() + ", " + loginData.getCity_name() +
                            loginData.getState_name() + " " + loginData.getPincode() + "\n" + loginData.getMail() + "\n" +
                            loginData.getContact_no();

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Feedback.this, "Check your code please in login_details " + throwable.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void entryFeedback(int login_id, String MSG) {
        StringEntity stringEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_id", login_id);
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
            String dateTemp = ft.format(dNow);
            jsonObject.put("date", dateTemp);

            ft = new SimpleDateFormat("hh:mm:ss");
            dateTemp = ft.format(dNow);
            jsonObject.put("time", dateTemp);
            jsonObject.put("feedback", MSG);

            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(Feedback.this, getResources().getString(R.string.web_url) + "add_feedback", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    int success = response.getInt("d");
                    if (success > 0) {
                        Toast.makeText(Feedback.this, "Feedback send successfully", Toast.LENGTH_LONG).show();
                        clearData();

                    }
                } catch (Exception e) {
                    Toast.makeText(Feedback.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

        });

    }

    private void clearData() {
        msg.setText("");
        sub.setText("");
    }
}
