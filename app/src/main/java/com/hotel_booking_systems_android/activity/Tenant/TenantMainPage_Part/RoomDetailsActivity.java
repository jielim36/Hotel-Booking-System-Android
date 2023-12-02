package com.hotel_booking_systems_android.activity.Tenant.TenantMainPage_Part;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;

public class RoomDetailsActivity extends AppCompatActivity {

    ImageButton amountDetails_btn;
    ImageButton previousPage_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        initializeComponent();
        initializeEvent();
    }

    private void initializeComponent() {
        amountDetails_btn = findViewById(R.id.room_details_amount_details_btn);
        previousPage_btn = findViewById(R.id.room_details_back_btn);
    }

    private void initializeEvent() {
        amountDetails_btn.setOnClickListener(v -> {
            Intent intent = new Intent(RoomDetailsActivity.this,AmountDetailsActivity.class);
            startActivity(intent);
        });

        previousPage_btn.setOnClickListener(v -> onBackPressed());
    }
}