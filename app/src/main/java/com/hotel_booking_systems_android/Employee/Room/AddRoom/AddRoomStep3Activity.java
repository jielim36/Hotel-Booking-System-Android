package com.hotel_booking_systems_android.Employee.Room.AddRoom;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.Employee.MyDatabaseHelper;
import com.hotel_booking_systems_android.Employee.Room.RoomListActivity;
import com.hotel_booking_systems_android.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.R;

@SuppressWarnings("all")
public class AddRoomStep3Activity extends AppCompatActivity {
    // Delcare View
    // Button
    private Button btn_back, btn_next;
    private TextView tv_room_no, tv_price, tv_type, tv_floor_no, tv_max_people, tv_describe;

    // store the input value
    private String room_no, price, type, floor_no, max_people, describe, status;

    MyDatabaseHelper roomListDatabaseHelper;

    private void initializeView() {
        // Buttons
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);

        // TextViews
        tv_room_no = findViewById(R.id.room_no);
        tv_price = findViewById(R.id.price);
        tv_type = findViewById(R.id.type);
        tv_floor_no = findViewById(R.id.floor_no);
        tv_max_people = findViewById(R.id.max_people);
        tv_describe = findViewById(R.id.describe);

        // get values from inputs
        Intent intent = getIntent();
        room_no = intent.getStringExtra("room_no");
        price = intent.getStringExtra("price");
        type = intent.getStringExtra("type");
        floor_no = intent.getStringExtra("floor_no");
        max_people = intent.getStringExtra("max_people");
        describe = intent.getStringExtra("describe");
    }

    private void setTextViews() {
        tv_room_no.setText(room_no);
        tv_price.setText(price);
        tv_type.setText(type);
        tv_floor_no.setText(floor_no);
        tv_max_people.setText(max_people);
        tv_describe.setText(describe);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room_step3);

        //  get the values from step1 and 2
        initializeView();

        // set text
        setTextViews();

        // set as maintenance
        status = RoomStatus.AVAILABLE.toString();

        //
        roomListDatabaseHelper = new MyDatabaseHelper(this);

        // go to other pages
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddRoomStep3Activity.this, "back to step 2", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        // submit and insert
        Button btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddRoomStep3Activity.this, "submit!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddRoomStep3Activity.this, RoomListActivity.class);
                startActivity(intent);
                insert();
                finish();
            }
        });


    }

    public void insert() {
        try {
            SQLiteDatabase db = roomListDatabaseHelper.getWritableDatabase();
            String sql = "INSERT INTO Rooms(room_no, price, type, floor_no, max_people, describe, status) VALUES(?,?,?,?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, room_no);
            statement.bindString(2, price);
            statement.bindString(3, type);
            statement.bindString(4, floor_no);
            statement.bindString(5, max_people);
            statement.bindString(6, describe); // 添加这一行来绑定 describe 字段的值
            statement.bindString(7, status); // 添加这一行来绑定 status 字段的值
            statement.execute();

            Toast.makeText(AddRoomStep3Activity.this, "Submitted!!", Toast.LENGTH_SHORT).show();

            //
            Intent intent = new Intent(AddRoomStep3Activity.this, AddRoomSuccessfulActivity.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            Toast.makeText(AddRoomStep3Activity.this, "Submit Fail - " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddRoomStep3Activity.this, AddRoomFailedActivity.class);
            startActivity(intent);
            finish();
            Log.e("TAG", e.getMessage());
        }
    }

}
