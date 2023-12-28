package com.hotel_booking_systems_android.Activity.Tenant.Start_Booking_Part;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hotel_booking_systems_android.Activity.Employee.Room.Room;
import com.hotel_booking_systems_android.Activity.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.DB.ItemDatabaseHelper;
import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.DB.TenantRoomDatabaseHelper;
import com.hotel_booking_systems_android.MainActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Item;
import com.hotel_booking_systems_android.bean.TenantRoom;
import com.hotel_booking_systems_android.custom.RoomAdapter;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class ActivityDoubleRoom extends AppCompatActivity {

    ListView roomListView;
    RoomAdapter roomAdapter;
    MyDatabaseHelper roomDBManager;
    List<Room> targetRoomList;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_room);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize
        roomListView = findViewById(R.id.double_room_list); // Make sure to use the correct ListView ID
        roomDBManager = new MyDatabaseHelper(this);
        sp = getSharedPreferences("account", MODE_PRIVATE);

        showRoomList();

        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(targetRoomList.get(position).getStatus() != RoomStatus.BOOKED){
                    showConfirmationDialog(targetRoomList.get(position));
                }
            }
        });
    }

    private void showConfirmationDialog(Room targetRoom) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 设置对话框标题
        builder.setTitle("Confirm Dialog");

        // 设置对话框消息
        builder.setMessage("Confirm Booking " + targetRoom.getRoom_no() + "?");

        //定义Booking Success后跳转到Tenant Page
        Intent intent = new Intent(this, MainActivity.class);
        // 设置确认按钮和点击事件
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Booking Success", Toast.LENGTH_SHORT).show();

                //get item database helper
                ItemDatabaseHelper itemDatabaseHelper = new ItemDatabaseHelper(getApplicationContext());
                String itemName = targetRoom.getType() + "("+ targetRoom.getRoom_no() +")";
                Integer userId = AccountSharedPreferences.getInstance(getApplicationContext()).getUserId();

                //save checkout record
                Item item = new Item(null, userId , itemName , Double.parseDouble(targetRoom.getPrice()),1 , Item.Status.UNPAID);
                itemDatabaseHelper.addItem(item);

                //change room status
                MyDatabaseHelper roomDBHelper = new MyDatabaseHelper(getApplicationContext());
                roomDBHelper.updateStatus(targetRoom.getRoom_no() , RoomStatus.BOOKED.toString());

                //record user and room relation for checkout
                TenantRoomDatabaseHelper tenantRoomDatabaseHelper = new TenantRoomDatabaseHelper(getApplicationContext());
                tenantRoomDatabaseHelper.addTenantRoom(userId , Integer.parseInt(targetRoom.getRoom_no()) , TenantRoom.Status.CHECKED_IN);

                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isBooking",true);
                editor.apply();
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

    private void showRoomList(){
        targetRoomList = new ArrayList<>();
        List<Room> allRooms = roomDBManager.getAllRooms();
        for(Room room : allRooms){
            if(room.getType().equals("Double")){ // Modify the room type
                targetRoomList.add(room);
            }
        }

        // Initialize RoomAdapter
        roomAdapter = new RoomAdapter(this, targetRoomList);
        // Set Adapter
        roomListView.setAdapter(roomAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
