package com.hotel_booking_systems_android.Employee.Tenant.AddTenant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.hotel_booking_systems_android.Employee.MyDatabaseHelper;
import com.hotel_booking_systems_android.Employee.Room.Room;
import com.hotel_booking_systems_android.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.R;

import java.util.ArrayList;
import java.util.List;

public class AddTenantActivity extends AppCompatActivity {
    EditText name, ic, contactNumber, gmail;
    EditText roomType, roomPrice;
    Spinner roomID;
    EditText checkingDate, checkingTime, checkoutDate, checkoutTime;
    Button save, reset;
    ImageView back;
    MyDatabaseHelper myDatabaseHelper;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);

        name = findViewById(R.id.name_et);
        ic = findViewById(R.id.ic_et);
        contactNumber = findViewById(R.id.contact_number_et);
        gmail = findViewById(R.id.gmail_et);
        roomID = findViewById(R.id.room_id_et);
        roomType = findViewById(R.id.population_et);
        roomType.setEnabled(false);
        roomType.setTextColor(Color.BLACK);
        roomPrice = findViewById(R.id.room_price_et);
        roomPrice.setEnabled(false);
        roomPrice.setTextColor(Color.BLACK);
        checkingDate = findViewById(R.id.checking_date_et);
        checkingTime = findViewById(R.id.checking_time_et);
        checkoutDate = findViewById(R.id.checkout_date_et);
        checkoutTime = findViewById(R.id.checkout_time_et);

        save = findViewById(R.id.save_btn);
        reset = findViewById(R.id.reset_btn);
        back = findViewById(R.id.back_btn);

        myDatabaseHelper = new MyDatabaseHelper(this);

        // 显示所有状态为空的房间在spinner上
        List<String> roomNumbers  = myDatabaseHelper.getAvailableRoomNumbers();
        ArrayAdapter<String> adapter;

        if (roomNumbers .isEmpty()) {
            roomNumbers = new ArrayList<>();
            roomNumbers.add("No room left");
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomNumbers );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomID.setAdapter(adapter);

        roomID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRoomId = parent.getItemAtPosition(position).toString();

                Room roomDetails = myDatabaseHelper.getRoomDetails(selectedRoomId);

                if (roomDetails != null) {
                    roomType.setText(roomDetails.getType());
                    roomPrice.setText(roomDetails.getPrice());
                } else {
                    // Handle the case where roomDetails is null
                    // For example, you could display a default message or clear the fields
                    roomType.setText("None");
                    roomPrice.setText("None");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle onNothingSelected event if needed
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
            // 调用insert()方法将租户数据保存到数据库
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setText("");
                ic.setText("");
                contactNumber.setText("");
                gmail.setText("");
                roomType.setText("");
                roomPrice.setText("");
                checkingDate.setText("");
                checkingTime.setText("");
                checkoutDate.setText("");
                checkoutTime.setText("");

                Toast.makeText(AddTenantActivity.this, "Reset Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // 将租户数据插入数据库的方法
    public void insert() {
        try {
            // 从输入字段中检索租户数据
            String Name = name.getText().toString();
            String IC = ic.getText().toString();
            String ContactNumber = contactNumber.getText().toString();
            String Gmail = gmail.getText().toString();
            String RoomID = roomID.getSelectedItem().toString();
            String RoomType = roomType.getText().toString();
            String RoomPrice = roomPrice.getText().toString();
            String CheckingDate = checkingDate.getText().toString();
            String CheckingTime = checkingTime.getText().toString();
            String CheckoutDate = checkoutDate.getText().toString();
            String CheckoutTime = checkoutTime.getText().toString();

            // 使用AddTenantVerify方法检查输入数据的有效性
            if (!AddTenantVerify(Name, IC, ContactNumber, Gmail, RoomID, RoomType, RoomPrice, CheckingDate, CheckingTime, CheckoutDate, CheckoutTime)) {
                return; // 如果数据无效，则返回，不保存
            }

            // 获取可写数据库
            SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();

            // 定义插入租户数据的SQL语句
            String sql = "INSERT INTO TenantData(Name, IC, ContactNumber, Gmail, RoomID, RoomType, RoomPrice, CheckingDate, CheckingTime, CheckoutDate, CheckoutTime) values(?,?,?,?,?,?,?,?,?,?,?)";

            // 编译SQL语句并绑定数据
            SQLiteStatement statement = database.compileStatement(sql);
            statement.bindString(1, Name);
            statement.bindString(2, IC);
            statement.bindString(3, ContactNumber);
            statement.bindString(4, Gmail);
            statement.bindString(5, RoomID);
            statement.bindString(6, RoomType);
            statement.bindString(7, RoomPrice);
            statement.bindString(8, CheckingDate);
            statement.bindString(9, CheckingTime);
            statement.bindString(10, CheckoutDate);
            statement.bindString(11, CheckoutTime);
            statement.execute(); // 执行SQL语句插入数据

            Toast.makeText(this, "Add Data Successful", Toast.LENGTH_SHORT).show();

            String selectedRoomId = roomID.getSelectedItem().toString();

            // 更新房间状态为BOOKED
            boolean isRoomStatusUpdated = myDatabaseHelper.updateStatus(selectedRoomId, RoomStatus.BOOKED.name());

            if (isRoomStatusUpdated) {
                Toast.makeText(this, "Room status updated to BOOKED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update room status", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(AddTenantActivity.this, AddTenantSuccessful.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // 验证租户数据正确性的方法
    public boolean AddTenantVerify(String Name, String IC, String ContactNumber, String Gmail,  String RoomID, String RoomType, String RoomPrice, String CheckingDate, String CheckingTime, String CheckoutDate, String CheckoutTime){
        // 个人资料检查
        if (Name.isEmpty() || IC.isEmpty() || ContactNumber.isEmpty() || Gmail.isEmpty()){
            Toast.makeText(this, "Tenant Detail can't be empty",Toast.LENGTH_SHORT).show();
            return false;
        } else if (RoomID.isEmpty() || RoomType.isEmpty() || RoomPrice.isEmpty()) {
            Toast.makeText(this, "Room Detail can't be empty",Toast.LENGTH_SHORT).show();
            return false;
        } else if (CheckingDate.isEmpty() || CheckingTime.isEmpty() || CheckoutDate.isEmpty() || CheckoutTime.isEmpty()) {
            Toast.makeText(this, "Check In And Check Out Detail can't be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        // 身份证检查
        String icRegex = "\\d{2}(0[1-9]|1[0-2])(0[1-9]|1\\d|2\\d|3[0-1])\\d{2}\\d{4}";
        if (!IC.matches(icRegex)) {
            Toast.makeText(this, "IC format is not correct!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 手机号码检查
        String phoneRegex = "^(01[0-9])?\\d{7,8}$";
        if (!ContactNumber.matches(phoneRegex)) {
            return false;
        }

        // 电邮地址检查
        String gmailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

        if (!Gmail.matches(gmailRegex)) {
            Toast.makeText(this, "Gmail format is not correct!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // 检查日期和时间
        String datePattern = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        String timePattern = "^(0[0-9]|1[0-9]|2[0-3]):(0[0-9]|[1-5][0-9])$";

        if(!CheckingDate.matches(datePattern) || !CheckoutDate.matches(datePattern)){
            Toast.makeText(this, "Date format is not correct!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!CheckingTime.matches(timePattern) || !CheckoutTime.matches(timePattern)){
            Toast.makeText(this, "Time format is not correct!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}