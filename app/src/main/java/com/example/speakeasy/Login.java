package com.example.speakeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    Button signup_button;
    EditText email;
    EditText password;
    Button login_button;
    DB DB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signup_button = findViewById(R.id.signup_button);
        email = findViewById(R.id.email);
        password= findViewById(R.id.password);
        login_button = findViewById(R.id.login_button);

        DB = new DB(this);
        login_button.setOnClickListener(view->{
            String email2 = email.getText().toString().trim();
            String password2 = password.getText().toString().trim();

            if(DB.validateUser(email2, password2)){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });
        signup_button.setOnClickListener(view->{
            startActivity(new Intent(this, Signup.class));
        });

    }

}
