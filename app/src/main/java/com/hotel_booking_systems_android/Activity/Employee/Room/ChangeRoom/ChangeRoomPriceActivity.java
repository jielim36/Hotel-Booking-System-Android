package com.hotel_booking_systems_android.Activity.Employee.Room.ChangeRoom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.R;

@SuppressWarnings("all")
public class ChangeRoomPriceActivity extends AppCompatActivity {
    // Views
    TextView tv_price;
    EditText et_new_price, et_describe;
    Button btn_cancel, btn_confirm;

    // Values
    String room_no, price, new_price, describe;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // table: change price record (have reason change), change status record
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_room_price);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(ChangeRoomPriceActivity.this);



        // find Views
        tv_price = findViewById(R.id.price);
        et_new_price = findViewById(R.id.new_price);
        et_describe = findViewById(R.id.describe);
        btn_confirm = findViewById(R.id.btn_confirm);
        btn_cancel = findViewById(R.id.btn_cancel);



        // get intent: room_no, price
        Intent intent = getIntent();
        room_no = intent.getStringExtra("room_no");
        price = intent.getStringExtra("price");


        // setText of current price
        tv_price.setText(price);


        // button confirm
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // give value
//                describe = String.valueOf(et_describe.getText()); need to store in table record
                new_price = String.valueOf(et_new_price.getText());


                // change new price in database
                boolean success = dbHelper.updatePrice(room_no, new_price);


                // 根据数据库操作结果进行逻辑处理
                if (success) {
                    Toast.makeText(ChangeRoomPriceActivity.this, "Room price set to " + new_price, Toast.LENGTH_SHORT).show();
                    // 如果需要更新 UI 或执行其他操作，可以在这里添加代码
                    Intent intent = new Intent(ChangeRoomPriceActivity.this, ChangeRoomPriceSuccessfulActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangeRoomPriceActivity.this, "Failed to update room price", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ChangeRoomPriceActivity.this, ChangeRoomPriceFailedActivity.class);
                    startActivity(intent);
                }

            }
        });



        // button cancel
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangeRoomPriceActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
