package com.hamza.blood_harv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorProfile extends AppCompatActivity {
    CircleImageView profileImage;
    TextView username, accountType, bloodtype, location, age, gender,active;
    Profile profile;
    ImageView activeChange, backToHome;

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
        active=findViewById(R.id.active);
        activeChange=findViewById(R.id.activeChange);
        backToHome=findViewById(R.id.openhome);
        String ip=getResources().getString(R.string.IP);

        activeChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val;
                if (active.getText().toString().equals("false")){
                    val="true";
                }
                else{
                    val="false";
                }
                FirebaseDatabase database=FirebaseDatabase.getInstance();
                String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference reference=database.getReference("Profile/"+id).child("active");
                reference.setValue(val);
//                reference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        DatabaseReference new_reference=snapshot.getRef().child("active");
//                        if (snapshot.getValue(Profile.class).getActive().equals("false")){
//                            new_reference.setValue("true");
//                        }
//                        else{
//                            new_reference.setValue("false");
//                        }
//
////                Toast.makeText(DonorProfile.this, profile.getName()+"", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

            }
        });
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DonorProfile.this,MainActivity.class);
                startActivity(intent);
            }
        });
        
        String url="http://"+ip+"/Account/getAccount.php";
        StringRequest request=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object=new JSONObject(response);
                            if(object.getInt("Success")==1){

                                String image=object.getString("image");

                                Picasso.get().load("http://"+ip+"/Account/"+image).fit().centerCrop().into(profileImage);
                            }

                        } catch ( JSONException jsonException) {
                            jsonException.printStackTrace();
                        }
                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DonorProfile.this, error+"", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            protected Map<String,String> getParams(){
                Map<String,String> data=new HashMap<String,String>();
                String myid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                data.put("uid",myid);
                return data;
            }
        };
        Volley.newRequestQueue(DonorProfile.this).add(request);

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference=database.getReference("Profile/"+id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile=snapshot.getValue(Profile.class);
                //Picasso.get().load(profile.getDp()).into(profileImage);
                username.setText(profile.getName());
                accountType.setText(profile.getAccountType());
                bloodtype.setText(profile.getBloodType());
                location.setText(profile.getLocation());
                age.setText(profile.getAge());
                gender.setText(profile.getGender());
                active.setText(profile.getActive());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}