package com.example.speakeasy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {


    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        db = new DB(this);

        EditText emailInput = findViewById(R.id.Email_input);
        Button resetPasswordButton = findViewById(R.id.rereset_password);

        resetPasswordButton.setOnClickListener(v -> {

            String email = emailInput.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                return;
            }

            String tempPassword = "newTemporaryPassword123"; // Replace with dynamic logic if needed

            try {
                boolean isUpdated = db.updateUserPassword(email, tempPassword);
                if (isUpdated) {
                    Toast.makeText(this, "Password updated successfully. Temporary password is: " + tempPassword, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Failed to update password. Email not found.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "An error occurred: " + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }
}
