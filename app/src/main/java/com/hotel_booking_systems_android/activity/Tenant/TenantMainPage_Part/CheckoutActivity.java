package com.hotel_booking_systems_android.activity.Tenant.TenantMainPage_Part;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;

public class CheckoutActivity extends AppCompatActivity {

    ImageButton instructions_dropDown_btn;
    ImageButton amount_dropDown_btn;
    ImageButton back_btn;
    TextView instructionContent;
    boolean instructionDropdown = false;
    boolean amountDetailsDropdown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initializeComponent();
        initializeEvent();
    }

    private void initializeComponent() {
        instructions_dropDown_btn = findViewById(R.id.checkout_instructions_dropDown_btn);
        amount_dropDown_btn = findViewById(R.id.checkout_amount_details_dropDown_btn);
        instructionContent = findViewById(R.id.checkout_instructions_content);
        back_btn = findViewById(R.id.checkout_back_btn);
    }

    private void initializeEvent(){

        instructions_dropDown_btn.setOnClickListener(v -> {
            instructionDropdown = !instructionDropdown;
            if (instructionDropdown){
                instructionContent.setVisibility(TextView.VISIBLE);
            }else {
                instructionContent.setVisibility(TextView.GONE);
            }
        });

        amount_dropDown_btn.setOnClickListener(v -> {
            Intent intent = new Intent(CheckoutActivity.this,AmountDetailsActivity.class);
            startActivity(intent);
        });

        back_btn.setOnClickListener(v -> {
            onBackPressed();//go back to the previous page
        });
    }
}