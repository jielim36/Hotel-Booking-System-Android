package com.hotel_booking_systems_android.activity.Tenant.Start_Booking_Part;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.hotel_booking_systems_android.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingRoomActivity extends AppCompatActivity {

    TextInputEditText checkInDateEditText, checkOutDateEditText;
    Button searchButton;
    Date checkInDate, checkOutDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_room);

        // Set up the ActionBar with the "Up" button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        checkInDateEditText = findViewById(R.id.checkInDate);
        checkOutDateEditText = findViewById(R.id.checkOutDate);
        searchButton = findViewById(R.id.searchButton);

        checkInDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkInDateEditText);
            }
        });

        checkOutDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(checkOutDateEditText);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInDate != null && checkOutDate != null) {
                    // Create an Intent to start the ActivityRoom
                    Intent intent = new Intent(BookingRoomActivity.this, ActivityRoom.class);

                    // Pass the selected dates as extras to the next activity
                    intent.putExtra("CHECK_IN_DATE", checkInDate.getTime());
                    intent.putExtra("CHECK_OUT_DATE", checkOutDate.getTime());

                    // Start the next activity
                    startActivity(intent);
                } else {
                    // Display a message indicating that dates are not selected
                    Toast.makeText(BookingRoomActivity.this, "Please select check-in and check-out dates", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDatePickerDialog(final TextInputEditText dateEditText) {
        Calendar selectedCalendar = Calendar.getInstance();

        if (dateEditText == checkOutDateEditText && checkInDate != null) {
            selectedCalendar.setTime(checkInDate);
        }

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedCalendar.set(year, month, dayOfMonth);
                Date selectedDate = selectedCalendar.getTime();

                if (dateEditText == checkInDateEditText) {
                    if (selectedDate.before(Calendar.getInstance().getTime())) {
                        Toast.makeText(BookingRoomActivity.this, "Invalid check-in date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    checkInDate = selectedDate;
                } else if (dateEditText == checkOutDateEditText) {
                    if (selectedDate.equals(checkInDate)) {
                        Toast.makeText(BookingRoomActivity.this, "Check-out date cannot be the same as check-in date", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (selectedDate.before(checkInDate)) {
                        Toast.makeText(BookingRoomActivity.this, "Invalid check-out date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    checkOutDate = selectedDate;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateEditText.setText(dateFormat.format(selectedDate));
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                dateSetListener,
                selectedCalendar.get(Calendar.YEAR),
                selectedCalendar.get(Calendar.MONTH),
                selectedCalendar.get(Calendar.DAY_OF_MONTH)
        );

        if (dateEditText == checkOutDateEditText && checkInDate != null) {
            datePickerDialog.getDatePicker().setMinDate(checkInDate.getTime());
        }

        if (dateEditText == checkInDateEditText) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }

        datePickerDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}