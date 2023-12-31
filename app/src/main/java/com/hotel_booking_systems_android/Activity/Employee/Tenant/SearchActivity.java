package com.hotel_booking_systems_android.Activity.Employee.Tenant;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.DB.MyDatabaseHelper;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.bean.Tenant;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchContent;
    private ListView resultListView;
    private MyDatabaseHelper databaseHelper;
    private ArrayAdapter<String> resultAdapter;
    List<Tenant> tenants;
    ImageView back;
    ArrayList<String> titles = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchContent = findViewById(R.id.search_content);
        resultListView = findViewById(R.id.result_list_view);
        databaseHelper = new MyDatabaseHelper(this);

        // 初始化结果适配器
        resultAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        resultListView.setAdapter(resultAdapter);

        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 设置搜索文本监听器
        searchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 不需要实现
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 当文本改变时执行搜索
                performSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 不需要实现
            }

        });

        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 提取租户详细信息并将其传递给 EditTenantActivity
                Tenant tenant = tenants.get(position);
                Intent intent = new Intent(SearchActivity.this, EditTenantActivity.class);

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
    }
    private void performSearch(String query) {
        tenants = databaseHelper.searchData(query);
        resultAdapter.clear();

        for (Tenant tenant : tenants) {
            String resultText =
                    "Name: " + tenant.getName() + "\n" +
                    "IC: " + tenant.getIC() + "\n" +
                    "Contact Number: " + tenant.getContactNumber() + "\n" +
                    "Gmail: " + tenant.getGmail() + "\n" +
                    "Room ID: " + tenant.getRoomID() + "\n" +
                    "RoomType: " + tenant.getRoomType() + "\n" +
                    "Price: " + tenant.getRoomPrice() + "\n" +
                    "Checking Date: " + tenant.getCheckingDate() + "\n" +
                    "Checking Time: " + tenant.getCheckingTime() + "\n" +
                    "Checkout Date: " + tenant.getCheckoutDate() + "\n" +
                    "Checkout Time: " + tenant.getCheckoutTime();

            resultAdapter.add(resultText);
        }
    }
}
