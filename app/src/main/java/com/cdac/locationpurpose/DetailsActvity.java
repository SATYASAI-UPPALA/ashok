package com.cdac.locationpurpose;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;




public class DetailsActvity extends AppCompatActivity {
    TextView markertxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_actvity);
        markertxt=findViewById(R.id.marker);
        //we will get data from our maps activity
        String title=getIntent().getStringExtra("title");
        markertxt.setText(title);
        //add internet permission in manifest



    }
}