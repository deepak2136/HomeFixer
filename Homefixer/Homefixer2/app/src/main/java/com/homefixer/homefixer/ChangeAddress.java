package com.homefixer.homefixer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeAddress extends AppCompatActivity {

    private LinearLayoutManager lLayout;
    private EditText line1, line2, area, city, pincode;
    private Button save;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeaddress);
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        line1 = (EditText) findViewById(R.id.ADD_line1);
        line2 = (EditText) findViewById(R.id.ADD_line2);
        area = (EditText) findViewById(R.id.ADD_area);
        city = (EditText) findViewById(R.id.ADD_city);
        pincode = (EditText) findViewById(R.id.ADD_pincode);
        save = (Button) findViewById(R.id.BTN_add_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pincode.getText().toString().trim().length() < 6) {
                    Toast.makeText(ChangeAddress.this, "Enter proper Pincode first !", Toast.LENGTH_LONG).show();
                    pincode.requestFocus();
                    return;
                }

                if (line1.getText().toString().trim().length() < 6) {
                    Toast.makeText(ChangeAddress.this, "Enter proper Adress !", Toast.LENGTH_LONG).show();
                    line1.requestFocus();
                    return;
                }

                if (line2.getText().toString().trim().length() < 6) {
                    Toast.makeText(ChangeAddress.this, "Enter proper Adress !", Toast.LENGTH_LONG).show();
                    line2.requestFocus();
                    return;
                }

                if (area.getText().toString().trim().length() < 6) {
                    Toast.makeText(ChangeAddress.this, "Enter proper area name !", Toast.LENGTH_LONG).show();
                    area.requestFocus();
                    return;
                }

                if (city.getText().toString().trim().length() < 6) {
                    Toast.makeText(ChangeAddress.this, "Enter proper City name first !", Toast.LENGTH_LONG).show();
                    city.requestFocus();
                    return;
                }

                SharedPreferences sharedPreferences = getSharedPreferences("temp_address", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("line1", line1.getText().toString().trim());
                editor.putString("line2", line2.getText().toString().trim());
                editor.putString("area", area.getText().toString().trim());
                editor.putString("city", city.getText().toString().trim());
                editor.putString("pincode", pincode.getText().toString().trim());
                editor.commit();

                Intent intent = new Intent(ChangeAddress.this, AppoinmentActivity.class);
                clearData();
                startActivity(intent);
            }
        });
    }

    private void clearData() {
        line1.setText(null);
        line2.setText(null);
        area.setText(null);
        city.setText(null);
        pincode.setText(null);
    }
}