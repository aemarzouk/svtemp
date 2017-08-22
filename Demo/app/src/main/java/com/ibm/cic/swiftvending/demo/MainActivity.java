package com.ibm.cic.swiftvending.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
      private Button demo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demo = (Button) findViewById(R.id.lol);

        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hi youmna", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
    }
}
