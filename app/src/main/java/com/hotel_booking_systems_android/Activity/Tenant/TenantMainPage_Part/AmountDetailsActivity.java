package com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.DB.ItemDatabaseHelper;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Item;
import com.hotel_booking_systems_android.custom.ItemAdapter;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class AmountDetailsActivity extends AppCompatActivity {

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

        //get amount data
        ItemDatabaseHelper itemDBHelper = new ItemDatabaseHelper(getApplicationContext());
        Integer userId = AccountSharedPreferences.getInstance(getApplicationContext()).getUserId();
        List<Item> itemListByUserId = itemDBHelper.getUnpaidItemByUserId(userId);

        itemAdapter = new ItemAdapter(this , (ArrayList<Item>) itemListByUserId);
        listView.setAdapter(itemAdapter);
        previousPage_btn = findViewById(R.id.amountDetails_back_btn);

        totalAmountValue = findViewById(R.id.amount_details_totalAmountValue);

        double totalAmount = 0;
        for(Item item : itemListByUserId){
            totalAmount += item.getTotalAmount();
        }
        totalAmountValue.setText(String.valueOf(totalAmount));

    }
}