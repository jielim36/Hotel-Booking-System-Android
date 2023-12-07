package com.hotel_booking_systems_android.Employee.Tenant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

public class EditTenantActivity extends AppCompatActivity {

    EditText Id, name, ic, contactNumber, gmail;
    EditText roomType, roomPrice;
    Spinner roomID;
    EditText currentRoomID,checkingDate, checkingTime, checkoutDate, checkoutTime;
    Button edit, reset, delete;
    ImageView back;
    MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tenant);

        Id = findViewById(R.id.id_et);
        Id.setEnabled(false);
        Id.setTextColor(Color.BLACK);
        name = findViewById(R.id.name_et);
        ic = findViewById(R.id.ic_et);
        contactNumber = findViewById(R.id.contact_number_et);
        gmail = findViewById(R.id.gmail_et);
        currentRoomID = findViewById(R.id.current_room_id);
        currentRoomID.setEnabled(false);
        currentRoomID.setTextColor(Color.BLACK);
        roomID = findViewById(R.id.room_id_et);
        roomType = findViewById(R.id.population_et);
        roomPrice = findViewById(R.id.room_price_et);
        checkingDate = findViewById(R.id.checking_date_et);
        checkingTime = findViewById(R.id.checking_time_et);
        checkoutDate = findViewById(R.id.checkout_date_et);
        checkoutTime = findViewById(R.id.checkout_time_et);

        edit = findViewById(R.id.edit_btn);
        reset = findViewById(R.id.reset_btn);
        delete = findViewById(R.id.delete_btn);
        back = findViewById(R.id.back_btn);

        // 使用 Intent 从之前的活动中检索租户详细信息
        Intent intent = getIntent();

        String ID = intent.getStringExtra("id");
        String Name = intent.getStringExtra("Name");
        String IC = intent.getStringExtra("IC");
        String ContactNumber = intent.getStringExtra("ContactNumber");
        String Gmail = intent.getStringExtra("Gmail");
        String RoomID = intent.getStringExtra("RoomID");
        String RoomType = intent.getStringExtra("RoomType");
        String RoomPrice = intent.getStringExtra("RoomPrice");
        String CheckingDate = intent.getStringExtra("CheckingDate");
        String CheckingTime = intent.getStringExtra("CheckingTime");
        String CheckoutDate = intent.getStringExtra("CheckoutDate");
        String CheckoutTime = intent.getStringExtra("CheckoutTime");

        Id.setText(ID);
        name.setText(Name);
        ic.setText(IC);
        contactNumber.setText(ContactNumber);
        gmail.setText(Gmail);
        currentRoomID.setText(RoomID);
        roomType.setText(RoomType);
        roomPrice.setText(RoomPrice);
        checkingDate.setText(CheckingDate);
        checkingTime.setText(CheckingTime);
        checkoutDate.setText(CheckoutDate);
        checkoutTime.setText(CheckoutTime);
        // 使用检索到的租户数据填充 UI 字段

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
                    roomType.setText("None");
                    roomPrice.setText("None");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditTenantActivity.this)
                        .setMessage("Are you sure to check out?")
                        .setPositiveButton("Check Out", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                checkout();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 用户点击取消按钮后的操作
                                dialog.dismiss(); // 关闭对话框
                            }
                        })
                        .show();
            }
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
                new AlertDialog.Builder(EditTenantActivity.this)
                        .setMessage("Are you sure to reset?")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                name = findViewById(R.id.name_et);
                                ic = findViewById(R.id.ic_et);
                                contactNumber = findViewById(R.id.contact_number_et);
                                gmail = findViewById(R.id.gmail_et);
                                roomID = findViewById(R.id.room_id_et);
                                roomType = findViewById(R.id.population_et);
                                roomPrice = findViewById(R.id.room_price_et);
                                checkingDate = findViewById(R.id.checking_date_et);
                                checkingTime = findViewById(R.id.checking_time_et);
                                checkoutDate = findViewById(R.id.checkout_date_et);
                                checkoutTime = findViewById(R.id.checkout_time_et);

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

                                Toast.makeText(EditTenantActivity.this, "Reset Successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 用户点击取消按钮后的操作
                                dialog.dismiss(); // 关闭对话框
                            }
                        })
                        .show();
            }
        });
    }

    public void edit() {
        try {
            String id = Id.getText().toString();
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

            // 在继续之前验证数据
            if (!AddTenantVerify(Name, IC, ContactNumber, Gmail, RoomID, RoomType, RoomPrice, CheckingDate, CheckingTime, CheckoutDate, CheckoutTime)) {
                return;
            }
            // 创建一个确认对话框
            new AlertDialog.Builder(this)
                .setMessage("Are you sure want to save changes?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveChanges();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户点击取消按钮后的操作
                        dialog.dismiss(); // 关闭对话框
                    }
                })
                .show();

        } catch (Exception exception) {
            // 处理异常
            Toast.makeText(this, "Edit Data Failed", Toast.LENGTH_SHORT).show();

        }
    }
    private void saveChanges() {
        // 先获取编辑后的数据
        String id = Id.getText().toString();
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

        // 在继续之前验证数据
        if (!AddTenantVerify(Name, IC, ContactNumber, Gmail, RoomID, RoomType, RoomPrice, CheckingDate, CheckingTime, CheckoutDate, CheckoutTime)) {
            return;
        }

        SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
        database.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put("ID", id);
            values.put("Name", Name);
            values.put("IC", IC);
            values.put("ContactNumber", ContactNumber);
            values.put("Gmail", Gmail);
            values.put("RoomID", RoomID);
            values.put("RoomType", RoomType);
            values.put("RoomPrice", RoomPrice);
            values.put("CheckingDate", CheckingDate);
            values.put("CheckingTime", CheckingTime);
            values.put("CheckoutDate", CheckoutDate);
            values.put("CheckoutTime", CheckoutTime);

            String selection = "id = ?";
            String[] selectionArgs = {id};

            // 更新数据库中的租户数据
            int count = database.update("TenantData", values, selection, selectionArgs);

            if (count > 0) {
                Toast.makeText(this, "Edit Data Successful", Toast.LENGTH_SHORT).show();

                // 设置编辑界面上的文本字段的值
                Id.setText(id);
                name.setText(Name);
                ic.setText(IC);
                contactNumber.setText(ContactNumber);
                gmail.setText(Gmail);
                roomType.setText(RoomType);
                roomPrice.setText(RoomPrice);
                checkingDate.setText(CheckingDate);
                checkingTime.setText(CheckingTime);
                checkoutDate.setText(CheckoutDate);
                checkoutTime.setText(CheckoutTime);

                // 将焦点设置到适当的字段
                name.requestFocus();
            } else {
                Toast.makeText(this, "Edit Data Failed", Toast.LENGTH_SHORT).show();
            }

            // 设置数据交易成功并完成
            database.setTransactionSuccessful();
        } finally {
            // 结束事务并关闭数据库
            database.endTransaction();
        }

        String selectedRoomId = roomID.getSelectedItem().toString();
        String currentRoomId = currentRoomID.getText().toString();

        // 将更换的房间更新房间状态为BOOKED，将旧的房间更新状态为AVAILABLE
        boolean isRoomStatusUpdated = myDatabaseHelper.updateStatus(selectedRoomId, RoomStatus.BOOKED.name());
        boolean oldRoomStatusUpdated = myDatabaseHelper.updateStatus(currentRoomId,RoomStatus.AVAILABLE.name());

        if (isRoomStatusUpdated && oldRoomStatusUpdated) {
            Toast.makeText(this, "Room status updated to BOOKED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update room status", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent( EditTenantActivity.this, TenantMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

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

    @SuppressLint("Range")
    public void checkout() {
        try {
            String id = Id.getText().toString();
            SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
            database.beginTransaction();

            try {
                String sql = "select * from TenantData where id = ?";
                Cursor cursor = database.rawQuery(sql,new String[]{id});
                if (cursor.moveToFirst()) {
                    ContentValues values = new ContentValues();
                    values.put("ID",cursor.getString(cursor.getColumnIndex("id")));
                    values.put("Name", cursor.getString(cursor.getColumnIndex("Name")));
                    values.put("IC", cursor.getString(cursor.getColumnIndex("IC")));
                    values.put("ContactNumber", cursor.getString(cursor.getColumnIndex("ContactNumber")));
                    values.put("Gmail", cursor.getString(cursor.getColumnIndex("Gmail")));
                    values.put("RoomID", cursor.getString(cursor.getColumnIndex("RoomID")));
                    values.put("RoomPrice", cursor.getString(cursor.getColumnIndex("RoomPrice")));
                    values.put("RoomType", cursor.getString(cursor.getColumnIndex("RoomType")));
                    values.put("CheckingDate", cursor.getString(cursor.getColumnIndex("CheckingDate")));
                    values.put("CheckingTime", cursor.getString(cursor.getColumnIndex("CheckingTime")));
                    values.put("CheckoutDate", cursor.getString(cursor.getColumnIndex("CheckoutDate")));
                    values.put("CheckoutTime", cursor.getString(cursor.getColumnIndex("CheckoutTime")));

                    database.insert("historyData",null,values);
                }
                cursor.close();
                String deleteSql = "DELETE FROM TenantData WHERE id = ?";
                SQLiteStatement statement = database.compileStatement(deleteSql);
                statement.bindString(1, id);
                statement.execute();
                Toast.makeText(this, "Check Out Successful", Toast.LENGTH_SHORT).show();

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
                name.requestFocus();

                database.setTransactionSuccessful();
                finish();
            } finally {
                database.endTransaction();
            }
        } catch (Exception exception) {
            Toast.makeText(this, "Check Out Failed", Toast.LENGTH_SHORT).show();
        }

        // 当退房时，更改房间为清洁状态
        String selectedRoomID = currentRoomID.getText().toString();
        boolean RoomStatusUpdate = myDatabaseHelper.updateStatus(selectedRoomID,RoomStatus.CLEANING.name());
        if (RoomStatusUpdate) {
            Toast.makeText(this, "Room status updated to BOOKED", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update room status", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent( EditTenantActivity.this, TenantMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}