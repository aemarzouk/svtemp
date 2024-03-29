package com.google.android.gms.samples.vision.face.multitracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignInPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_page);

//        Button btn_button =(Button) findViewById(R.id.order);
//        btn_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(com.google.android.gms.samples.vision.face.multitracker.SignInPage.this);
//                View mView = getLayoutInflater().inflate(R.layout.login_dialog, null);

                final EditText mEmail = (EditText) findViewById(R.id.etEmail);
                final EditText mPassword = (EditText) findViewById(R.id.etpassword);
                Button mLogin = (Button) findViewById(R.id.btn_login);
                Button mSignup = (Button) findViewById(R.id.btn_signUp);
//                mSignup.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(getApplicationContext(), signUp.class));
//                   }
//               });
//
//
//
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEmail.getText().toString().isEmpty() ||mPassword.getText().toString().isEmpty() ){
                    Toast.makeText(getApplication(),"Please Enter Username and Password", Toast.LENGTH_LONG).show();
                }
                else {
                    Login(mEmail.getText().toString(), mPassword.getText().toString());
                }
            }


        });

                //show dialog
//                dialog.setView(mView);
//                AlertDialog Dialog = dialog.create();
//                Dialog.show();
            }

            //condition of login
            public void Login(String name, String password) {
                    String url= "https://resttest--59196.eu-gb.mybluemix.net/RestTest/jaxrs/SignIn" ;
                    new SignIn_check().execute(url , name , password);
            }

    protected class SignIn_check extends AsyncTask<String, Void, String> {
        String error , out="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            BufferedReader br = null;
            String username , password ;
            URL url;
            try {
                url = new URL(params[0]);
                username = new String(params[1]) ;
                password = new String(params[2]) ;
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
                ////change the next line according to the object you want to post
                String jsonObj ="{\"_id\":\""+username+"\",\"password\":\""+password+"\" }"  ;
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
           if(result.contains("1")) {
               // open new activity
//               startActivity(new Intent(getApplicationContext(), Main3Activity.class));
           }
           else if (result.contains("2")){
               Toast.makeText(getApplication(),"Wrong Username", Toast.LENGTH_LONG).show();
           }
           else{
               Toast.makeText(getApplication(), "Wrong password", Toast.LENGTH_LONG).show();
           }
        }
    }

}






