package com.hotel_booking_systems_android.Activity.Employee.Room.AddRoom;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;


@SuppressWarnings("all")
public class AddRoomStep2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room_step2);

        // Delcare View
        // Button
        Button btn_back, btn_next;

        // EditText
        EditText e6;

        // initialize
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        e6 = findViewById(R.id.describe);



        // go to other pages
        // 1. back to step 1
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!e6.toString().isEmpty()) { // any field have content will no cancel

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddRoomStep2Activity.this);
                    builder.setMessage("Are you sure to back step 1? Input will not save.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // 用户点击了"Yes"按钮，执行退出操作
                                    Toast.makeText(AddRoomStep2Activity.this, "back to step 1", Toast.LENGTH_SHORT).show();
                                    finish(); // 关闭当前Activity
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // 用户点击了"No"按钮，取消对话框，不执行任何操作
                                    dialog.dismiss();
                                }
                            });
                    // 创建并显示对话框
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(AddRoomStep2Activity.this, "back to step 1", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        // 2. go step 3
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // string
                String room_no, price, type, floor_no, max_people, describe;

                // get values from inputs
                Intent values = getIntent();
                room_no = values.getStringExtra("room_no");
                price = values.getStringExtra("price");
                type = values.getStringExtra("type");
                floor_no = values.getStringExtra("floor_no");
                max_people = values.getStringExtra("max_people");
                describe = e6.getText().toString();

                //
                if (!room_no.isEmpty() && !price.isEmpty() && !type.isEmpty()
                        && !floor_no.isEmpty() && !max_people.isEmpty() && !describe.isEmpty()) {
                    Toast.makeText(AddRoomStep2Activity.this, "go to step 3", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddRoomStep2Activity.this, AddRoomStep3Activity.class);

                    intent.putExtra("room_no", room_no);
                    intent.putExtra("price", price);
                    intent.putExtra("type", type);
                    intent.putExtra("floor_no", floor_no);
                    intent.putExtra("max_people", max_people);
                    intent.putExtra("describe", describe);
                    // 传images的集合

                    startActivity(intent);

                } else {
                    Toast.makeText(AddRoomStep2Activity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
