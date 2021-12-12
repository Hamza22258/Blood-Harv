package com.hamza.blood_harv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorProfile extends AppCompatActivity {
    CircleImageView profileImage;
    TextView username, accountType, bloodtype, location, age, gender;
    Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);
        profileImage=findViewById(R.id.profileImage);
        username=findViewById(R.id.username);
        accountType=findViewById(R.id.accountType);
        bloodtype=findViewById(R.id.bloodtype_text);
        location=findViewById(R.id.location_text);
        age=findViewById(R.id.age_text);
        gender=findViewById(R.id.gender);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference=database.getReference("Profile/"+id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile=snapshot.getValue(Profile.class);
                Picasso.get().load(profile.getDp()).into(profileImage);
                username.setText(profile.getName());
                accountType.setText(profile.getAccountType());
                bloodtype.setText(profile.getBloodType());
                location.setText(profile.getLocation());
                age.setText(profile.getAge());
                gender.setText(profile.getGender());
//                Toast.makeText(DonorProfile.this, profile.getName()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Toast.makeText(DonorProfile.this, profile.getName()+"", Toast.LENGTH_SHORT).show();

    }
}