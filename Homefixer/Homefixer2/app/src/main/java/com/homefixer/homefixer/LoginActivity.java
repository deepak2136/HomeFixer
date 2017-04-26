package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    TextView forgetPassword;

    @InjectView(R.id.input_email)
    EditText _emailText;
    @InjectView(R.id.input_password)
    EditText _passwordText;
    @InjectView(R.id.btn_login)
    Button _loginButton;
    @InjectView(R.id.link_signup)
    TextView _signupLink;

    Button BTN_login;
    EditText ET_email, ET_password;
    String mail, password;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String temp = preferences.getString("login_state", "logout");
        if (temp.equalsIgnoreCase("login")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String temp = preferences.getString("login_state", "logout");
        if (temp.equalsIgnoreCase("login")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
        }

        BTN_login = (Button) findViewById(R.id.btn_login);
        ET_email = (EditText) findViewById(R.id.input_email);
        ET_password = (EditText) findViewById(R.id.input_password);
        forgetPassword = (TextView) findViewById(R.id.link_forgetPassword);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ET_email.getText().toString().length() < 4) {
                    Toast.makeText(LoginActivity.this, "Please Enter id First .. ", Toast.LENGTH_SHORT).show();
                    ET_email.requestFocus();
                    return;
                }

                try {

                    Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                    intent.putExtra("login_id", Integer.valueOf(ET_email.getText().toString().trim()));
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "Please Enter id First .. ", Toast.LENGTH_SHORT).show();
                    ET_email.requestFocus();
                    return;
                }
            }
        });

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (ET_email.getText().toString().length() < 4) {
                    Toast.makeText(LoginActivity.this, "Please Enter id First .. ", Toast.LENGTH_SHORT).show();
                    ET_email.requestFocus();
                    return;
                }

                if (ET_password.getText().toString().length() < 4) {
                    Toast.makeText(LoginActivity.this, "Enter password properly", Toast.LENGTH_SHORT).show();
                    ET_password.requestFocus();
                    return;
                }

                mail = ET_email.getText().toString().trim();
                password = ET_password.getText().toString().trim();

                StringEntity stringEntity = null;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", Integer.valueOf(mail));
                    jsonObject.put("password", password);
                    stringEntity = new StringEntity(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("Accept", "application/json");

                client.post(LoginActivity.this, getResources().getString(R.string.web_url) + "check_login", stringEntity, "application/json", new JsonHttpResponseHandler() {

                    ProgressDialog progressDialog;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        JSONArray mJsonArray = null;
                        boolean b;

                        try {
                            b = response.getBoolean("d");

                            if (b) {

                                ET_password.setText(null);
                                ET_email.setText(null);

                                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("login_id", mail);
                                editor.putString("password", password);
                                editor.putString("login_state", "login");
                                editor.commit();

                                Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivityForResult(intent, REQUEST_SIGNUP);

                            } else {
                                Toast.makeText(LoginActivity.this, "Enter valid id and password", Toast.LENGTH_SHORT).show();
                                ET_password.setText(null);
                                ET_email.setText(null);
                                ET_email.requestFocus();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(LoginActivity.this, "check network connection", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        progressDialog = ProgressDialog.show(LoginActivity.this, "Loading", mail + password);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                });
            }
        });


        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id", ET_email.getText().toString());
        outState.putString("password", ET_password.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ET_email.setText(savedInstanceState.getString("id"));
        ET_password.setText(savedInstanceState.getString("password"));
    }
}