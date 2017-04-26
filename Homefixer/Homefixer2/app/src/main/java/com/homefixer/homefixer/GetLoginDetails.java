package com.homefixer.homefixer;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class GetLoginDetails {

    public static Class_Login getLoginDetails(int login_id, final Context context) {
        Class_Login data = null;

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

        client.post(context, context.getResources().getString(R.string.web_url) + "get_login_details", stringEntity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONObject jsonObject1 = response.getJSONObject("d");
                    Class_Login data = new Class_Login();
                    data.setAccount_state(jsonObject1.getString("account_state"));
                    data.setAddress_line1(jsonObject1.getString("address_line1"));
                    data.setAddress_line2(jsonObject1.getString("address_line2"));
                    data.setArea_name(jsonObject1.getString("area_name"));
                    data.setCity_name(jsonObject1.getString("city_name"));
                    data.setContact_no(jsonObject1.getString("contact_no"));
                    data.setFname(jsonObject1.getString("fname"));
                    data.setMname(jsonObject1.getString("mname"));
                    data.setLname(jsonObject1.getString("lname"));
                    data.setMail(jsonObject1.getString("mail"));
                    data.setPic(jsonObject1.getString("pic"));
                    data.setUser_type(jsonObject1.getString("user_type"));
                    data.setState_name(jsonObject1.getString("state_name"));
                    data.setGen(jsonObject1.getString("gen"));
                    data.setLogin_id(jsonObject1.getInt("login_id"));
                    data.setPincode(jsonObject1.getInt("pincode"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return data;
    }
}
