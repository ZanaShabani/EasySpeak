package com.example.speakeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    Button signup_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup_button = findViewById(R.id.signup_button);

        signup_button.setOnClickListener(view->{
            startActivity(new Intent(this, Signup.class));
        });

    }

}
