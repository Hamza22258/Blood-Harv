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

public class SelectAccountActivity extends AppCompatActivity {
    RadioGroup rdGroup;
    RadioButton rdButton;
    Button createAccount;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        rdGroup=findViewById(R.id.select_account_type);

        createAccount=findViewById(R.id.createprofile);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioBtnId=rdGroup.getCheckedRadioButtonId();
                rdButton=findViewById(radioBtnId);

                String result=rdButton.getText().toString();
                if(result.equals("Donor")){
                    Intent intent=new Intent(SelectAccountActivity.this, CreateAccountActivity.class);
                    intent.putExtra("type", "Donor");
                    startActivity(intent);
                }
                if(result.equals("Blood Bank")){
                    Intent intent=new Intent(SelectAccountActivity.this, CreateAccountActivity.class);
                    intent.putExtra("type", "Blood Bank");
                    startActivity(intent);
                }
            }
        });



    }
}
