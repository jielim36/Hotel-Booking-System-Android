package com.hotel_booking_systems_android.activity.TenantMainPage_Part;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.activity.TenantMainPage_Part.custom.ItemAdapter;
import com.hotel_booking_systems_android.activity.TenantMainPage_Part.service.ItemService;

public class AmountDetailsActivity extends AppCompatActivity {

    ItemService itemService = new ItemService();
    ListView listView;
    ItemAdapter itemAdapter;
    TextView totalAmountValue;
    ImageButton previousPage_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_details);
        initializeComponent();
        initializeEvent();
    }

    private void initializeEvent() {
        previousPage_btn.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void initializeComponent(){
        listView = findViewById(R.id.amount_details_listview);
        itemAdapter = new ItemAdapter(this);
        listView.setAdapter(itemAdapter);
        previousPage_btn = findViewById(R.id.amountDetails_back_btn);

        totalAmountValue = findViewById(R.id.amount_details_totalAmountValue);
        totalAmountValue.setText(String.valueOf(itemService.getTotalAmount()));

    }
}