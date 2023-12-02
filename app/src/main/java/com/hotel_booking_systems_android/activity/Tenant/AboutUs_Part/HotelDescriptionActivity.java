package com.hotel_booking_systems_android.activity.Tenant.AboutUs_Part;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hotel_booking_systems_android.R;

public class HotelDescriptionActivity extends AppCompatActivity {
    ImageView contactInstagram;
    ImageView contactWhatsapp;
    ImageView contactPhone;
    ImageButton hotelDescriptionBack_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_description);

        initializeComponent();
        initializeEvent();

    }

    public void initializeComponent(){
        contactInstagram = findViewById(R.id.contact_instagram);
        contactWhatsapp = findViewById(R.id.contact_whatsapp);
        contactPhone = findViewById(R.id.contact_phone);
        hotelDescriptionBack_btn = findViewById(R.id.hotelDescription_back_btn);
    }

    public void initializeEvent(){
        contactWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://wa.me/601116540123";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        contactInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/jielim36/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        contactPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "01116540123";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phoneNumber));
                startActivity(intent);
            }
        });

        hotelDescriptionBack_btn.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}