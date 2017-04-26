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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AppointmentContact extends AppCompatActivity {

    TextView name, contact, call, email, semail, degree, speciality, exp;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_contact);

        contact = (TextView) findViewById(R.id.appcontact);
        name = (TextView) findViewById(R.id.appname);
        call = (TextView) findViewById(R.id.appcall);
        email = (TextView) findViewById(R.id.appemail);
        semail = (TextView) findViewById(R.id.appemail1);
        degree = (TextView) findViewById(R.id.appdergree);
        speciality = (TextView) findViewById(R.id.appspe);
        exp = (TextView) findViewById(R.id.appexp);

        Intent intent = getIntent();
        id = intent.getIntExtra("provider_id", 3136);

        loadData(id);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"  + contact.getText().toString().trim()));
                startActivity(intent);

            }
        });

        semail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void loadData(int id) {
        StringEntity stringEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(AppointmentContact.this, getResources().getString(R.string.web_url) + "contactDetails", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    //Toast.makeText(AppointmentContact.this,"check",Toast.LENGTH_LONG).show();
                    JSONObject object = response.getJSONObject("d");
                    name.setText(object.getString("name"));
                    contact.setText(object.getString("contact"));
                    email.setText(object.getString("email"));
                    exp.setText(object.getString("exp"));
                    speciality.setText(object.getString("spe"));
                    degree.setText(object.getString("degree"));

                } catch (Exception e) {
                    Toast.makeText(AppointmentContact.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AppointmentContact.this, "Check your code please Appointment Contact ", Toast.LENGTH_LONG).show();
                Toast.makeText(AppointmentContact.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(AppointmentContact.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }

    private void openCall() {

    }

    protected void sendEmail() {

        String[] TO = {email.getText().toString().trim()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Appointment");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AppointmentContact.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
