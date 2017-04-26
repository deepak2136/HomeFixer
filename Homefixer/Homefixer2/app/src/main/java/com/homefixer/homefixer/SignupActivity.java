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

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    Button BTN_signup;
    EditText ET_id, ET_pass;
    String id, pass;
    TextView linkLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BTN_signup = (Button) findViewById(R.id.btn_signup);
        ET_id = (EditText) findViewById(R.id.input_email);
        ET_pass = (EditText) findViewById(R.id.input_password);
        linkLogin = (TextView) findViewById(R.id.link_login);

        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ET_id.getText().toString().trim().length() < 4) {
                    Toast.makeText(SignupActivity.this, "Minimum 4 number require ", Toast.LENGTH_SHORT).show();
                    ET_id.requestFocus();
                    return;
                }

                if (ET_pass.getText().toString().trim().length() < 6) {
                    Toast.makeText(SignupActivity.this, "Minimum 6 char require ", Toast.LENGTH_SHORT).show();
                    ET_pass.requestFocus();
                    return;
                }

                id = ET_id.getText().toString().trim();
                pass = ET_pass.getText().toString().trim();

                StringEntity stringEntity = null;
                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("id", Integer.valueOf(id));
                    //jsonObject.put("id",3136);
                    stringEntity = new StringEntity(jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("Accept", "application/json");

                client.post(SignupActivity.this, getResources().getString(R.string.web_url) + "get_login_details", stringEntity, "application/json", new JsonHttpResponseHandler() {

                    ProgressDialog progressDialog;

                 /*   @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONObject jsonObject1 = response.getJSONObject("d");
                            int pincode = jsonObject1.getInt("pincode");

                            if( pincode == 0  )
                            {
                                SharedPreferences sharedPreferences = getSharedPreferences("newLogin", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id",id);
                                editor.putString("password",pass);
                                editor.commit();

                                Intent intent = new Intent(SignupActivity.this, SignupActivity2.class);
                                ET_id.setText(null);
                                ET_pass.setText(null);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SignupActivity.this,"Login Allready exists",Toast.LENGTH_SHORT).show();
                                ET_id.setText(null);
                                ET_pass.setText(null);
                                return;
                            }


                        } catch (JSONException e) {
                            Toast.makeText(SignupActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }*/


                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONObject jsonObject1 = response.getJSONObject("d");
                            int pincode = jsonObject1.getInt("pincode");

                            if (pincode == 0) {
                                SharedPreferences sharedPreferences = getSharedPreferences("newLogin", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id", id);
                                editor.putString("password", pass);
                                editor.commit();

                                Intent intent = new Intent(SignupActivity.this, SignupActivity2.class);
                                ET_id.setText(null);
                                ET_pass.setText(null);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignupActivity.this, "Login Allready exists", Toast.LENGTH_SHORT).show();
                                ET_id.setText(null);
                                ET_pass.setText(null);
                                return;
                            }


                        } catch (JSONException e) {
                            Toast.makeText(SignupActivity.this, e.toString(), Toast.LENGTH_LONG).show();

                            SharedPreferences sharedPreferences = getSharedPreferences("newLogin", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("id", id);
                            editor.putString("password", pass);
                            editor.commit();

                            Intent intent = new Intent(SignupActivity.this, SignupActivity2.class);
                            ET_id.setText(null);
                            ET_pass.setText(null);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);

                        Toast.makeText(SignupActivity.this, "Login Allready exists", Toast.LENGTH_SHORT).show();
                        ET_id.setText(null);
                        ET_pass.setText(null);
                        return;
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        progressDialog = ProgressDialog.show(SignupActivity.this, "Loading", id + pass);
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

    }
}