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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BankProfile extends AppCompatActivity {

    List<BloodTypes> ls;
    CircleImageView profile_image;
    TextView username,location,account;
    Profile profile;
    ImageView add, imageView;
    RecyclerView rv;
    TypesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_profile);
        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
        location=findViewById(R.id.location);
        account=findViewById(R.id.donor);
        imageView=findViewById(R.id.openhome);
        add=findViewById(R.id.add);
        rv=findViewById(R.id.listBlood);
        ls=new ArrayList<>();
        adapter=new TypesAdapter(ls,BankProfile.this);
        rv.setLayoutManager(new LinearLayoutManager(BankProfile.this));
        rv.setAdapter(adapter);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BankProfile.this, MainActivity.class));
            }
        });
        String ip=getResources().getString(R.string.IP);
        Toast.makeText(BankProfile.this, "IP: "+ip, Toast.LENGTH_SHORT).show();
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

                                Picasso.get().load("http://"+ip+"/Account/"+image).fit().centerCrop().into(profile_image);
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
                        Toast.makeText(BankProfile.this, error+"", Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(BankProfile.this).add(request);





        FirebaseDatabase database=FirebaseDatabase.getInstance();
        String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference=database.getReference("Profile/"+id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                profile=snapshot.getValue(Profile.class);
//                Picasso.get().load(profile.getDp()).into(profile_image);
                username.setText(profile.getName());
                account.setText("Blood Bank");
                location.setText(profile.getLocation());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BankProfile.this, CreateBankBloodActivity.class));
            }
        });
        FirebaseDatabase new_database=FirebaseDatabase.getInstance();
        DatabaseReference new_reference=new_database.getReference("Profile/"+id+"/Types");
        new_reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                BloodTypes m=snapshot.getValue(BloodTypes.class);
                Toast.makeText(BankProfile.this, m.getType(), Toast.LENGTH_SHORT).show();
                ls.add(new BloodTypes(m.getType(), m.getAvailability()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Toast.makeText(DonorProfile.this, profile.getName()+"", Toast.LENGTH_SHORT).show();

    }

}