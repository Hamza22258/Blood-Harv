package com.hamza.blood_harv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {
    EditText email,  password;
    Button createAccount;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        createAccount=findViewById(R.id.createAccount);
        mAuth=FirebaseAuth.getInstance();
        String type= getIntent().getStringExtra("type");

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if(type.equals("Donor")){
                                    Intent intent=new Intent(CreateAccountActivity.this, CreateDonorProfile.class);
                                    intent.putExtra("type", "Donor");
                                    startActivity(intent);

                                }
                                else if(type.equals("Blood Bank")){
                                    Intent intent=new Intent(CreateAccountActivity.this, CreateBloodBankProfile.class);
                                    intent.putExtra("type", "Blood Bank");
                                    startActivity(intent);

                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateAccountActivity.this,"Signup Failure", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });

            }
        });

    }
}