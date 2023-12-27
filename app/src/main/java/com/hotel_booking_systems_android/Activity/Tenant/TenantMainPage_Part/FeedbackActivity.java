package com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;

public class FeedbackActivity extends AppCompatActivity {

    ImageButton previousPage_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initializeComponent();
        initializeEvent();
    }

    private void initializeEvent() {
        previousPage_btn.setOnClickListener(v -> onBackPressed());
    }

    private void initializeComponent() {
        previousPage_btn = findViewById(R.id.feedback_back_btn);
    }
}