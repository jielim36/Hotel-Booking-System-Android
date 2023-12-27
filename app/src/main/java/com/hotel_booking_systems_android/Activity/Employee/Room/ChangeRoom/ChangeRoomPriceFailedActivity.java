package com.hotel_booking_systems_android.Activity.Employee.Room.ChangeRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.Activity.Employee.Room.RoomListActivity;
import com.hotel_booking_systems_android.R;

public class ChangeRoomPriceFailedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_room_price_failed);

        View btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeRoomPriceFailedActivity.this, RoomListActivity.class);
                startActivity(intent);
                finish(); // 关闭当前Activity
            }
        });
    }
}
