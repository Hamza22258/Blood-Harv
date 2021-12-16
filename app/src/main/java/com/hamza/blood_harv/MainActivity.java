package com.hamza.blood_harv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Profile> ls;
    ImageView imageView;
    RecyclerView rv;
    HomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Profile");
        rv=findViewById(R.id.rv);
        imageView=findViewById(R.id.openprofile);
        ls=new ArrayList<>();
//        ls.add(new Profile("as","as","as","as","as","as","as","as"));
        adapter=new HomeAdapter(ls,MainActivity.this);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference reference=database.getReference("Profile/"+id);
               ;
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String accounttype=snapshot.getValue(Profile.class).getAccountType();
                        if (accounttype.equals("Blood Bank")){
                            startActivity(new Intent(MainActivity.this, BankProfile.class));
                        }
                        else if (accounttype.equals("Donor")){
                            startActivity(new Intent(MainActivity.this, DonorProfile.class));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    ls.add(new Profile(data.child("name").getValue(String.class), data.child("bloodType").getValue(String.class),
                            data.child("accountType").getValue(String.class), data.child("location").getValue(String.class),
                            data.child("age").getValue(String.class), data.child("gender").getValue(String.class),
                            data.child("dp").getValue(String.class), data.child("active").getValue(String.class)));

                    adapter.notifyDataSetChanged();
//                    Toast.makeText(MainActivity.this, data.child("dp").getValue(String.class)+"", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}