package com.hamza.blood_harv;

import androidx.annotation.NonNull;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class CreateDonorProfile extends AppCompatActivity {
    private GoogleMap mMap;
    TextView name, bloodType, location, age, gender;
    Button addDetails, uploadImage;
    Uri selectedImage;
    Double longitude,latitude;
    Bitmap bitmap;
    String encodedImage;
    ImageView temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_donor_profile);
        name=findViewById(R.id.name);
        bloodType=findViewById(R.id.bloodType);
        location=findViewById(R.id.location);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        addDetails=findViewById(R.id.addDetails);
        uploadImage=findViewById(R.id.uploadImage);
        temp=findViewById(R.id.temp);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Profile");

        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage!=null){
                    StorageReference st= FirebaseStorage.getInstance().getReference();
                    String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    st=st.child("images/"+id+".jpg");
                    Toast.makeText(CreateDonorProfile.this,id+"",Toast.LENGTH_LONG).show();
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
                                                            ,bloodType.getText().toString()
                                                            ,"Donor"
                                                            ,location.getText().toString()
                                                            ,age.getText().toString()
                                                            ,gender.getText().toString()
                                                            ,dp, "false")).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        String url="http://192.168.0.100/Account/insertAccount.php";
                                                        StringRequest request=new StringRequest(
                                                                Request.Method.POST,
                                                                url,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        try {
                                                                            JSONObject object=new JSONObject(response);
                                                                            if(object.getInt("Success")==1){
//                                                                                Toast.makeText(CreateDonorProfile.this, object.getString("msg")+"", Toast.LENGTH_LONG).show();
                                                                                Toast.makeText(CreateDonorProfile.this,"User Registered",Toast.LENGTH_LONG).show();
//                                                                                Bitmap decdedImage= BitmapFactory.decodeByteArray( android.util.Base64.decode(encodedImage, Base64.DEFAULT),
//                                                                                        0, android.util.Base64.decode(encodedImage, Base64.DEFAULT).length);
//                                                                                temp.setImageBitmap(decdedImage);
                                                                                startActivity(new Intent(CreateDonorProfile.this, DonorProfile.class));
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
                                                                        Toast.makeText(CreateDonorProfile.this, error+"", Toast.LENGTH_LONG).show();
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
                                                        Volley.newRequestQueue(CreateDonorProfile.this).add(request);


                                                    }
                                                    else{
                                                        Toast.makeText(CreateDonorProfile.this,"User Not Registered",Toast.LENGTH_LONG).show();
                                                        finish();
                                                    }
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CreateDonorProfile.this,"Image uploading failed",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateDonorProfile.this,"Image uploading failed on main upload",Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else{
                    Toast.makeText(CreateDonorProfile.this,"Image not loaded",Toast.LENGTH_LONG).show();
                }
            }
        });
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pickPhoto = new Intent();
                pickPhoto.setType("images/*");
                pickPhoto.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(pickPhoto,"select image") , 1);
            }

        });

//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                snapshot.getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if(requestCode==1) {
            if (resultCode == RESULT_OK) {
                selectedImage = imageReturnedIntent.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imageStore(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(CreateDonorProfile.this, "Add Image", Toast.LENGTH_LONG).show();

            }
        }
    }

    public void getlivelocation(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                latitude=mMap.getCameraPosition().target.latitude;
                longitude=mMap.getCameraPosition().target.longitude;
            }
        });
    }
    public void insertImg(int id , Bitmap img ) {


        byte[] data = getBitmapAsByteArray(img); // this is a function



    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[] imageBytes = stream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }

}