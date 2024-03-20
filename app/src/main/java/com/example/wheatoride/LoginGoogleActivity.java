package com.example.wheatoride;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginEmailActivity extends AppCompatActivity {

    EditText emailInput;
    Button sendOtpBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        emailInput = findViewById(R.id.login_email_address);
        sendOtpBtn = findViewById(R.id.send_otp_btn);
        progressBar = findViewById(R.id.login_progress_bar);

        progressBar.setVisibility(View.GONE);

        sendOtpBtn.setOnClickListener((v) -> {
            String email = emailInput.getText().toString().trim();
            if (!email.isEmpty()) {
                // Assuming you have a method to send OTP
                sendOtp(email);
            }
        });
    }

    private void sendOtp(String email) {
        // Placeholder code for sending OTP
        // Replace this with your actual logic to send OTP
        // For demonstration purposes, we'll just show a toast message
        // You should replace this with your actual OTP sending mechanism
        Toast.makeText(this, "OTP sent to " + email, Toast.LENGTH_SHORT).show();

        // Proceed to the OTP verification screen
        Intent intent = new Intent(LoginEmailActivity.this, LoginOtpActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}
