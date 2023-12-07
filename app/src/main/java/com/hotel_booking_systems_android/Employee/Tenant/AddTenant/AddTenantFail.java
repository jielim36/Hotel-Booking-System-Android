package com.hotel_booking_systems_android.Employee.Tenant.AddTenant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.R;


public class AddTenantFail extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant_fail);

        findViewById(R.id.btn_done).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AddTenantFail.this,AddTenantActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
