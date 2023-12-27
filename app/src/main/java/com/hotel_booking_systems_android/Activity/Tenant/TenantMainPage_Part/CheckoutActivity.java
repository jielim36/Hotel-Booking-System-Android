package com.hotel_booking_systems_android.Activity.Tenant.TenantMainPage_Part;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.Activity.Employee.Room.Room;
import com.hotel_booking_systems_android.Activity.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.DB.ItemDatabaseHelper;
import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.DB.TenantRoomDatabaseHelper;
import com.hotel_booking_systems_android.MainActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Item;
import com.hotel_booking_systems_android.bean.TenantRoom;
import com.hotel_booking_systems_android.custom.ItemAdapter;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    ImageButton instructions_dropDown_btn;
    ImageButton amount_dropDown_btn;
    ImageButton back_btn;
    Button checkout_btn;
    TextView instructionContent;
    TextView checkout_amount_tv;
    boolean instructionDropdown = false;
    boolean amountDetailsDropdown = false;
    List<Item> unpaidItemListByUserId;
    ItemDatabaseHelper itemDBHelper;
    TenantRoomDatabaseHelper tenantRoomDatabaseHelper;
    double totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        initializeComponent();
        initializeEvent();
    }

    private void initializeComponent() {
        instructions_dropDown_btn = findViewById(R.id.checkout_instructions_dropDown_btn);
        amount_dropDown_btn = findViewById(R.id.checkout_amount_details_dropDown_btn);
        instructionContent = findViewById(R.id.checkout_instructions_content);
        back_btn = findViewById(R.id.checkout_back_btn);
        checkout_btn = findViewById(R.id.checkout_confirm_btn);
        itemDBHelper = new ItemDatabaseHelper(getApplicationContext());
        tenantRoomDatabaseHelper = new TenantRoomDatabaseHelper(getApplicationContext());
        checkout_amount_tv = findViewById(R.id.checkout_amount);

        Integer userId = AccountSharedPreferences.getInstance(getApplicationContext()).getUserId();
        unpaidItemListByUserId = itemDBHelper.getUnpaidItemByUserId(userId);

        for(Item item : unpaidItemListByUserId){
            totalAmount += item.getTotalAmount();
            Log.e("amount", "Item: " + item.getTotalAmount());
        }
        checkout_amount_tv.setText(String.valueOf(totalAmount));
    }

    private void initializeEvent(){

        instructions_dropDown_btn.setOnClickListener(v -> {
            instructionDropdown = !instructionDropdown;
            if (instructionDropdown){
                instructionContent.setVisibility(TextView.VISIBLE);
            }else {
                instructionContent.setVisibility(TextView.GONE);
            }
        });

        amount_dropDown_btn.setOnClickListener(v -> {
            Intent intent = new Intent(CheckoutActivity.this,AmountDetailsActivity.class);
            startActivity(intent);
        });

        back_btn.setOnClickListener(v -> {
            onBackPressed();//go back to the previous page
        });

        checkout_btn.setOnClickListener(v -> {
            showConfirmationDialog();
        });
    }

    private void showConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 设置对话框标题
        builder.setTitle("Confirm Checkout Dialog");

        // 设置对话框消息
        builder.setMessage("Total Amount:" + totalAmount);

        //定义Booking Success后跳转到Tenant Page
        Intent intent = new Intent(this, MainActivity.class);
        // 设置确认按钮和点击事件
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Checkout Success", Toast.LENGTH_SHORT).show();
                Integer userId = AccountSharedPreferences.getInstance(getApplicationContext()).getUserId();
                //get unpaid room by userid
                List<TenantRoom> unpaidRoomByUser = tenantRoomDatabaseHelper.getTenantRoomsByUserIdAndStatus(userId, TenantRoom.Status.CHECKED_IN);
                //update room status according by room no
                MyDatabaseHelper roomDBHelper = new MyDatabaseHelper(getApplicationContext());
                for(TenantRoom tenantRoom : unpaidRoomByUser){
                    roomDBHelper.updateStatus(String.valueOf(tenantRoom.getRoomId()) , RoomStatus.AVAILABLE.toString());
                }

                //update tenant room relation table status
                tenantRoomDatabaseHelper.updateTenantRoomStatusByUserId(userId , TenantRoom.Status.CHECKED_OUT);
                itemDBHelper.checkoutUnpaidItems(unpaidItemListByUserId);

                //update isBooking state to prevent user that are no booking still can access tenant main fragment page
                AccountSharedPreferences.getInstance(getApplicationContext()).setBooking(false);

                intent.putExtra("fragmentToShow", "tenant");
                startActivity(intent);
            }
        });

        // 设置取消按钮和点击事件
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 在这里处理取消按钮点击后的逻辑
                // 例如，不执行任何操作或显示一个提示消息
                Toast.makeText(getApplicationContext(), "Booking Cancel", Toast.LENGTH_SHORT).show();
            }
        });

        // 创建并显示AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}