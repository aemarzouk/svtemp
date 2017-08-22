package com.example.abdooooo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ImageView image = (ImageView) findViewById(R.id.Startimage);
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String defaultValue2 = getResources().getString(R.string.sessionID);
        String sessionID = sharedPref.getString(getString(R.string.sessionID), defaultValue2);
        if (!sharedPref.contains("userID") || sessionID.equals("null")) {
            startActivity(new Intent(getApplicationContext(), SignInPage.class));
        } else {
            String defaultValue1 = getResources().getString(R.string.userID);
            String userID = sharedPref.getString(getString(R.string.userID), defaultValue1);
            Toast.makeText(getApplication(), "Welcome " + userID + ".", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), MultiTrackerActivity.class));
        }
    }

}
