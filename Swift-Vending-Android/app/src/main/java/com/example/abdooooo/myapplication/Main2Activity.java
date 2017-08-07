//package com.example.abdooooo.myapplication;
//
//import android.content.Intent;
//import android.graphics.Point;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.util.Log;
//import android.view.View;
//import android.support.design.widget.NavigationView;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.Button;
//import android.widget.ImageView;//
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.google.android.gms.common.api.CommonStatusCodes;
//import com.google.android.gms.vision.barcode.Barcode;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.List;
//
//import camera.BarcodeCaptureActivity;
//
//public class Main2Activity extends AppCompatActivity
//
//
//        implements NavigationView.OnNavigationItemSelectedListener
//
//    {
//
//
//
//
//
//
////   public void Onclick(View view){
////     TextView textView = (TextView) findViewById(R.id.tv_price);
////       textView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                String id ="" ;
////                String restURL = "https://resttest--59196.eu-gb.mybluemix.net/RestTest/jaxrs/Buy?"+id ;
////
////                new BuyItem().execute(restURL);
////            }
////
////        });
////    }
//
//
//
//
//
//
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        //rest backend
////        String restURL = "https://resttest--59196.eu-gb.mybluemix.net/RestTest/jaxrs/getItems";
////        new GetItems().execute(restURL);
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//    }
//
//
//        @Override
//        public void onBackPressed () {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//        @Override
//        public boolean onCreateOptionsMenu (Menu menu){
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main2, menu);
//        return true;
//    }
//
//        @Override
//        public boolean onOptionsItemSelected (MenuItem item){
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//        @SuppressWarnings("StatementWithEmptyBody")
//        @Override
//        public boolean onNavigationItemSelected (MenuItem item){
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//
//        if (id == R.id.nav_camera) {
//            startActivity(new Intent(getApplicationContext(), History.class));
//            // Handle the camera action
////        } else if (id == R.id.nav_gallery) {
////
////        } else if (id == R.id.nav_slideshow) {
//
////        } else if (id == R.id.nav_manage) {
//
////        } else if (id == R.id.nav_share) {
////
////        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//
//    }
//
