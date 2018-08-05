package com.example.mallo.salon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView  tvsalon_id,tvsalon_name,tvdetail,tvlatitude,tvlongitude,tvuser_id,tvname,tvphone,tvemail;
    Button btnsalon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btnsalon = findViewById(R.id.btnsalon);

        tvsalon_id = findViewById(R.id.tvsalon_id);
        tvsalon_name = findViewById(R.id.tvsalon_name);
        tvdetail = findViewById(R.id.tvdetail);
        tvlatitude = findViewById(R.id.tvlatitude);
        tvlongitude = findViewById(R.id.tvlongitude);
        tvuser_id = findViewById(R.id.tvuser_id);
        tvname = findViewById(R.id.tvname);
        tvphone = findViewById(R.id.tvphone);
        tvemail = findViewById(R.id.tvemail);


        String salon_id = getIntent().getExtras().getString("A");
        tvsalon_id.setText(salon_id);
        String salon_name = getIntent().getExtras().getString("B");
        tvsalon_name.setText(salon_name);
        String detail = getIntent().getExtras().getString("C");
        tvdetail.setText(detail);
        String latitude = getIntent().getExtras().getString("D");
        tvlatitude.setText(latitude);
        String longitude = getIntent().getExtras().getString("E");
        tvlongitude.setText(longitude);
        String user_id = getIntent().getExtras().getString("F");
        tvuser_id.setText(user_id);
        String name = getIntent().getExtras().getString("G");
        tvname.setText(name);
        String phone = getIntent().getExtras().getString("S");
        tvphone.setText(phone);
        String email = getIntent().getExtras().getString("I");
        tvemail.setText(email);

        btnsalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = getIntent().getExtras().getString("A");

                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                intent.putExtra("A", id);
                startActivity(intent);
            }
        });

    }
}
