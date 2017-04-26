package com.homefixer.homefixer;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class AppoinmentActivity extends AppCompatActivity {

    Button btn;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    HashMap<String, String> data;
    Button order, appoint, change, cDate;
    EditText addres, date;
    int rate;
    private int sub_service_id;
    String id;
    EditText edit;
    private int flag;
    int service_id;
    boolean offer = false;
    Intent intent;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("flag", flag);
        outState.putInt("sub_service_id", sub_service_id);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        flag = savedInstanceState.getInt("flag");
        sub_service_id = savedInstanceState.getInt("sub_service_id");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoinment);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        flag = 0;

        data = new HashMap<String, String>();
        order = (Button) findViewById(R.id.BTN_app_pro);
        appoint = (Button) findViewById(R.id.btn_app_app);
        addres = (EditText) findViewById(R.id.ET_addess);
        cDate = (Button) findViewById(R.id.date_button);
        change = (Button) findViewById(R.id.BTN_change);
        date = (EditText) findViewById(R.id.editText);
        addres.setEnabled(false);

        intent = getIntent();
        try {
            service_id = intent.getIntExtra("SID", 111001);
            sub_service_id = intent.getIntExtra("sub_service_id", 0);
            rate = intent.getIntExtra("rate", 0);
            if( rate == 0 )
                offer = true;
            else
                offer = false;
        }
        catch (Exception e)
        {
            offer = true;
        }

        cDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    String tDate = date.getText().toString().trim();
                    String[] check = tDate.split("/");
                    Toast.makeText(AppoinmentActivity.this, check[0] + "\t" + check[1] + "\t" + check[2], Toast.LENGTH_LONG).show();
                    if (Integer.valueOf(check[0].trim()) < calendar.get(Calendar.YEAR)) {
                        Toast.makeText(AppoinmentActivity.this, "Please Select Valid Year", Toast.LENGTH_LONG).show();
                        cDate.performClick();
                        return;
                    }
                    if (Integer.valueOf(check[1].trim()) < calendar.get(Calendar.MONTH)) {
                        Toast.makeText(AppoinmentActivity.this, "Please Select Valid Month", Toast.LENGTH_LONG).show();
                        cDate.performClick();
                        return;
                    }
                    if (Integer.valueOf(check[2].trim()) < calendar.get(Calendar.DATE)) {
                        Toast.makeText(AppoinmentActivity.this, "Please Select Valid date", Toast.LENGTH_LONG).show();
                        cDate.performClick();
                        return;
                    }
                    date.setText(check[2].trim() + "/" + check[1].trim() + "/" + check[0].trim());
                } catch (Exception e) {
                    Toast.makeText(AppoinmentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        change.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                Intent i = new Intent(AppoinmentActivity.this, ChangeAddress.class);
                startActivity(i);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("login_id", "");

        StringEntity stringEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", Integer.valueOf(id.toString().trim()));
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DATE);
        showDialogOnButtonClick();

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(AppoinmentActivity.this, getResources().getString(R.string.web_url) + "get_login_details", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog1;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONObject jsonObject1 = response.getJSONObject("d");
                    String temp;
                    temp = jsonObject1.getString("fname");
                    data.put("fname", temp);
                    temp = jsonObject1.getString("mname");
                    data.put("mname", temp);
                    temp = jsonObject1.getString("lname");
                    data.put("lname", temp);
                    temp = jsonObject1.getString("address_line1");
                    data.put("address_line1", temp);
                    temp = jsonObject1.getString("address_line2");
                    data.put("address_line2", temp);
                    temp = jsonObject1.getString("area_name");
                    data.put("area_name", temp);
                    temp = jsonObject1.getString("city_name");
                    data.put("city_name", temp);
                    temp = jsonObject1.getString("state_name");
                    data.put("state_name", temp);
                    temp = jsonObject1.getString("pincode");
                    data.put("pincode", temp);
                    temp = jsonObject1.getInt("pincode") + "";
                    data.put("pincode", temp);
                    temp = jsonObject1.getString("mail");
                    data.put("mail", temp);
                    temp = jsonObject1.getString("contact_no");
                    data.put("contact_no", temp);

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                addres.setText(
                        data.get("address_line1") + "\n" + data.get("address_line2") + " " +
                                data.get("area_name") + "\n" + data.get("city_name") + ", " + data.get("pincode")
                                + "\n" + data.get("state_name") + "\n" + data.get("mail") + "\n" +
                                data.get("contact_no")
                );

                SharedPreferences sharedPreferences1 = getSharedPreferences("temp_address", Context.MODE_PRIVATE);
                if (sharedPreferences1.getString("line1", "").length() > 2) {
                    addres.setText(
                            sharedPreferences1.getString("line1", data.get("address_line1")) + "\n" + sharedPreferences1.getString("line2", data.get("address_line2")) + " " +
                                    sharedPreferences1.getString("area", data.get("area_name")) + "\n" + sharedPreferences1.getString("city", data.get("city_name")) + ", " + sharedPreferences1.getString("pincode", data.get("pincode"))
                                    + "\n" + data.get("state_name") + "\n" + data.get("mail") + "\n" +
                                    data.get("contact_no")
                    );

                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("line1", "");
                    editor.putString("line2", "");
                    editor.putString("area", "");
                    editor.putString("city", "");
                    editor.putString("pincode", "");
                    editor.commit();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AppoinmentActivity.this, "Check your code please in login_details ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog1 = ProgressDialog.show(AppoinmentActivity.this, "Loading", "Please wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog1.isShowing())
                    progressDialog1.dismiss();
            }
        });

        order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addres.getText().length() < 20) {
                    addres.requestFocus();
                    addres.setText(null);
                    Toast.makeText(AppoinmentActivity.this, "Enter Proper address first", Toast.LENGTH_LONG).show();
                    return;
                }

                if (edit.getText().toString().trim().length() < 6) {
                    edit.requestFocus();
                    Toast.makeText(AppoinmentActivity.this, "first selcect date" + edit.getText().toString(), Toast.LENGTH_LONG).show();
                    return;
                }
                String[] check = new String[3];
                try {
                    Calendar calendar = Calendar.getInstance();
                    String tDate = date.getText().toString().trim();
                    check = tDate.split("/");
                    //Toast.makeText(AppoinmentActivity.this, check[0] + "\t" + check[1] + "\t" + check[2], Toast.LENGTH_LONG).show();
                    if (Integer.valueOf(check[0].trim()) < calendar.get(Calendar.YEAR)) {
                        Toast.makeText(AppoinmentActivity.this, "Please Select Valid Year", Toast.LENGTH_LONG).show();
                        cDate.performClick();
                        return;
                    }
                    if (Integer.valueOf(check[1].trim()) < calendar.get(Calendar.MONTH)) {
                        Toast.makeText(AppoinmentActivity.this, "Please Select Valid Month", Toast.LENGTH_LONG).show();
                        cDate.performClick();
                        return;
                    }
                    if (Integer.valueOf(check[2].trim()) < calendar.get(Calendar.DATE)) {
                        Toast.makeText(AppoinmentActivity.this, "Please Select Valid date", Toast.LENGTH_LONG).show();
                        cDate.performClick();
                        return;
                    }

                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date d1 = df.parse(check[2].trim() + "/" + check[1].trim() + "/" + check[0].trim());
                    Date d2 = new Date();
                    d2 = df.parse(df.format(d2));
                    long diff = d1.getTime() - d2.getTime();
                    long diffHours = diff / (60 * 60 * 1000);

                    if (diffHours < 24 || diffHours > 120) {
                        Toast.makeText(AppoinmentActivity.this, "Please chose valid date\t" + diffHours, Toast.LENGTH_LONG).show();
                        cDate.performClick();
                        return;
                    }

                } catch (Exception e) {
                    Toast.makeText(AppoinmentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


               if( offer == false )
               {
                   Intent intent1 = new Intent(AppoinmentActivity.this, Select_Provider.class);
                   intent1.putExtra("fname", data.get("fname"));
                   intent1.putExtra("mname", data.get("mname"));
                   intent1.putExtra("lname", data.get("lname"));
                   intent1.putExtra("address_line1", data.get("address_line1"));
                   intent1.putExtra("address_line2", data.get("address_line2"));
                   intent1.putExtra("area_name", data.get("area_name"));
                   intent1.putExtra("city_name", data.get("city_name"));
                   intent1.putExtra("state_name", data.get("state_name"));
                   intent1.putExtra("pincode", data.get("pincode"));
                   intent1.putExtra("mail", data.get("mail"));
                   intent1.putExtra("contact_no", data.get("contact_no"));
                   intent1.putExtra("target_date", check[2].trim() + "/" + check[1].trim() + "/" + check[0].trim());
                   intent1.putExtra("rate", rate);
                   intent1.putExtra("sub_service_id", sub_service_id);
                   startActivity(intent1);
               }
                else
               {
                   Intent intent1 = new Intent(AppoinmentActivity.this,Confirm_Order.class);
                   intent1.putExtra("target_date",check[2].trim() + "/" + check[1].trim() + "/" + check[0].trim());
                   intent1.putExtra("provider_id",intent.getIntExtra("provider_id",3136));
                   intent1.putExtra("id",intent.getIntExtra("ssi",110010));
                   intent1.putExtra("discount",intent.getIntExtra("discount",0));
                   startActivity(intent1);
               }
            }

        });

        appoint.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AppoinmentActivity.this, Select_Provider.class);
                intent1.putExtra("fname", data.get("fname"));
                intent1.putExtra("mname", data.get("mname"));
                intent1.putExtra("lname", data.get("lname"));
                intent1.putExtra("address_line1", data.get("address_line1"));
                intent1.putExtra("address_line2", data.get("address_line2"));
                intent1.putExtra("area_name", data.get("area_name"));
                intent1.putExtra("city_name", data.get("city_name"));
                intent1.putExtra("state_name", data.get("state_name"));
                intent1.putExtra("pincode", data.get("pincode"));
                intent1.putExtra("mail", data.get("mail"));
                intent1.putExtra("contact_no", data.get("contact_no"));
                intent1.putExtra("target_date", " ");
                intent1.putExtra("rate", rate);
                intent1.putExtra("sub_service_id", sub_service_id);
                startActivity(intent1);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == event.KEYCODE_BACK) {
            Intent intent = new Intent(AppoinmentActivity.this, ExpandbleList.class);
            intent.putExtra("SID", service_id);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDialogOnButtonClick() {
        btn = (Button) findViewById(R.id.date_button);
        edit = (EditText) findViewById(R.id.editText);
        btn.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, dPickerListener, year_x, month_x, day_x);
        return null;

    }

    private DatePickerDialog.OnDateSetListener dPickerListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear;
            day_x = dayOfMonth;
            edit.setText(year_x + " / " + (month_x + 1) + " / "
                    + day_x);
        }
    };
}