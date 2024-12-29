package com.example.speakeasy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    Button signup_button;
    EditText email;
    EditText password;
    TextView forgot_password1;
    Button login_button;
    DB DB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Notification.createNotificationChannel(this);
        signup_button = findViewById(R.id.signup_button);
        email = findViewById(R.id.email);
        password= findViewById(R.id.password);
        forgot_password1 = findViewById(R.id.Forgot_Password);
        login_button = findViewById(R.id.login_button);

        DB = new DB(this);
        login_button.setOnClickListener(view->{
            String email2 = email.getText().toString().trim();
            String password2 = password.getText().toString().trim();

            if (email2.isEmpty() || password2.isEmpty()) {
                Toast.makeText(LogIn.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return; }
            if (!DB.userExists(email2)) {
                Toast.makeText(LogIn.this, "Account does not exist", Toast.LENGTH_SHORT).show();
            } else if (!DB.validateUser(email2, password2)) {
                Toast.makeText(LogIn.this, "Incorrect password", Toast.LENGTH_SHORT).show();
            } else { loginNotification(); Intent intent = new Intent(this, HomePage.class);
                startActivity(intent); } });

        signup_button.setOnClickListener(view->{
            startActivity(new Intent(this, SignUp.class));
        });
        forgot_password1.setOnClickListener(view->{
            startActivity(new Intent(this, ForgotPassword.class));
        });

    }

    private void loginNotification() {

            Notification.sendNotification(
                    this,
                    "Welcome!",
                    "Thanks for logging in! Start your learning journey now."
            );
        }


}
