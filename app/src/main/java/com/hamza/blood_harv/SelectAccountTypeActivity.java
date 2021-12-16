package com.hamza.blood_harv;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectAccountTypeActivity extends AppCompatActivity {
    RadioGroup rdGroup;
    RadioButton rdButton;
    Button createAccount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);

        rdGroup=findViewById(R.id.select_account_type);

        createAccount=findViewById(R.id.createprofile);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioBtnId=rdGroup.getCheckedRadioButtonId();
                rdButton=findViewById(radioBtnId);

                String result=rdButton.getText().toString();
                if(result.equals("Donor")){
                    Intent intent=new Intent(SelectAccountTypeActivity.this, CreateAccountActivity.class);
                    intent.putExtra("type", "Donor");
                    startActivity(intent);
                }
                if(result.equals("Blood Bank")){
                    Intent intent=new Intent(SelectAccountTypeActivity.this, CreateAccountActivity.class);
                    intent.putExtra("type", "Blood Bank");
                    startActivity(intent);
                }
            }
        });



    }
}




















//package com.hamza.blood_harv;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class SelectAccountTypeActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_account_type);
//    }
//}