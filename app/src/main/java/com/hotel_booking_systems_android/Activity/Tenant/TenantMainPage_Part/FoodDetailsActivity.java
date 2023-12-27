package com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Food;
import com.hotel_booking_systems_android.bean.Item;


public class FoodDetailsActivity extends AppCompatActivity {

    ImageButton previousPage_btn;
    ImageView foodImg;
    TextView foodName;
    TextView foodPrice;
    TextView foodStock;
    TextView foodDescription;
    TextView foodQuantity;
    ImageButton increaseQty_btn;
    ImageButton decreaseQty_btn;
    Button order_btn;
    Food food;
    int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        initializeComponent();
        initializeEvent();
    }

    private void initializeComponent() {
        previousPage_btn = findViewById(R.id.food_details_back_btn);
        foodImg = findViewById(R.id.food_list_img);
        foodName = findViewById(R.id.food_details_name);
        foodPrice = findViewById(R.id.food_details_price);
        foodStock = findViewById(R.id.food_details_stock);
        foodDescription = findViewById(R.id.food_details_description);
        foodQuantity = findViewById(R.id.food_details_quantity);
        increaseQty_btn = findViewById(R.id.food_details_increase_quantity_btn);
        decreaseQty_btn = findViewById(R.id.food_details_decrease_quantity_btn);
        order_btn = findViewById(R.id.food_details_order_btn);

        //initialize value
        foodId = getIntent().getIntExtra("foodId", -1);
        if (foodId < 0){
            Toast.makeText(this, "Error Food Selection", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        food = Food.getFoodListInstance().get(foodId);
        foodName.setText(food.getName());
        foodPrice.setText("Price: "+String.valueOf(food.getPrice()));
        foodStock.setText("Stock: " + String.valueOf(food.getStock()));
        foodDescription.setText(food.getDescription());
        foodQuantity.setText("1");//default = 1

    }

    private void initializeEvent() {

        previousPage_btn.setOnClickListener(v -> onBackPressed());

        increaseQty_btn.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(foodQuantity.getText().toString());
            if (currentQuantity >= 1 && currentQuantity <= 9){
                foodQuantity.setText(String.valueOf(currentQuantity+1));
            }
        });

        decreaseQty_btn.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(foodQuantity.getText().toString());
            if (currentQuantity >= 2 && currentQuantity <= 10){
                foodQuantity.setText(String.valueOf(currentQuantity-1));
            }
        });

        order_btn.setOnClickListener(v -> {
            int currentStock = Food.getFoodListInstance().get(foodId).getStock();
            int quantity = Integer.parseInt(foodQuantity.getText().toString());
//            Item.getItemListInstance().add(new Item(Item.getItemListInstance().size()+1,food.getName(),food.getPrice(),quantity));
            Food.getFoodListInstance().get(foodId).setStock(currentStock-quantity);//update stock number
            Intent intent = new Intent(FoodDetailsActivity.this, OrderFoodActivity.class);
            startActivity(intent);
        });

    }
}