package com.hotel_booking_systems_android.Activity.Employee.Tenant;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.R;

public class HistoryTenantDetailActivity extends AppCompatActivity {

    EditText Id, name, ic, contactNumber, gmail;
    EditText roomID, roomType, roomPrice;
    EditText checkingDate, checkingTime, checkoutDate, checkoutTime;
    Button delete;
    ImageView back;
    MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_tenant_detail);

        Id = findViewById(R.id.id_et);
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

        delete = findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  new AlertDialog.Builder(HistoryTenantDetailActivity.this)
                          .setMessage("Are you sure to delete?")
                          .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  delete();
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
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        if (name != null) {
            Id.setText(ID);
            name.setText(Name);
            ic.setText(IC);
            contactNumber.setText(ContactNumber);
            gmail.setText(Gmail);
            roomID.setText(RoomID);
            roomType.setText(RoomType);
            roomPrice.setText(RoomPrice);
            checkingDate.setText(CheckingDate);
            checkingTime.setText(CheckingTime);
            checkoutDate.setText(CheckoutDate);
            checkoutTime.setText(CheckoutTime);
        }

    }
    public void delete() {
        try {
            String id = Id.getText().toString();
            SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
            database.beginTransaction();

            try {
                String deleteSql = "DELETE FROM HistoryData WHERE id = ?";
                SQLiteStatement statement = database.compileStatement(deleteSql);
                statement.bindString(1, id);
                statement.execute();
                Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show();

                // Clear the EditText fields
                
                Id.setText("");
                name.setText("");
                ic.setText("");
                contactNumber.setText("");
                gmail.setText("");
                roomID.setText("");
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
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(HistoryTenantDetailActivity.this, HistoryTenantActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}