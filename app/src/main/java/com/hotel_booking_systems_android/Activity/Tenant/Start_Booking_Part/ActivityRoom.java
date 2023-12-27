package com.hotel_booking_systems_android.Activity.Tenant.Start_Booking_Part;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hotel_booking_systems_android.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityRoom extends AppCompatActivity {

    TextView textViewCheckInDate;
    TextView textViewCheckOutDate;

    Date checkInDate;
    Date checkOutDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        // Set up the ActionBar with the "Up" button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rooms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initialize TextViews
        textViewCheckInDate = findViewById(R.id.textViewCheckInDate);
        textViewCheckOutDate = findViewById(R.id.textViewCheckOutDate);

        // Get data from Intent
        Intent intent = getIntent();
        if (intent != null) {
            long checkInTime = intent.getLongExtra("CHECK_IN_DATE", 0);
            long checkOutTime = intent.getLongExtra("CHECK_OUT_DATE", 0);

            // Convert long values to Date objects
            checkInDate = new Date(checkInTime);
            checkOutDate = new Date(checkOutTime);

            // Display dates in TextViews
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            textViewCheckInDate.setText("Check-In-Date: " + dateFormat.format(checkInDate));
            textViewCheckOutDate.setText("Check-Out-Date: " + dateFormat.format(checkOutDate));
        }

        // Find the ImageButtons
        ImageButton singleRoomImageButton = findViewById(R.id.singleRoomImage);
        ImageButton doubleRoomImageButton = findViewById(R.id.doubleRoomImage);
        ImageButton familyRoomImageButton = findViewById(R.id.familyRoomImage);
        ImageButton suiteRoomImageButton = findViewById(R.id.suiteRoomImage);

        // Set click listeners
        singleRoomImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNewPage(ActivitySingleRoom.class, "Single Room");
            }
        });

        doubleRoomImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNewPage(ActivityDoubleRoom.class, "Double Room");
            }
        });

        familyRoomImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNewPage(ActivityFamilyRoom.class, "Family Room");
            }
        });

        suiteRoomImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNewPage(ActivitySuitRoom.class, "Suite Room");
            }
        });
    }

    private void navigateToNewPage(Class<?> destinationClass, String roomType) {
        Intent intent = new Intent(this, destinationClass);
        intent.putExtra("ROOM_TYPE", roomType);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}