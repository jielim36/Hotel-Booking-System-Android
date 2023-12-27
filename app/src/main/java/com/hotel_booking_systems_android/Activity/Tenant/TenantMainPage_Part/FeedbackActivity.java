package com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.DB.FeedbackDatabaseHelper;
import com.hotel_booking_systems_android.DB.TenantRoomDatabaseHelper;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Feedback;
import com.hotel_booking_systems_android.bean.TenantRoom;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

import java.util.List;

public class FeedbackActivity extends AppCompatActivity {


    private TextView roomIdTextView, customerNameTextView;
    private RadioGroup radioGroup;
    private EditText contentEditText;
    private Button confirmButton;
    private ImageButton backButton;

    private FeedbackDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // Initialize the database helper
        databaseHelper = new FeedbackDatabaseHelper(this);

        // Initialize views from the layout
        roomIdTextView = findViewById(R.id.checkout_roomId);
        customerNameTextView = findViewById(R.id.extendStay_customerName);
        radioGroup = findViewById(R.id.radioGroup);
        contentEditText = findViewById(R.id.extendStay_extendDate);
        confirmButton = findViewById(R.id.checkout_confirm_btn);
        backButton = findViewById(R.id.feedback_back_btn);

        // Get data passed from the previous activity
        AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(getApplicationContext());
        TenantRoomDatabaseHelper tenantRoomDatabaseHelper = new TenantRoomDatabaseHelper(getApplicationContext());
        Integer userId = accSp.getUserId();
        List<TenantRoom> rooms = tenantRoomDatabaseHelper.getTenantRoomsByUserIdAndStatus(userId, TenantRoom.Status.CHECKED_IN);
        String roomsNoContent = "";
        for(int i = 0 ; i < rooms.size() ; i++){
            if(i > 0){
                roomsNoContent += "," + rooms.get(i).getRoomId();
            }else{
                roomsNoContent += String.valueOf(rooms.get(i).getRoomId());
            }
        }

        // Set data to respective TextViews
        roomIdTextView.setText(roomsNoContent);
        customerNameTextView.setText(accSp.getUsername());

        // Set click listener for the confirm button
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFeedbackToDatabase(userId,rooms.get(0).getRoomId());
            }
        });

        // Set click listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    // Add feedback data to the database
    private void addFeedbackToDatabase(Integer userId , Integer roomNo) {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == -1) {
            // If no category is selected
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        Feedback.Categories selectedCategory = Feedback.Categories.valueOf(selectedRadioButton.getText().toString().toUpperCase());

        String content = contentEditText.getText().toString();

        // Create a Feedback object
        Feedback feedback = new Feedback(userId, String.valueOf(roomNo), selectedCategory, content);

        // Add Feedback data to the database
        databaseHelper.addFeedback(feedback);

        // Show a toast indicating successful feedback addition
        Toast.makeText(this, "Feedback added successfully", Toast.LENGTH_SHORT).show();

        // Finish the current activity
        onBackPressed();
    }
}