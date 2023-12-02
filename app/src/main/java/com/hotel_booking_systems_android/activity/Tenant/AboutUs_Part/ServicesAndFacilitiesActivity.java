package com.hotel_booking_systems_android.activity.Tenant.AboutUs_Part;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.hotel_booking_systems_android.R;

public class ServicesAndFacilitiesActivity extends AppCompatActivity {

    ImageButton servicesAndFacilities_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_and_facilities);

        initializeMember();
        initializeEvent();
    }

    private void initializeMember() {
        servicesAndFacilities_back_btn = findViewById(R.id.servicesAndFacilities_back_btn);
    }
    private void initializeEvent() {
        servicesAndFacilities_back_btn.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}