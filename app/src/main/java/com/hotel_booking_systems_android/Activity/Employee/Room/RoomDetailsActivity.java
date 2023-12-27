package com.hotel_booking_systems_android.Activity.Employee.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.Activity.Employee.Room.ChangeRoom.ChangeRoomPriceActivity;
import com.hotel_booking_systems_android.Activity.Employee.Room.ChangeRoom.ChangeRoomStatusActivity;
import com.hotel_booking_systems_android.Activity.Employee.Room.DeleteRoom.DeleteRoomActivity;
import com.hotel_booking_systems_android.R;

@SuppressWarnings("all")
public class RoomDetailsActivity extends AppCompatActivity {
    private TextView tv_room_no, tv_price, tv_type, tv_floor_no, tv_max_people, tv_describe;
    private RoomStatus status;
    private ImageView iv_image;

    private String room_no, price, type, floor_no, max_people, describe;
    private int images;
    MyDatabaseHelper roomListDatabaseHelper;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details1);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(RoomDetailsActivity.this);

        // find views
        tv_room_no = findViewById(R.id.room_no);
        tv_price = findViewById(R.id.price);
        tv_type = findViewById(R.id.type);
        tv_floor_no = findViewById(R.id.floor_no);
        tv_max_people = findViewById(R.id.max_people);
        tv_describe = findViewById(R.id.describe);
        iv_image = findViewById(R.id.room_image);

        // set text view
        // 获取传递过来的房间信息
        Intent intent = getIntent();
        if (intent != null) {
            // 根据房间ID获取其他信息，然后更新UI
            room_no = intent.getStringExtra("room_no");
            price = intent.getStringExtra("price");
            type = intent.getStringExtra("type");
            floor_no = intent.getStringExtra("floor_no");
            max_people = intent.getStringExtra("max_people");
            describe = intent.getStringExtra("describe");
            images = intent.getIntExtra("images", 0);

            // set text
            tv_room_no.setText(room_no);
            tv_price.setText(price);
            tv_type.setText(type);
            tv_floor_no.setText(floor_no);
            tv_max_people.setText(max_people);
            tv_describe.setText(describe);

            // set images
            iv_image.setImageResource(images);

            // get status
            status = (RoomStatus) intent.getSerializableExtra("status");
        }

        // buttons
        // back to previous page (room list)
        View btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomDetailsActivity.this, "back", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(RoomDetailsActivity.this, RoomListActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        // change status
        View img_status = findViewById(R.id.room_status);
        img_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomDetailsActivity.this, "room status!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RoomDetailsActivity.this, ChangeRoomStatusActivity.class);
                intent.putExtra("room_no", room_no);
                startActivity(intent);
            }
        });

        // go to change price page
        Button btn_change = findViewById(R.id.btn_change_price);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomDetailsActivity.this, "go change price", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RoomDetailsActivity.this, ChangeRoomPriceActivity.class);
                intent.putExtra("room_no", room_no);
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });

        // go to delete room page
        Button btn_delete = findViewById(R.id.btn_delete_room);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomDetailsActivity.this, "go delete room", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RoomDetailsActivity.this, DeleteRoomActivity.class);
                intent.putExtra("room_no", tv_room_no.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

}
