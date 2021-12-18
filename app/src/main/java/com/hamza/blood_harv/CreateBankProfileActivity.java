package com.hamza.blood_harv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CreateBankProfileActivity extends AppCompatActivity {
    private GoogleMap mMap;
    TextView name, location;
    Button addDetails, uploadImage;
    Uri selectedImage;
    Double longitude,latitude;
    String encodedImage;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bank_profile);
        name=findViewById(R.id.name);
        location=findViewById(R.id.location);
        addDetails=findViewById(R.id.addDetails);
        uploadImage=findViewById(R.id.uploadImage);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Profile");
        String ip=getResources().getString(R.string.IP);


        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage!=null){
                    String accountType=getIntent().getStringExtra("type");

                    StorageReference st= FirebaseStorage.getInstance().getReference();
                    String id= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    st=st.child("images/"+id+".jpg");
                    Toast.makeText(CreateBankProfileActivity.this,id+"",Toast.LENGTH_LONG).show();
                    st.putFile(selectedImage)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task=taskSnapshot.getStorage().getDownloadUrl();
                                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String dp=uri.toString();
                                            reference.child(id).setValue(
                                                    new Profile(
                                                            name.getText().toString()
                                                            ,"Blood Bank"
                                                            ,location.getText().toString()
                                                            ,dp));
                                            String url="http://"+ip+"/Account/insertAccount.php";
                                            StringRequest request=new StringRequest(
                                                    Request.Method.POST,
                                                    url,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            try {
                                                                JSONObject object=new JSONObject(response);
                                                                if(object.getInt("Success")==1){
                                                                    Toast.makeText(CreateBankProfileActivity.this,"Bank Profile Created",Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(CreateBankProfileActivity.this, BankProfile.class));
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
                                                            Toast.makeText(CreateBankProfileActivity.this, error+"", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                            ){
                                                protected Map<String,String> getParams(){
                                                    Map<String,String> data=new HashMap<String,String>();
                                                    String myid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    data.put("uid",myid);
                                                    data.put("image",encodedImage);
                                                    return data;
                                                }
                                            };
                                            Volley.newRequestQueue(CreateBankProfileActivity.this).add(request);


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CreateBankProfileActivity.this,"Image uploading failed",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateBankProfileActivity.this,"Image uploading failed on main upload",Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else{
                    Toast.makeText(CreateBankProfileActivity.this,"Image not loaded",Toast.LENGTH_LONG).show();
                }
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,200);
            }
        });


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200 && resultCode==RESULT_OK){
            selectedImage= data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageStore(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Toast.makeText(CreateBankProfileActivity.this,"Image Select Success",Toast.LENGTH_SHORT).show();
        }
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }
}