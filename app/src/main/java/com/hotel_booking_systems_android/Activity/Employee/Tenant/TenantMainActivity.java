package com.hotel_booking_systems_android.Activity.Employee.Tenant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.Activity.Employee.Room.RoomListActivity;
import com.hotel_booking_systems_android.Activity.Employee.Tenant.AddTenant.AddTenantActivity;
import com.hotel_booking_systems_android.MainActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Tenant;

import java.util.ArrayList;

public class TenantMainActivity extends AppCompatActivity {
    ListView listView; // 这是一个用于显示项目列表的 UI 组件
    ArrayList<String> titles = new ArrayList<String>(); // 用于存储要显示的租户详细信息列表
    ArrayAdapter arrayAdapter; // 将数据绑定到ListView的适配器

    Button addButton, historyButton;
    ImageView search, back_btn, roomlist, profile;
    MyDatabaseHelper myDatabaseHelper; // 管理数据库的辅助类
    ArrayList<Tenant> tenants; // 保存租户对象的列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_main);

        // 初始化数据库助手并获取可写数据库
        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();

        // 初始化ListView和ArrayAdapter以显示租户详细信息
        listView = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);

        // 初始化一个列表来保存租户对象
        tenants = new ArrayList<Tenant>();

        // 它初始化 UI 组件、数据库帮助程序并设置事件侦听器。

        // 处理“添加租户”按钮单击以导航到 AddTenantActivity
        addButton = findViewById(R.id.add_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TenantMainActivity.this, AddTenantActivity.class);
                startActivity(intent);
            }
        });

        search = findViewById(R.id.search_icon);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantMainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // 调用refreshTenantList()，用数据库中的数据填充标题和租户ArrayList，并将其显示在listView 中。
        refreshTenantList();

        // 处理ListView上item点击查看/编辑租户详细信息
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 提取租户详细信息并将其传递给 EditTenantActivity
                String a = titles.get(position).toString();
                Tenant tenant = tenants.get(position);
                Intent intent = new Intent(TenantMainActivity.this, EditTenantActivity.class);

                // 将租户详细信息作为附加信息传递给下一个活动
                intent.putExtra("id", tenant.getId());
                intent.putExtra("Name", tenant.getName());
                intent.putExtra("IC", tenant.getIC());
                intent.putExtra("ContactNumber", tenant.getContactNumber());
                intent.putExtra("Gmail", tenant.getGmail());
                intent.putExtra("RoomID", tenant.getRoomID());
                intent.putExtra("RoomType", tenant.getRoomType());
                intent.putExtra("RoomPrice", tenant.getRoomPrice());
                intent.putExtra("CheckingDate", tenant.getCheckingDate());
                intent.putExtra("CheckingTime", tenant.getCheckingTime());
                intent.putExtra("CheckoutDate", tenant.getCheckoutDate());
                intent.putExtra("CheckoutTime", tenant.getCheckoutTime());


                startActivity(intent);
            }
        });

        historyButton = findViewById(R.id.history_btn);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantMainActivity.this, HistoryTenantActivity.class);
                startActivity(intent);
            }
        });
        roomlist = findViewById(R.id.room_list_btn);
        roomlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TenantMainActivity.this, RoomListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
//
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        back_btn = findViewById(R.id.tenant_returnHome_btn);
        back_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    // 从数据库刷新租户列表的方法
    public void refreshTenantList() {
        titles.clear(); // 清除现有数据
        tenants.clear(); // 清空租户列表

        SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from TenantData", null); // 使用查询和游标从数据库检索数据

        // 定义光标的列索引
        int id = cursor.getColumnIndex("id");
        int Name = cursor.getColumnIndex("Name");
        int IC = cursor.getColumnIndex("IC");
        int ContactNumber = cursor.getColumnIndex("ContactNumber");
        int Gmail = cursor.getColumnIndex("Gmail");
        int RoomID = cursor.getColumnIndex("RoomID");
        int RoomType = cursor.getColumnIndex("RoomType");
        int RoomPrice = cursor.getColumnIndex("RoomPrice");
        int CheckingDate = cursor.getColumnIndex("CheckingDate");
        int CheckingTime = cursor.getColumnIndex("CheckingTime");
        int CheckoutDate = cursor.getColumnIndex("CheckoutDate");
        int CheckoutTime = cursor.getColumnIndex("CheckoutTime");

        if (cursor.moveToFirst()) {
            do {
                // 创建一个 Tenant 对象并用游标中的数据填充它
                Tenant tenant = new Tenant();
                tenant.setId(cursor.getString(id));
                tenant.setName(cursor.getString(Name));
                tenant.setIC(cursor.getString(IC));
                tenant.setContactNumber(cursor.getString(ContactNumber));
                tenant.setGmail(cursor.getString(Gmail));
                tenant.setRoomID(cursor.getString(RoomID));
                tenant.setRoomType(cursor.getString(RoomType));
                tenant.setRoomPrice(cursor.getString(RoomPrice));
                tenant.setCheckingDate(cursor.getString(CheckingDate));
                tenant.setCheckingTime(cursor.getString(CheckingTime));
                tenant.setCheckoutDate(cursor.getString(CheckoutDate));
                tenant.setCheckoutTime(cursor.getString(CheckoutTime));

                tenants.add(tenant);

                // 将Tenant对象添加到列表中，并将相应的显示字符串添加到标题中
                titles.add("Name :"+cursor.getString(Name) +"\n"+ "IC :" + cursor.getString(IC) +"\n"+
                        "Contact Number :" + cursor.getString(ContactNumber) +"\n"+ "Gmail :" + cursor.getString(Gmail) +"\n"+
                        "Room ID :" + cursor.getString(RoomID) +"\n"+ "Type :" + cursor.getString(RoomType) +"\n"+
                        "Price :" + cursor.getString(RoomPrice) +"\n"+ "Checking Date :" + cursor.getString(CheckingDate) +"\n"+
                        "Checking Time :" + cursor.getString(CheckingTime) +"\n"+ "Checkout Date :" + cursor.getString(CheckoutDate) +"\n"+
                        "Checkout Time :" + cursor.getString(CheckoutTime)+"\n");
            } while (cursor.moveToNext());

            // 通知ArrayAdapter数据发生变化
            arrayAdapter.notifyDataSetChanged();
            listView.invalidateViews();
            cursor.close();
        }
    }
}