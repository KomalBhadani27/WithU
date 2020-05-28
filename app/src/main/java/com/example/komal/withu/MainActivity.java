package com.example.komal.withu;

import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecycler;
    private GridLayoutManager mLayoutManager;
    private portalAdapter mAdapter;
    private List<portalDetails> data = new ArrayList<>();
    private portalDetails UserData;
    private String question,user;

    TextView userName,Degn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton sos = (FloatingActionButton) findViewById(R.id.sos);
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    Log.i("Sending SMS","");
                    Intent I =new Intent(Intent.ACTION_VIEW);

                    I.setData(Uri.parse("smsto:"));
                    I.setType("vnd.android-dir/mms-sms");
                    I.putExtra("address", new String ("8318807190"));
                    I.putExtra("sms_body","Emergency! Please help.");

                    try
                    {
                        startActivity(I);
                        finish();
                        Log.i("Sms Send","");
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(MainActivity.this,"Sms not send",Toast.LENGTH_LONG).show();
                    }
                }

        });

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView =  findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//
//        userName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.Nav_Name);
//        Degn = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_degn);
//
//        Log.e("In start", "doOnSuccess: " + userName.getText());

        String url = "https://newsapi.org/v2/everything?q=domestic%20violence&from=2019-03-28&to=2019-03-28&sortBy=popularity&apiKey={API_KEY}";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
        rQueue.add(request);



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

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.rules) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void doOnSuccess(String s) {
        try {

            JSONObject res = new JSONObject(s);
            JSONArray art = res.getJSONArray("articles");

            for(int i=0;i<art.length();i++){
                JSONObject article=art.getJSONObject(i);

                String author=article.getString("author");
                String title=article.getString("title");
                String desc=article.getString("description");
                String link=article.getString("url");

                UserData=new portalDetails(author,title,desc,link);
                data.add(UserData);

            }
            //     JSONObject res1 = new JSONObject(s);
            Log.e("res output", "doOnSuccess1: " + art);

            mRecycler=findViewById(R.id.recycle);

            mLayoutManager = new GridLayoutManager(this, 1);
            mRecycler.setLayoutManager(mLayoutManager);

            mAdapter = new portalAdapter(this, data);
            mRecycler.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Connect(View v){

    }
}
