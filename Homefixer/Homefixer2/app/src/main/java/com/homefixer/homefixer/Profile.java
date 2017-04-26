package com.homefixer.homefixer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Profile extends AppCompatActivity {

    private int login_id;
    Intent intent;
    EditText name, email, contect, image;
    Button img, save, change;
    Class_Login data = null;
    String add1, add2, city, state, area, pincode, imageData, password;
    ImageView imageView;
    Uri img1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        img1 = data.getData();
        Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), img1);
            imageData = encodeToBase64(bitmap);

            SharedPreferences sharedPreferences = getSharedPreferences("image", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("image", " ");
            editor.commit();
            image.setText(img1.getPath());
        } catch (Exception e) {
            Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        login_id = Integer.valueOf(sharedPreferences.getString("login_id", "000").trim());
        password = sharedPreferences.getString("password", " ");
        Toast.makeText(this, login_id + "", Toast.LENGTH_LONG).show();

        name = (EditText) findViewById(R.id.profilename);
        email = (EditText) findViewById(R.id.profileemail);
        contect = (EditText) findViewById(R.id.profilecontact);
        imageView = (ImageView) findViewById(R.id.profile_image);
        image = (EditText) findViewById(R.id.profilepic);
        img = (Button) findViewById(R.id.profilepic2);
        save = (Button) findViewById(R.id.profilesave);
        change = (Button) findViewById(R.id.changePassword);
        loadData();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profile.this, "Clicked", Toast.LENGTH_LONG).show();
                saveData();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, ChangePassword.class);
                intent.putExtra("login_id", login_id + "");
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

    }

    private void saveData() {

        StringEntity stringEntity = null;
        final JSONObject jsonObject = new JSONObject();
        try {
            String[] name1 = name.getText().toString().trim().split(" ");
            jsonObject.put("login_id", login_id);
            jsonObject.put("fname", name1[1]);
            jsonObject.put("mname", data.getMname());
            jsonObject.put("lname", name1[0]);
            jsonObject.put("address_line1", data.getAddress_line1());
            jsonObject.put("address_line2", data.getAddress_line2());
            jsonObject.put("area_name", data.getArea_name());
            jsonObject.put("city_name", data.getCity_name());
            jsonObject.put("state_name", data.getState_name());
            jsonObject.put("pincode", Integer.valueOf(data.getPincode()));
            jsonObject.put("mail", email.getText().toString().trim());
            jsonObject.put("contact_no", contect.getText().toString().trim());
            jsonObject.put("account_state", data.getAccount_state());
            jsonObject.put("user_type", data.getUser_type());
            jsonObject.put("gen", data.getGen());

            if (image.getText().toString().trim().isEmpty() || image.getText().toString().length() < 10)
                imageData = "empty";
            jsonObject.put("pic", imageData);
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(Profile.this, getResources().getString(R.string.web_url) + "update_user", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String str = response.getString("d");
                    if (str.equalsIgnoreCase("Record does not exists")) {
                        Toast.makeText(Profile.this, "Record does not exists", Toast.LENGTH_LONG).show();
                    } else if (str.equalsIgnoreCase("Updated Successfully")) {
                        Toast.makeText(Profile.this, "Updated Successfully", Toast.LENGTH_LONG).show();
                        loadData();
                    } else {
                        Toast.makeText(Profile.this, "Can't Updated Successfully", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Profile.this, "Check code in profile save data method", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Profile.this, "Updating", "Please Wait");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

    }

    private void loadData() {

        StringEntity stringEntity = null;
        final JSONObject jsonObject = new JSONObject();
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

        client.post(Profile.this, getResources().getString(R.string.web_url) + "get_login_details", stringEntity, "application/json", new JsonHttpResponseHandler() {

            ProgressDialog progressDialog;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONObject jsonObject1 = response.getJSONObject("d");
                    data = new Class_Login();
                    data.setAccount_state(jsonObject1.getString("account_state"));
                    data.setAddress_line1(jsonObject1.getString("address_line1"));
                    add1 = data.getAddress_line1();
                    data.setAddress_line2(jsonObject1.getString("address_line2"));
                    add2 = data.getAddress_line2();
                    data.setArea_name(jsonObject1.getString("area_name"));
                    area = data.getArea_name();
                    data.setCity_name(jsonObject1.getString("city_name"));
                    city = data.getCity_name();
                    data.setContact_no(jsonObject1.getString("contact_no"));
                    data.setFname(jsonObject1.getString("fname"));
                    data.setMname(jsonObject1.getString("mname"));
                    data.setLname(jsonObject1.getString("lname"));
                    data.setMail(jsonObject1.getString("mail"));
                    data.setPic(jsonObject1.getString("pic"));
                    Picasso.with(Profile.this).load(data.getPic()).into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                        @Override
                        public void onError() {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    });
                    data.setUser_type(jsonObject1.getString("user_type"));
                    data.setState_name(jsonObject1.getString("state_name"));
                    state = data.getState_name();
                    data.setGen(jsonObject1.getString("gen"));
                    data.setLogin_id(jsonObject1.getInt("login_id"));
                    data.setPincode(jsonObject1.getInt("pincode"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Profile.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                if (data == null || data.getArea_name().trim().length() < 2)
                    Toast.makeText(Profile.this, "Error Encounter check code please", Toast.LENGTH_LONG).show();
                else {
                    name.setText(data.getMname() + " " + data.getFname());
                    email.setText(data.getMail());
                    contect.setText(data.getContact_no());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(Profile.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog = ProgressDialog.show(Profile.this, "Loading", "Please Wait");
            }
        });

    }

    private String encodeToBase64(Bitmap bitmap_image) {
        Bitmap bitmapx = bitmap_image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapx.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

}
