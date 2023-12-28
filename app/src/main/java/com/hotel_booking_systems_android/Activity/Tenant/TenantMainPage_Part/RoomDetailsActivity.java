package com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.Activity.Employee.Room.Room;
import com.hotel_booking_systems_android.DB.ItemDatabaseHelper;
import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.DB.TenantRoomDatabaseHelper;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Item;
import com.hotel_booking_systems_android.bean.TenantRoom;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

import java.util.List;

public class RoomDetailsActivity extends AppCompatActivity {

    private TextView roomIdTextView, roomSizeTextView, customerNameTextView, totalAmountTextView;
    private ImageButton amountDetailsButton;
    private Button back_btn;
    private ImageButton navigateBack_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        // Initialize views from the layout
        roomIdTextView = findViewById(R.id.checkout_roomId);
        roomSizeTextView = findViewById(R.id.room_size_text_view);
        customerNameTextView = findViewById(R.id.extendStay_customerName);
        totalAmountTextView = findViewById(R.id.room_details_amount);
        amountDetailsButton = findViewById(R.id.room_details_amount_details_btn);
        back_btn = findViewById(R.id.checkout_back_btn);
        navigateBack_btn = findViewById(R.id.room_details_back_btn);

        //get value
        AccountSharedPreferences accSp = AccountSharedPreferences.getInstance(this);
        Integer userId = accSp.getUserId();

        //all rooms
        TenantRoomDatabaseHelper tenantRoomDatabaseHelper = new TenantRoomDatabaseHelper(getApplicationContext());
        List<TenantRoom> rooms = tenantRoomDatabaseHelper.getTenantRoomsByUserIdAndStatus(userId, TenantRoom.Status.CHECKED_IN);
        String roomsNoContent = "";
        for(int i = 0 ; i < rooms.size() ; i++){
            if(i > 0){
                roomsNoContent += "," + rooms.get(i).getRoomId();
            }else{
                roomsNoContent += String.valueOf(rooms.get(i).getRoomId());
            }
        }

        MyDatabaseHelper roomDBHelper = new MyDatabaseHelper(this);
        String roomType = "";
        for(int i = 0 ; i < rooms.size() ; i++){
            Room room = roomDBHelper.getRoomDetails(String.valueOf(rooms.get(i).getRoomId()));
            if(i > 0){
                roomType += "," + room.getType();
            }else{
                roomType += String.valueOf(room.getType());
            }
        }

        ItemDatabaseHelper itemDBHelper = new ItemDatabaseHelper(this);
        List<Item> unpaidItemListByUserId = itemDBHelper.getUnpaidItemByUserId(userId);

        double totalAmount = 0;
        for(Item item : unpaidItemListByUserId){
            totalAmount += item.getTotalAmount();
            Log.e("amount", "Item: " + item.getTotalAmount());
        }

        // Initialize values (replace these with your actual values)
        roomIdTextView.setText(roomsNoContent);
        roomSizeTextView.setText(roomType);
        customerNameTextView.setText(accSp.getUsername());
        totalAmountTextView.setText(String.valueOf(totalAmount));

        // Set click listener for the amount details button (if needed)
        amountDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(RoomDetailsActivity.this,AmountDetailsActivity.class);
            startActivity(intent);
        });

        back_btn.setOnClickListener(v -> {
            onBackPressed();
        });

        navigateBack_btn.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}