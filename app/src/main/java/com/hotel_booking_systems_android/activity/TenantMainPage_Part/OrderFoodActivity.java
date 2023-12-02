package com.hotel_booking_systems_android.activity.TenantMainPage_Part;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.activity.TenantMainPage_Part.custom.FoodAdapter;


public class OrderFoodActivity extends AppCompatActivity {

    ListView foodListView;
    FoodAdapter foodAdapter;
    ImageButton previousPage_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);

        initializeComponent();
        initializeEvent();
    }

    private void initializeComponent() {
        foodListView = findViewById(R.id.order_food_list);
        foodAdapter = new FoodAdapter(this);
        foodListView.setAdapter(foodAdapter);
        previousPage_btn = findViewById(R.id.order_food_back_btn);
    }

    private void initializeEvent() {
        previousPage_btn.setOnClickListener(v -> onBackPressed());

        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent foodDetailsPage = new Intent(OrderFoodActivity.this , FoodDetailsActivity.class);
                foodDetailsPage.putExtra("foodId",position);
                startActivity(foodDetailsPage);
            }
        });
    }
}