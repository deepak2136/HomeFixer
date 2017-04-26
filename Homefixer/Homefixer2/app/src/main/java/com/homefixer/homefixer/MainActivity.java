package com.homefixer.homefixer;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayoutManager lLayout;
    ImageView DRWR_pic;
    RecyclerView recyclerView;
    private final int SID = 111001;
    ImageView pic;
    TextView user_name, mail;
    Class_Login data;
    DrawerLayout drawerLayout;
    // String imgData;

    private String password, id;

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String temp = preferences.getString("login_state", "logout");
        if (temp.equalsIgnoreCase("logout")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void loadData() {

        StringEntity stringEntity = null;
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", Integer.valueOf(id));
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Accept", "application/json");

        client.post(MainActivity.this, getResources().getString(R.string.web_url) + "get_login_details", stringEntity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    JSONObject jsonObject1 = response.getJSONObject("d");
                    data = new Class_Login();
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
                    data.setUser_type(jsonObject1.getString("user_type"));
                    data.setState_name(jsonObject1.getString("state_name"));
                    data.setGen(jsonObject1.getString("gen"));
                    data.setLogin_id(jsonObject1.getInt("login_id"));
                    data.setPincode(jsonObject1.getInt("pincode"));
                    //loadImage();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                //Bitmap bitmap = decodeBase64(data.getPic());
                //DRWR_pic.setImageBitmap(bitmap);

                if (data == null || data.getArea_name().trim().length() < 2)
                    Toast.makeText(MainActivity.this, "Error Encounter check code please  ", Toast.LENGTH_LONG).show();
                else {
                    //   user_name.setText(data.getMname() + " " + data.getFname());
                    //  mail.setText(data.getMail());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if( keyCode == event.KEYCODE_BACK )
        {

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        pic = (ImageView) findViewById(R.id.DRW_pic);
        user_name = (TextView) findViewById(R.id.DRW_name);
        mail = (TextView) findViewById(R.id.DRW_contact);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("login_id", null);
        password = sharedPreferences.getString("password", "");

        loadData();
        if (id == null || id.length() < 4) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_id", "");
            editor.putString("password", "");
            editor.putString("login_state", "logout");
            editor.commit();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float last = 0;
                boolean opening = slideOffset>last;
                boolean closing = slideOffset<last;

                if(opening) {
                    Picasso.with(drawerLayout.getContext()).load(data.getPic()).into(pic);
                } else if(closing) {
                    Log.i("Drawer", "closing");
                } else {
                    Log.i("Drawer","doing nothing");
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Picasso.with(drawerLayout.getContext()).load(data.getPic()).into(pic);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });



        // Intent intent = new Intent(MainActivity.this, ExpandbleList.class);
        // intent.putExtra("SID", SID + 0);
        // startActivity(intent);

    /*    pic = (ImageView) findViewById(R.id.DRW_pic);
        SharedPreferences sharedPreferences = getSharedPreferences("image", Context.MODE_PRIVATE);
        String temp = sharedPreferences.getString("image", "");
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(temp)));
            pic.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        List<ItemObject> rowListItem = getAllItemList();
        lLayout = new LinearLayoutManager(MainActivity.this);

        RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.putExtra("SID", SID + RecyclerViewHolders.pos);
                startActivity(intent);

              //  Toast.makeText(MainActivity.this, SID + RecyclerViewHolders.pos + "", Toast.LENGTH_LONG).show();
              //  Toast.makeText(MainActivity.this, SID + "dsdsdfdfdf" + "", Toast.LENGTH_LONG).show();
            }
        });

//        ImageView inImageView = (ImageView) findViewById(R.id.DRW_pic);

    }


    public void loadNext(int id) {
        Intent intent = new Intent(MainActivity.this, ExpandbleList.class);
        intent.putExtra("SID", id + 111001);
        startActivity(intent);
        // Toast.makeText(MainActivity.this, "" + (111001 + id), Toast.LENGTH_LONG).show();
    }

    private List<ItemObject> getAllItemList() {

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("", R.drawable.card4));
        allItems.add(new ItemObject("", R.drawable.card12));
        allItems.add(new ItemObject("", R.drawable.card32));
        allItems.add(new ItemObject("", R.drawable.card9));
        allItems.add(new ItemObject("", R.drawable.card6));
        allItems.add(new ItemObject("", R.drawable.card11));
        allItems.add(new ItemObject("", R.drawable.card7));
        allItems.add(new ItemObject("", R.drawable.card13));
        allItems.add(new ItemObject("", R.drawable.card8));
        allItems.add(new ItemObject("", R.drawable.card5));
        return allItems;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_order) {
            Intent intent1 = new Intent(MainActivity.this, Panding_Order.class);
            startActivity(intent1);

        } else if (id == R.id.nav_rate) {
            intent = new Intent(MainActivity.this, RateActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_AboutUs) {
            Intent intent1 = new Intent(MainActivity.this, AboutUs.class);
            startActivity(intent1);
        } else if (id == R.id.nav_offer) {
            Intent intent1 = new Intent(MainActivity.this, Offers.class);
            startActivity(intent1);

        } else if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login_state", "logout");
            editor.putString("login_id", "");
            editor.putString("password", "");
            editor.commit();

            Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent1);
        } else if (id == R.id.nav_profile) {
            Intent intent1 = new Intent(MainActivity.this, Profile.class);
            intent1.putExtra("login_id", id);
            startActivity(intent1);
        } else if (id == R.id.nav_upcom) {
            Intent intent1 = new Intent(MainActivity.this, Pandin_Appointment.class);
            startActivity(intent1);
        } else if (id == R.id.nav_feedback) {
            Intent intent1 = new Intent(MainActivity.this, Feedback.class);
            startActivity(intent1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

 /*   private void loadImage() {
       // int image_id = Integer.parseInt(data.getPic().trim());
        try {

            StringEntity stringEntity = null;
           // Toast.makeText(MainActivity.this, image_id + "", Toast.LENGTH_LONG).show();
            final JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id",12121 );
                stringEntity = new StringEntity(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("Accept", "application/json");

            client.post(MainActivity.this, getResources().getString(R.string.web_url) + "getImg", stringEntity, "application/json", new JsonHttpResponseHandler() {

                ProgressDialog progressDialog;

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                       // imgData = response.getString("d");
                        Picasso.with(MainActivity.this).load("http://192.168.23.1:12312/pic/12121.jpg").into(DRWR_pic);
                       // Toast.makeText(MainActivity.this, imgData.length(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   // DRWR_pic.setImageBitmap(decodeBase64(imgData));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    progressDialog = ProgressDialog.show(MainActivity.this, "Load Profile", "Please Wait");

                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage() + " catch end mthod", Toast.LENGTH_LONG).show();
        }
    }*/

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
