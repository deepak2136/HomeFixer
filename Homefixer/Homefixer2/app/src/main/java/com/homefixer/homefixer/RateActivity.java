package com.homefixer.homefixer;

/**
 * Created by chudasama  kishan on 19-03-2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {
    GridView grid;
    String[] web = {
            "    Beauty   ",
            "  Appliances ",
            "   Cleaning  ",
            "    Drivers  ",
            "   Plumbing  ",
            " PestControl ",
            "  Automobile ",
            " Elecrical ",
            "Mobile Repair",
            "Computer Repair"
    };
    int[] imageId = {
            R.mipmap.ic_beauty,
            R.mipmap.ic_elec,
            R.mipmap.ic_clean,
            R.mipmap.ic_driver,
            R.mipmap.ic_plum,
            R.mipmap.ic_pest,
            R.mipmap.ic_auto,
            R.mipmap.ic_elec,
            R.mipmap.ic_mobile,
            R.mipmap.ic_laptop
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        CustomGrid adapter = new CustomGrid(RateActivity.this, web, imageId);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(RateActivity.this, "You Clicked at " + web[+position], Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RateActivity.this, RateCard.class);
                intent.putExtra("service_id", 111001 + position);
                startActivity(intent);

            }
        });

    }

}