package com.example.speakeasy;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {
    String email;

    DB DB;

    TextView greetingView;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        email = getIntent().getStringExtra("USER_EMAIL");
        DB = new DB(this);
        greetingView = findViewById(R.id.greeting);


        String username = DB.getUsername(email);
        if (username != null) {
            greetingView.setText("Hello " + username + ",");
        } else {
            greetingView.setText("Hello,");  // Default if user not found
        }


    }
}
