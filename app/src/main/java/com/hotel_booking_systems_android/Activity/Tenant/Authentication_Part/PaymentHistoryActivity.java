package com.hotel_booking_systems_android.Activity.Tenant.Authentication_Part;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.hotel_booking_systems_android.DB.ItemDatabaseHelper;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Item;
import com.hotel_booking_systems_android.custom.ItemAdapter;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryActivity extends AppCompatActivity {

    ListView listView;
    ItemAdapter itemAdapter;
    ImageButton previousPage_btn;
    TextView paymentHistory_totalAmountValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        initComponent();
    }

    private void initComponent(){
        listView = findViewById(R.id.paymentHistory_listview);
        previousPage_btn = findViewById(R.id.paymentHistory_back_btn);
        paymentHistory_totalAmountValue = findViewById(R.id.paymentHistory_totalAmountValue);

        ItemDatabaseHelper itemDBHelper = new ItemDatabaseHelper(getApplicationContext());
        Integer userId = AccountSharedPreferences.getInstance(getApplicationContext()).getUserId();
        List<Item> itemListByUserId = itemDBHelper.getItemsByUserId(userId);

        itemAdapter = new ItemAdapter(this , (ArrayList<Item>) itemListByUserId);
        listView.setAdapter(itemAdapter);

        previousPage_btn.setOnClickListener(v -> {
            onBackPressed();
        });

        double totalAmount = 0 ;
        for(Item item : itemListByUserId){
            totalAmount += item.getTotalAmount();
        }
        paymentHistory_totalAmountValue.setText(String.valueOf(totalAmount));
    }
}