package com.hotel_booking_systems_android.Employee.Room.ChangeRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.Employee.MyDatabaseHelper;
import com.hotel_booking_systems_android.Employee.Room.RoomListActivity;
import com.hotel_booking_systems_android.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.R;

@SuppressWarnings("all")
public class ChangeRoomStatusActivity extends AppCompatActivity {
    Button btn_available, btn_booked, btn_maintain, btn_cleaning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_room_status);

        // find views
        btn_available = findViewById(R.id.available);
        btn_booked = findViewById(R.id.booked);
        btn_maintain = findViewById(R.id.maintain);
        btn_cleaning = findViewById(R.id.cleaning);


        // get intent
        Intent intent = getIntent();
        String room_no = intent.getStringExtra("room_no");

        // 数据库帮助类
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(ChangeRoomStatusActivity.this);

        // change status
        btn_available.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在点击事件中执行数据库更新操作
                boolean success = dbHelper.updateStatus(room_no, RoomStatus.AVAILABLE.toString());

                // 根据数据库操作结果进行逻辑处理
                if (success) {
                    Toast.makeText(ChangeRoomStatusActivity.this, "Room status set to AVAILABLE", Toast.LENGTH_SHORT).show();
                    // 如果需要更新 UI 或执行其他操作，可以在这里添加代码
                    Intent intent = new Intent(ChangeRoomStatusActivity.this, RoomListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangeRoomStatusActivity.this, "Failed to update room status", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeRoomStatusActivity.this, RoomListActivity.class);
                    startActivity(intent);
                }

            }
        });

        btn_booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在点击事件中执行数据库更新操作
                boolean success = dbHelper.updateStatus(room_no, RoomStatus.BOOKED.toString());

                // 根据数据库操作结果进行逻辑处理
                if (success) {
                    Toast.makeText(ChangeRoomStatusActivity.this, "Room status set to BOOKED", Toast.LENGTH_SHORT).show();
                    // 如果需要更新 UI 或执行其他操作，可以在这里添加代码
                    Intent intent = new Intent(ChangeRoomStatusActivity.this, RoomListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangeRoomStatusActivity.this, "Failed to update room status", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeRoomStatusActivity.this, RoomListActivity.class);
                    startActivity(intent);
                }

            }
        });

        btn_maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在点击事件中执行数据库更新操作
                boolean success = dbHelper.updateStatus(room_no, RoomStatus.MAINTENANCE.toString());

                // 根据数据库操作结果进行逻辑处理
                if (success) {
                    Toast.makeText(ChangeRoomStatusActivity.this, "Room status set to MAINTENANCE", Toast.LENGTH_SHORT).show();
                    // 如果需要更新 UI 或执行其他操作，可以在这里添加代码
                    Intent intent = new Intent(ChangeRoomStatusActivity.this, RoomListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangeRoomStatusActivity.this, "Failed to update room status", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeRoomStatusActivity.this, RoomListActivity.class);
                    startActivity(intent);
                }

            }
        });

        btn_cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 在点击事件中执行数据库更新操作
                boolean success = dbHelper.updateStatus(room_no, RoomStatus.CLEANING.toString());

                // 根据数据库操作结果进行逻辑处理
                if (success) {
                    Toast.makeText(ChangeRoomStatusActivity.this, "Room status set to CLEANING", Toast.LENGTH_SHORT).show();
                    // 如果需要更新 UI 或执行其他操作，可以在这里添加代码
                    Intent intent = new Intent(ChangeRoomStatusActivity.this, RoomListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangeRoomStatusActivity.this, "Failed to update room status", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeRoomStatusActivity.this, RoomListActivity.class);
                    startActivity(intent);
                }

            }
        });

        // back
        View btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangeRoomStatusActivity.this, "back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    //activity_change_room_status
}
