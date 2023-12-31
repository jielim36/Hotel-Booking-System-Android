package com.hotel_booking_systems_android.Activity.Employee.Tenant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Tenant;

import java.util.ArrayList;


public class HistoryTenantActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    ImageView back;
    MyDatabaseHelper myDatabaseHelper;
    ArrayList<Tenant> tenants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_tenant);

        myDatabaseHelper = new MyDatabaseHelper(this);

        listView = findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(arrayAdapter);

        historyList();

        back = findViewById(R.id.return_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String a = titles.get(position).toString();
                Tenant tenant = tenants.get(position);
                Intent intent = new Intent(HistoryTenantActivity.this, HistoryTenantDetailActivity.class);

                intent.putExtra("id",tenant.getId());
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
    }

    public void historyList() {
        titles.clear();
        tenants = new ArrayList<Tenant>();

        SQLiteDatabase database = myDatabaseHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from HistoryData", null);

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
                titles.add("Name :" + cursor.getString(Name) + "\n" + "IC :" + cursor.getString(IC) + "\n" +
                        "Contact Number :" + cursor.getString(ContactNumber) + "\n" + "Gmail :" + cursor.getString(Gmail) + "\n" +
                        "Room ID :" + cursor.getString(RoomID) + "\n" + "RoomType :" + cursor.getString(RoomType) + "\n" +
                        "Price :" + cursor.getString(RoomPrice) + "\n" + "Checking Date :" + cursor.getString(CheckingDate) + "\n" +
                        "Checking Time :" + cursor.getString(CheckingTime) + "\n" + "Checkout Date :" + cursor.getString(CheckoutDate) + "\n" +
                        "Checkout Time :" + cursor.getString(CheckoutTime) + "\n");
            } while (cursor.moveToNext());

            // 通知ArrayAdapter数据发生变化
            arrayAdapter.notifyDataSetChanged();
            listView.invalidateViews();
            cursor.close();
        }
    }
}