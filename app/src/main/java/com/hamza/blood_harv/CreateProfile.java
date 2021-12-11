package com.hamza.blood_harv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CreateProfile extends AppCompatActivity {
    TextView name, bloodType, accountType, location, age, gender;
    Button addDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        name=findViewById(R.id.name);
        bloodType=findViewById(R.id.bloodType);
        accountType=findViewById(R.id.accountType);
        location=findViewById(R.id.location);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        addDetails=findViewById(R.id.addDetails);
        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}