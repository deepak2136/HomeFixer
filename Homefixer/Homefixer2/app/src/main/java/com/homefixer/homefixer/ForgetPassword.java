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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ForgetPassword extends AppCompatActivity {

    EditText ans1, ans2, new1, new2;
    TextView sq1, sq2;
    Button change;
    String a1, a2, s1, s2;
    int login_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ans1 = (EditText) findViewById(R.id.FP_ans1);
        ans2 = (EditText) findViewById(R.id.FP_ans2);
        new1 = (EditText) findViewById(R.id.FP_new1);
        new2 = (EditText) findViewById(R.id.FP_new2);
        sq1 = (TextView) findViewById(R.id.FP_sq1);
        sq2 = (TextView) findViewById(R.id.FP_sq2);
        change = (Button) findViewById(R.id.forgetPassword);

        Intent intent = getIntent();
        login_id = intent.getIntExtra("login_id", 3094);
        Toast.makeText(ForgetPassword.this, login_id + "", Toast.LENGTH_LONG).show();
        loadQue();

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ans1.getText().toString().length() < 2) {
                    Toast.makeText(ForgetPassword.this, "Enter currect answers first", Toast.LENGTH_LONG).show();
                    ans1.requestFocus();
                    //   setDisble();
                    return;
                }

                if (ans2.getText().toString().length() < 2) {
                    Toast.makeText(ForgetPassword.this, "Enter currect answers first", Toast.LENGTH_LONG).show();
                    ans2.requestFocus();
                    //   setDisble();
                    return;
                }

                if (!ans1.getText().toString().trim().equalsIgnoreCase(a1.trim())) {
                    Toast.makeText(ForgetPassword.this, "Enter currect answers first", Toast.LENGTH_LONG).show();
                    ans1.requestFocus();
                    //   setDisble();
                    return;
                }

                if (!ans2.getText().toString().trim().equalsIgnoreCase(a2.trim())) {
                    Toast.makeText(ForgetPassword.this, "Enter currect answers first", Toast.LENGTH_LONG).show();
                    ans2.requestFocus();
                    // setDisble();
                    return;
                }

                if (new1.getText().toString().length() < 4) {
                    Toast.makeText(ForgetPassword.this, "Enter correct new Password", Toast.LENGTH_LONG).show();
                    new1.setText(null);
                    new1.requestFocus();
                    return;
                }

                if (new2.getText().toString().length() < 4) {
                    Toast.makeText(ForgetPassword.this, "Enter correct new Password", Toast.LENGTH_LONG).show();
                    new2.setText(null);
                    new2.requestFocus();
                    return;
                }


                if (!new1.getText().toString().trim().equals(new2.getText().toString().trim())) {
                    Toast.makeText(ForgetPassword.this, "ReEnter currect new Password first", Toast.LENGTH_LONG).show();
                    new2.requestFocus();
                    //setDisble();
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

        client.post(ForgetPassword.this, getResources().getString(R.string.web_url) + "update_password", stringEntity, "application/json", new JsonHttpResponseHandler() {

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

                        Intent intent = new Intent(ForgetPassword.this, Profile.class);
                        Toast.makeText(ForgetPassword.this, "Password change successfully", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgetPassword.this, "Password can't change successfully", Toast.LENGTH_LONG).show();
                        new2.requestFocus();
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ForgetPassword.this, "Check code in change password class", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(ForgetPassword.this, "Updating", "Please Wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }

    private void loadQue() {
        StringEntity stringEntity = null;
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("login_id", login_id);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(ForgetPassword.this, getResources().getString(R.string.web_url) + "get_Login", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject object = response.getJSONObject("d");
                    s1 = object.getString("security_q1");
                    s2 = object.getString("security_q2");
                    a1 = object.getString("ans1");
                    a2 = object.getString("ans2");
                    sq1.setText(s1);
                    sq2.setText(s2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(ForgetPassword.this, "Check code in ForgetPassword password class", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(ForgetPassword.this, "Updating", "Please Wait");
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
