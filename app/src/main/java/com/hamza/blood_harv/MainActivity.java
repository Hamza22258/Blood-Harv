package com.hamza.blood_harv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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
    RecyclerView rv;
    HomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Profile");
        rv=findViewById(R.id.rv);
        ls=new ArrayList<>();
//        ls.add(new Profile("as","as","as","as","as","as","as","as"));
        adapter=new HomeAdapter(ls,MainActivity.this);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(adapter);
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