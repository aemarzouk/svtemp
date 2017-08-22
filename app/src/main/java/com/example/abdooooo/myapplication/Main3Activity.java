//package com.example.abdooooo.myapplication;
//
//import android.content.Intent;
//import android.graphics.Point;
//import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.google.android.gms.common.api.CommonStatusCodes;
//import com.google.android.gms.vision.barcode.Barcode;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//
//
//public class Main3Activity extends AppCompatActivity {
//
//    private static final String LOG_TAG = SignInPage.class.getSimpleName();
//    private static final int BARCODE_READER_REQUEST_CODE = 1;
//
//    private TextView mResultTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dashboard);
//
//        mResultTextView = (TextView) findViewById(R.id.result_textview);
//
//        Button scanBarcodeButton = (Button) findViewById(R.id.scan_barcode_button);
//        scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
//                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        String result;
//        if (requestCode == BARCODE_READER_REQUEST_CODE) {
//            if (resultCode == CommonStatusCodes.SUCCESS) {
//                if (data != null) {
//                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
//                    Point[] p = barcode.cornerPoints;
//                    mResultTextView.setText(barcode.displayValue);
//                    result = mResultTextView.getText().toString();
//                    String url = "https://resttest--59196.eu-gb.mybluemix.net/RestTest/jaxrs/GetItemsInfo" ;
//                    new RestOperation().execute(url , result);
//
//                } else mResultTextView.setText(R.string.no_barcode_captured);
//            } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
//                    CommonStatusCodes.getStatusCodeString(resultCode)));
//        } else super.onActivityResult(requestCode, resultCode, data);
//
//    }
//    private class RestOperation extends AsyncTask<String, Void, String> {
//        String error;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            BufferedReader br = null;
//            URL url;
//            try {
//                url = new URL(params[0]);
//                String  result = new String(params[1]);
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Accept", "application/json");
//                connection.setRequestMethod("POST");
//                ////change the next line according to the object you want to post
//                String jsonObj = "{\"item_id\":\""+result+"\"}";
//                connection.setDoOutput(true);
//                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
//                wr.write(jsonObj);
//                wr.flush();
//                wr.close();
//
//                int httpCode = connection.getResponseCode();
//                if (httpCode == 200 || httpCode == 201) {
//                    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                } else {
//                    br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//                }
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                    sb.append(System.getProperty("line.separator"));
//                }
//
//                String content = sb.toString();
//                return content;
//
//            } catch (MalformedURLException e) {
//                error = e.getMessage();
//                e.printStackTrace();
//            } catch (IOException e) {
//                error = e.getMessage();
//                e.printStackTrace();
//            } finally {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            try {
//                //JSONPObject jo = (JSONPObject) new JSONTokener(result).nextValue();
//                JSONObject jo = new JSONObject(result);
//                String name = jo.getString("name") ;
//                String price = jo.getString("price");
//                Toast.makeText(getApplicationContext() , name+" "+ price , Toast.LENGTH_LONG).show();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
