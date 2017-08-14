package com.example.abdooooo.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class History extends AppCompatActivity {
    private Activity activityReference;
    private ProgressBar loadingPb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //progress bar
        loadingPb = (ProgressBar) findViewById(R.id.loadingPb);
        //----

        String url = "https://swiftvending.eu-gb.mybluemix.net/RestTest/jaxrs/GetItems";
        Intent i = getIntent();
        String user_id = i.getStringExtra("user_id");
        String username = user_id;
        activityReference = this;
        new getTrans().execute(url , username);
        //init();


    }

    protected class getTrans extends AsyncTask<String, Void, String> {
        String error , out="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingPb.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {
            //Intent i = getIntent();
            //String item_id = i.getStringExtra("item_id");
            BufferedReader br = null;
            String username;
            URL url;
            try {
                url = new URL(params[0]);
                username = params[1] ;
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
                ////change the next line according to the object you want to post
                String jsonObj ="{\"_id\":\""+username+"\"}"  ;
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
                connection.disconnect();;
                out = content;
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
        public void onPostExecute(String result) {
            try {
                loadingPb.setVisibility(View.INVISIBLE);

                JSONObject j = (JSONObject) new JSONTokener(result).nextValue();
                JSONArray trans = j.getJSONArray("Transactions");
                TableLayout stk = (TableLayout) findViewById(R.id.table_main);
                stk.setColumnStretchable(0,false);
                stk.setColumnStretchable(1,true);
                stk.setColumnStretchable(2,false);
                TableRow tbrow0 = new TableRow(activityReference);
                TextView tv0 = new TextView(activityReference);
                tv0.setText("Item Name");
                tv0.setTextColor(Color.BLACK);
                tv0.setTextSize(20);
               // tv0.setGravity(Gravity.LEFT);
                tbrow0.addView(tv0);
                TextView tv1 = new TextView(activityReference);
                tv1.setText("Date");
                tv1.setTextColor(Color.BLACK);
                tv1.setGravity(Gravity.CENTER);
                tv1.setTextSize(20);
                tbrow0.addView(tv1);
                TextView tv2 = new TextView(activityReference);
                tv2.setText("Time");
                tv2.setTextColor(Color.BLACK);
                tv2.setGravity(Gravity.RIGHT);
                tv2.setTextSize(20);
                tbrow0.addView(tv2);
                stk.addView(tbrow0);
                for (int i = 0; i < trans.length(); i++) {
                    TableRow tbrow = new TableRow(activityReference);
                    TextView t1v = new TextView(activityReference);
                    t1v.setText(trans.getJSONObject(i).getString("item_name"));
                    t1v.setTextColor(Color.BLACK);
                    t1v.setTextSize(20);
                    //t1v.setGravity(Gravity.LEFT);
                    tbrow.addView(t1v);
                    TextView t2v = new TextView(activityReference);
                    t2v.setText("    "+trans.getJSONObject(i).getString("trans_date"));
                    t2v.setTextColor(Color.BLACK);
                    t2v.setGravity(Gravity.CENTER);
                    t2v.setTextSize(20);
                    tbrow.addView(t2v);
                    TextView t3v = new TextView(activityReference);
                    t3v.setText("         " + trans.getJSONObject(i).getString("trans_time"));
                    t3v.setTextColor(Color.BLACK);
                    t3v.setGravity(Gravity.RIGHT);
                    t3v.setTextSize(20);
                    tbrow.addView(t3v);
                    stk.addView(tbrow);

                }

                stk.isStretchAllColumns() ;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
