package com.example.abdooooo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class signUp extends AppCompatActivity {
    private Activity activityReference;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityReference=this;
        setContentView(R.layout.activity_sign_up);
        final Button button = (Button) findViewById(R.id.signup);
        final EditText mEmail = (EditText) findViewById(R.id.email);
        final EditText mPassword = (EditText) findViewById(R.id.pass);
        final EditText cPassword = (EditText) findViewById(R.id.confirmPass);
        final ImageView backbtn= (ImageView) findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInPage.class));
            }
        });

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!(mPassword.getText().toString().equals( cPassword.getText().toString()))) {
                        Toast.makeText(getApplication(), "Password Does not Match", Toast.LENGTH_LONG).show();

                    } else {
                        // Code here executes on main thread after user presses button
                        String restURL = "https://swiftvending.eu-gb.mybluemix.net/RestTest/jaxrs/SignUp";
                        new RestOperation().execute(restURL, mEmail.getText().toString(), mPassword.getText().toString());
                    }
                }
            });

    }

    private class RestOperation extends AsyncTask<String, Void, String> {
        String error;
       // TextView t = (TextView) findViewById(R.id.textView);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {


            BufferedReader br = null;

            URL url;
            try {
                url = new URL(params[0]);
                String username = new String(params[1]) ;
                String password = new String(params[2]) ;
                user_id = username;
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
                ////change the next line according to the object you want to post
                String jsonObj = "{\"_id\":\""+username+"\",\"password\":\""+password+"\"  }"  ;
                connection.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(jsonObj);
                wr.flush();
                wr.close();

                int httpCode = connection.getResponseCode();
                if (httpCode == 200 || httpCode == 201) {
                    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } else {
                    br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                }
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }

                String content = sb.toString();
                return content;

            } catch (MalformedURLException e) {
                error = e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                error = e.getMessage();
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.contains("1")) {
                final Intent i = new Intent (activityReference, MultiTrackerActivity.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
                //startActivity(new Intent(getApplicationContext(),MultiTrackerActivity.class));
            }
            else{
                Toast.makeText(getApplication(), "Email already exists", Toast.LENGTH_LONG).show();

            }
        }
    }

}
