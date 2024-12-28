package com.example.speakeasy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {

    // Database instance
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize database
        db = new DB(this);

        // Initialize UI components
        EditText emailInput = findViewById(R.id.Email_input);
        Button resetPasswordButton = findViewById(R.id.rereset_password);

        // Set up button click listener
        resetPasswordButton.setOnClickListener(v -> {
            // Get the email entered by the user
            String email = emailInput.getText().toString().trim();

            // Validate input
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Generate or fetch a temporary password
            String tempPassword = "newTemporaryPassword123"; // Replace with dynamic logic if needed

            // Update the password in the database
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
