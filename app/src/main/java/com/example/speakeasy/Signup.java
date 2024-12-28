package com.example.speakeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    TextView login_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        login_button = findViewById(R.id.login_button);


        login_button.setOnClickListener(view->{
            startActivity(new Intent(this, Login.class));
        });


    }
}
