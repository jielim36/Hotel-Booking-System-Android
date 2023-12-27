package com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.hotel_booking_systems_android.R;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CallServiceActivity extends AppCompatActivity {

    private ImageButton callService_back_btn;
    private ImageView instagramImageView, whatsappImageView, phoneImageView;
    private ImageView service01ImageView, service02ImageView, service03ImageView, service04ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_service);

        // Initialize components
        initializeComponents();

        // Set click events
        initializeEvents();
    }

    private void initializeComponents() {
        // Top contact buttons
        instagramImageView = findViewById(R.id.contact_instagram);
        whatsappImageView = findViewById(R.id.contact_whatsapp);
        phoneImageView = findViewById(R.id.contact_phone);

        // Service buttons
        service01ImageView = findViewById(R.id.service01_btn);
        service02ImageView = findViewById(R.id.service02_btn);
        service03ImageView = findViewById(R.id.service03_btn);
        service04ImageView = findViewById(R.id.service04_btn);

        callService_back_btn = findViewById(R.id.callService_back_btn);
    }

    private void initializeEvents() {

        callService_back_btn.setOnClickListener(v -> {
            onBackPressed();
        });

        // Top contact button click events
        instagramImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/jielim36/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        whatsappImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/601116540123";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        phoneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "01116540123";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });

        // Service button click events
        service01ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "01116540124";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });

        service02ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "01116540125";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });

        service03ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "01116540126";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });

        service04ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "01116540127";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });
    }
}