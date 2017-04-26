package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ChangePassword extends AppCompatActivity {

    EditText old, new1, new2;
    Button change;
    String login_id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        old = (EditText) findViewById(R.id.CP_old);
        new1 = (EditText) findViewById(R.id.CP_new);
        new2 = (EditText) findViewById(R.id.CP_new1);
        change = (Button) findViewById(R.id.CP_change);

        Intent intent = getIntent();
        login_id = intent.getStringExtra("login_id");
        password = intent.getStringExtra("password");

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (old.getText().toString().length() < 4) {
                    Toast.makeText(ChangePassword.this, "Enter correct Old Password", Toast.LENGTH_LONG).show();
                    old.setText(null);
                    old.requestFocus();
                    return;
                }

                if (new1.getText().toString().length() < 4) {
                    Toast.makeText(ChangePassword.this, "Enter correct new Password", Toast.LENGTH_LONG).show();
                    new1.setText(null);
                    new1.requestFocus();
                    return;
                }

                if (new2.getText().toString().length() < 4) {
                    Toast.makeText(ChangePassword.this, "Enter correct new Password", Toast.LENGTH_LONG).show();
                    new2.setText(null);
                    new2.requestFocus();
                    return;
                }

                if (!new1.getText().toString().trim().equalsIgnoreCase(new2.getText().toString().trim())) {
                    Toast.makeText(ChangePassword.this, "Enter same new password again", Toast.LENGTH_LONG).show();
                    new2.setText(null);
                    new2.requestFocus();
                    return;
                }

                if (!old.getText().toString().trim().equalsIgnoreCase(password.trim())) {
                    Toast.makeText(ChangePassword.this, "Old PAssword don't match enter correct old password", Toast.LENGTH_LONG).show();
                    old.setText(null);
                    old.requestFocus();
                    return;
                }

                updatepassword(new1.getText().toString().trim());
            }
        });

    }

    private void updatepassword(final String new1) {

        StringEntity stringEntity = null;
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_id", login_id);
            jsonObject.put("password", new1);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(ChangePassword.this, getResources().getString(R.string.web_url) + "update_password", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if (response.getBoolean("d")) {


                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("login_id", login_id + "");
                        editor.putString("password", new1);
                        editor.putString("login_state", "login");
                        editor.commit();

                        Intent intent = new Intent(ChangePassword.this, MainActivity.class);
                        Toast.makeText(ChangePassword.this, "Password change successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(ChangePassword.this, "Password can't change successfully", Toast.LENGTH_LONG).show();
                        old.requestFocus();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ChangePassword.this, "Check code in change password class", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(ChangePassword.this, "Updating", "Please Wait");
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
