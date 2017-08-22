package com.ibm.cic.swiftvending.firstappdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private ImageView image ;
    private TextView text ;
    private EditText view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image1);
        text = (TextView)findViewById(R.id.textView);
        view = (EditText) findViewById(R.id.view1);
        String Dolars = view.getText().toString();
        text.setText(Dolars);

        image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "well", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,MainActivity2.class));

            }
        });
    }
}
