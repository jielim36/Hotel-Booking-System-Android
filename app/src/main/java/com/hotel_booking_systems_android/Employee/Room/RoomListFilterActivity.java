package com.hotel_booking_systems_android.Employee.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hotel_booking_systems_android.R;

@SuppressWarnings("all")
public class RoomListFilterActivity extends AppCompatActivity {
    // Views
    private Button btn_cancel, btn_apply;

    // Check box
    CheckBox cb_single, cb_double, cb_family, cb_suite;
    EditText et_from_price, et_to_price;
    CheckBox cb_available, cb_maintain, cb_booked, cb_cleaning;
    CheckBox cb_floor1, cb_floor2, cb_floor3, cb_floor4;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list_filter);

        // find views
        // -- checkbox and edit text
        cb_single = findViewById(R.id.cb_single);
        cb_double = findViewById(R.id.cb_double);
        cb_family = findViewById(R.id.cb_family);
        cb_suite = findViewById(R.id.cb_suite);

        et_from_price = findViewById(R.id.from_price);
        et_to_price = findViewById(R.id.to_price);

        cb_available = findViewById(R.id.cb_available);
        cb_maintain = findViewById(R.id.cb_maintain);
        cb_booked = findViewById(R.id.cb_booked);
        cb_cleaning = findViewById(R.id.cb_cleaning);

        cb_floor1 = findViewById(R.id.cb_floor1);
        cb_floor2 = findViewById(R.id.cb_floor2);
        cb_floor3 = findViewById(R.id.cb_floor3);
        cb_floor4 = findViewById(R.id.cb_floor4);

        // --button
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_apply = findViewById(R.id.btn_apply);

        // cancel and back to previous page (room list)
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RoomListFilterActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RoomListFilterActivity.this, RoomListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // confirm and back to previous page (room list)
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // isChecked? (type)
                String[] types = {
                        ((cb_single.isChecked()) ? "'Single'" : "''"),
                        ((cb_double.isChecked()) ? "'Double'" : "''"),
                        ((cb_family.isChecked()) ? "'Family'" : "''"),
                        ((cb_suite.isChecked()) ? "'Suite'" : "''"),
                };
                String typeCondition = String.join(", ", types);


                // price range
                String from_price = String.valueOf(et_from_price.getText());
                String to_price = String.valueOf(et_to_price.getText());


                // isChecked? (status)
                String[] statuses = {
                        ((cb_available.isChecked()) ?
                                "'" + String.valueOf(RoomStatus.AVAILABLE) + "'" : "''"),
                        ((cb_booked.isChecked()) ?
                                "'" + String.valueOf(RoomStatus.BOOKED) + "'" : "''"),
                        ((cb_maintain.isChecked()) ?
                                "'" + String.valueOf(RoomStatus.MAINTENANCE) + "'" : "''"),
                        ((cb_cleaning.isChecked()) ?
                                "'" + String.valueOf(RoomStatus.CLEANING) + "'" : "''"),
                };
                String statusCondition = String.join(", ", statuses);


                // isChecked? (floor)
                String[] floors = {
                        ((cb_floor1.isChecked()) ? "'1'" : "''"),
                        ((cb_floor2.isChecked()) ? "'2'" : "''"),
                        ((cb_floor3.isChecked()) ? "'3'" : "''"),
                        ((cb_floor4.isChecked()) ? "'4'" : "''"),
                };
                String floorCondition = String.join(", ", floors);


                //
//                Toast.makeText(RoomListFilterActivity.this, statusCondition, Toast.LENGTH_SHORT).show();



                // Prepare intent to pass conditions to RoomListActivity
                Intent intent = new Intent(RoomListFilterActivity.this, RoomListActivity.class);
                intent.putExtra("typeCondition", typeCondition);
                intent.putExtra("fromPrice", from_price);
                intent.putExtra("toPrice", to_price);
                intent.putExtra("statusCondition", statusCondition);
                intent.putExtra("floorCondition", floorCondition);

                // Start RoomListActivity with conditions
                startActivity(intent);
                finish();

            }
        });
    }
}
