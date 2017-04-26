package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class SignupActivity2 extends AppCompatActivity {
    EditText fname, mname, lname, contact, add1, add2, area, city, ans1, ans2, pincode, pic1, email;
    Spinner sq1, sq2, state;
    RadioButton male, female;
    Button submit, up_image;
    ArrayAdapter<String> arrayAdapter;
    String[] data;
    String id, password = "12345", pic = null;
    ProgressDialog progressDialog = null;
    Intent intent;
    Uri img;
    String imageData = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        img = data.getData();
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img);
            imageData = encodeToBase64(bitmap);

            SharedPreferences sharedPreferences = getSharedPreferences("image", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("image", " ");
            editor.commit();
            pic1.setText(img.getPath());
        } catch (Exception e) {
            Toast.makeText(SignupActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fname = (EditText) findViewById(R.id.SU_fname);
        mname = (EditText) findViewById(R.id.SU_mname);
        lname = (EditText) findViewById(R.id.SU_lname);
        ans1 = (EditText) findViewById(R.id.SU_ans1);
        ans2 = (EditText) findViewById(R.id.SU_ans2);
        add1 = (EditText) findViewById(R.id.SU_add1);
        add2 = (EditText) findViewById(R.id.SU_add2);
        area = (EditText) findViewById(R.id.SU_area);
        city = (EditText) findViewById(R.id.SU_city);
        pincode = (EditText) findViewById(R.id.SU_pincode);
        contact = (EditText) findViewById(R.id.SU_contact);
        pic1 = (EditText) findViewById(R.id.SU_pic);
        up_image = (Button) findViewById(R.id.SU_bpic);
        email = (EditText) findViewById(R.id.SU_email);

        up_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        state = (Spinner) findViewById(R.id.SU_state);
        sq1 = (Spinner) findViewById(R.id.SU_sq1);
        sq2 = (Spinner) findViewById(R.id.SU_sq2);

        male = (RadioButton) findViewById(R.id.SU_male);
        female = (RadioButton) findViewById(R.id.SU_female);
        submit = (Button) findViewById(R.id.SU_submit);

        int i;
        data = new String[12];

        data = new String[]{"Gujarat", "Maharstra", "RAjsthan", "Hariyana", "Madya Pradesh", "uttar Prdesh", "karnataka", "panjab", "Delhi"};
        arrayAdapter = new ArrayAdapter<String>(SignupActivity2.this, R.layout.support_simple_spinner_dropdown_item, data);
        state.setAdapter(arrayAdapter);

        data = new String[]{"what is your favorite food ?", "what is your favorite game ?", "what is your favorite actor ?",
                "what is your hobby ?", "what is your nick name", "what is your favorite movie ?@"};
        arrayAdapter = new ArrayAdapter<String>(SignupActivity2.this, R.layout.support_simple_spinner_dropdown_item, data);
        sq1.setAdapter(arrayAdapter);
        sq2.setAdapter(arrayAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fname.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity2.this, "Please Enter First name", Toast.LENGTH_SHORT).show();
                    fname.setText(null);
                    fname.requestFocus();
                    return;
                }

                if (mname.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity2.this, "Please Enter Middle name", Toast.LENGTH_SHORT).show();
                    mname.setText(null);
                    mname.requestFocus();
                    return;
                }

                if (lname.getText().toString().trim().isEmpty()) {

                    Toast.makeText(SignupActivity2.this, "Please Enter Last name", Toast.LENGTH_SHORT).show();
                    lname.setText(null);
                    lname.requestFocus();
                    return;
                }

                if (add1.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity2.this, "Please Enter Address line 1", Toast.LENGTH_SHORT).show();
                    add1.setText(null);
                    add1.requestFocus();
                    return;
                }

                if (add2.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity2.this, "Please Enter Address line 2", Toast.LENGTH_SHORT).show();
                    add2.setText(null);
                    add2.requestFocus();
                    return;
                }

                if (area.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity2.this, "Please Enter Area NAme", Toast.LENGTH_SHORT).show();
                    area.setText(null);
                    area.requestFocus();
                    return;
                }

                if (city.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity2.this, "Please Enter City NAme", Toast.LENGTH_SHORT).show();
                    city.setText(null);
                    city.requestFocus();
                    return;
                }

                if (pincode.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity2.this, "Please Enter pincode", Toast.LENGTH_SHORT).show();
                    pincode.setText(null);
                    pincode.requestFocus();
                    return;
                }

                if (contact.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity2.this, "Please Enter contact number", Toast.LENGTH_SHORT).show();
                    contact.setText(null);
                    contact.requestFocus();
                    return;
                }


                if (sq1.getSelectedItem() == null) {
                    Toast.makeText(SignupActivity2.this, "Please select any one Security Question", Toast.LENGTH_SHORT).show();
                    sq1.requestFocus();
                    return;
                }

                if (sq2.getSelectedItem() == null) {
                    Toast.makeText(SignupActivity2.this, "Please select any one Security Question", Toast.LENGTH_SHORT).show();
                    sq2.requestFocus();
                    return;
                }

                if (state.getSelectedItem() == null) {
                    Toast.makeText(SignupActivity2.this, "Please select any state name", Toast.LENGTH_SHORT).show();
                    state.requestFocus();
                    return;
                }

                if (email.getText().toString().toString().length() < 10 ||
                        !email.getText().toString().endsWith(".com")) {
                    Toast.makeText(SignupActivity2.this, "Enter valid email address", Toast.LENGTH_SHORT).show();
                    email.setText(null);
                    email.requestFocus();
                    return;
                }


                StringEntity stringEntity = null;
                JSONObject jsonObject;

                SharedPreferences sharedPreferences = getSharedPreferences("newLogin", Context.MODE_PRIVATE);
                id = sharedPreferences.getString("id", "");
                password = sharedPreferences.getString("password", "");

                ////panding


                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("login_id", Integer.valueOf(id));
                    jsonObject.put("password", password);
                    jsonObject.put("fname", fname.getText().toString().trim());
                    jsonObject.put("mname", mname.getText().toString().trim());
                    jsonObject.put("lname", lname.getText().toString().trim());
                    jsonObject.put("address_line1", add1.getText().toString().trim());
                    jsonObject.put("address_line2", add2.getText().toString().trim());
                    jsonObject.put("area_name", area.getText().toString().trim());
                    jsonObject.put("city_name", city.getText().toString().trim());
                    jsonObject.put("state_name", state.getSelectedItem().toString().trim());
                    jsonObject.put("pincode", pincode.getText().toString().trim());
                    jsonObject.put("mail", email.getText().toString().trim());
                    jsonObject.put("contact_no", contact.getText().toString().trim());
                    jsonObject.put("security_q1", sq1.getSelectedItem().toString().trim());
                    jsonObject.put("security_q2", sq2.getSelectedItem().toString().trim());
                    jsonObject.put("ans1", ans1.getText().toString().trim());
                    jsonObject.put("ans2", ans2.getText().toString().trim());
                    jsonObject.put("account_state", "active");
                    jsonObject.put("user_type", "customer");
                    jsonObject.put("gen", (male.isChecked() ? "Male" : "Female"));
                    jsonObject.put("pic", imageData);
                    stringEntity = new StringEntity(jsonObject.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("Accept", "application/json");

                client.post(SignupActivity2.this, getResources().getString(R.string.web_url) + "new_login", stringEntity, "application/json", new JsonHttpResponseHandler() {

                    ProgressDialog progressDialog;

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        String temp;
                        try {
                            temp = response.getString("d");
                            if (temp.equalsIgnoreCase("Success")) {
                                Toast.makeText(SignupActivity2.this, temp, Toast.LENGTH_SHORT).show();
                                clearUI();

                                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("login_id", id);
                                editor.putString("password", password);
                                editor.putString("login_state", "login");
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivityForResult(intent, 0);

                            } else {
                                Toast.makeText(SignupActivity2.this, temp, Toast.LENGTH_SHORT).show();
                                clearUI();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(SignupActivity2.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        progressDialog = ProgressDialog.show(SignupActivity2.this, "Creating", "Please wait");
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

    public void getStringImage(final Bitmap bmp) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                pic = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            }
        }).start();
    }

    private void clearUI() {
        fname.setText(null);
        mname.setText(null);
        lname.setText(null);
        add1.setText(null);
        add2.setText(null);
        ans1.setText(null);
        ans2.setText(null);
        email.setText(null);
        area.setText(null);
        city.setText(null);
        contact.setText(null);
        pic1.setText(null);
        state.setSelection(1);
    }

    private String encodeToBase64(Bitmap bitmap_image) {
        Bitmap bitmapx = bitmap_image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapx.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    private Bitmap decodeBase64(String path) {
        byte[] decodeByte = Base64.decode(path, 0);
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }

}
