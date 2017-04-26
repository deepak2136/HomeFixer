package com.homefixer.homefixer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutUs extends AppCompatActivity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  mWebview = new WebView(this);
      //  mWebview.loadUrl("https://android-arsenal.com/details/1/3062");
      //  setContentView(mWebview);
    }
}
