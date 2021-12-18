package com.hamza.blood_harv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class CreateProfile extends AppCompatActivity {
    TextView name, bloodType, accountType, location, age, gender;
    Button addDetails, uploadImage;
    Uri selectedImage;
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
        uploadImage=findViewById(R.id.uploadImage);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference reference=database.getReference("Profile");
        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage!=null){
                    StorageReference st= FirebaseStorage.getInstance().getReference();
                    String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                    st=st.child("images/"+id+".jpg");
                    Toast.makeText(CreateProfile.this,id+"",Toast.LENGTH_LONG).show();
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
                                                    new Profile("",
                                                            name.getText().toString()
                                                            ,bloodType.getText().toString()
                                                            ,accountType.getText().toString()
                                                            ,location.getText().toString()
                                                            ,age.getText().toString()
                                                            ,gender.getText().toString()
                                                            ,dp, "false")).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(CreateProfile.this,"User Registered",Toast.LENGTH_LONG).show();
                                                        finish();
                                                    }
                                                    else{
                                                        Toast.makeText(CreateProfile.this,"User Not Registered",Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(CreateProfile.this,"Image uploading failed",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(CreateProfile.this,"Image uploading failed on main upload",Toast.LENGTH_LONG).show();
                                }
                            });
                }
                else{
                    Toast.makeText(CreateProfile.this,"Image not loaded",Toast.LENGTH_LONG).show();
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


            } else {
                Toast.makeText(CreateProfile.this, "Add Image", Toast.LENGTH_LONG).show();

            }
        }
    }
}