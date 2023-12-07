package com.hotel_booking_systems_android.Employee.Room.AddRoom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;

@SuppressWarnings("all")
public class AddRoomStep1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room_step1);

        // Delcare View
        // Button
        Button btn_back, btn_next;

        // EditText
        EditText e1, e2, e4, e5;
        Spinner e3;

        // initialize
        btn_back = findViewById(R.id.btn_back);
        btn_next = findViewById(R.id.btn_next);
        e1 = findViewById(R.id.step1_room_no);
        e2 = findViewById(R.id.step1_price);
        e3 = findViewById(R.id.step1_type);
        e4 = findViewById(R.id.step1_floor_no);
        e5 = findViewById(R.id.step1_max_people);

        // adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.room_type_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        e3.setAdapter(adapter);



        // go to other pages
        // 1. back to room list
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //room_no.isEmpty() false
                // string
                String room_no, price, type, floor_no, max_people;
                room_no = e1.getText().toString();
                price = e2.getText().toString();
                type = e3.getSelectedItem().toString();
                floor_no = e4.getText().toString();
                max_people = e5.getText().toString();


                if (!room_no.isEmpty() || !price.isEmpty() || !type.isEmpty()
                        || !floor_no.isEmpty() || !max_people.isEmpty()) { // any field have content will no cancel

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddRoomStep1Activity.this);
                    builder.setMessage("Are you sure to quit? Input will not save.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // 用户点击了"Yes"按钮，执行退出操作
                                    Toast.makeText(AddRoomStep1Activity.this, "back to room list", Toast.LENGTH_SHORT).show();
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

//                    Toast.makeText(AddRoomStep1Activity.this, "input will not save!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddRoomStep1Activity.this, "back to room list", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

        // 2. go to add room step 2
        /*
                Type        Price      people
               Single     91 ~ 120       1~2
               Double     121 ~ 150      2~3
               Family     151 ~ 200      3~6
               Suite      201 ~ 300      6~10
         */
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String room_no, price, type, floor_no, max_people;

                // string
                room_no = e1.getText().toString();
                price = e2.getText().toString();
                type = e3.getSelectedItem().toString();
                floor_no = e4.getText().toString();
                max_people = e5.getText().toString();

                if (!room_no.isEmpty() && !price.isEmpty() && !type.isEmpty()
                        && !floor_no.isEmpty() && !max_people.isEmpty()) {

                    boolean isFloorLogic = room_no.toCharArray()[0] == floor_no.toCharArray()[0];
                    boolean isTypeLogic = checkTypeLogic(type, Double.parseDouble(price), Integer.parseInt(max_people));

                    if (isFloorLogic && isTypeLogic) {
                        Toast.makeText(AddRoomStep1Activity.this, "go to step 2", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddRoomStep1Activity.this, AddRoomStep2Activity.class);

                        intent.putExtra("room_no", room_no);
                        intent.putExtra("price", price);
                        intent.putExtra("type", type);
                        intent.putExtra("floor_no", floor_no);
                        intent.putExtra("max_people", max_people);

                        startActivity(intent);

                    } else {
                        Toast.makeText(AddRoomStep1Activity.this, "Check your logic please.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e("TAG-room_no", room_no);
                    Log.e("TAG-price", price);
                    Log.e("TAG-type", type);
                    Log.e("TAG-floor_no", floor_no);
                    Log.e("TAG-max_people", max_people);
                    Toast.makeText(AddRoomStep1Activity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public static boolean checkTypeLogic(String type, double price, int people) {
        int typeNo = 0;
        if (type.equals("Single")) {
            typeNo = 1;
        } else if (type.equals("Double")) {
            typeNo = 2;
        } else if (type.equals("Family")) {
            typeNo = 3;
        } else { //Suite
            typeNo = 4;
        }

        switch (typeNo) {
            case 1:
                if ((price > 90 && price <= 120) && (people >= 1 && people <= 2)) {
                    return true;
                }
                break;

            case 2:
                if ((price > 120 && price <= 150) && (people >= 2 && people <= 3)) {
                    return true;
                }
                break;

            case 3:
                if ((price > 150 && price <= 200) && (people >= 3 && people <= 6)) {
                    return true;
                }
                break;

            case 4:
                if ((price > 200 && price <= 300) && (people >= 6 && people <= 10)) {
                    return true;
                }
                break;


        }


        return false;
    }




}
