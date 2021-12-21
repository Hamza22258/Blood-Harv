package com.hamza.blood_harv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewBankProfileActivity extends AppCompatActivity {
    List<BloodTypes> ls;
    CircleImageView profile_image;
    TextView username,location,account;
    Profile profile;
    RecyclerView rv;
    TypesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bank_profile);
    }
}