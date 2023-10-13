package com.hotel_booking_systems_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel_booking_systems_android.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button login_btn;
    private Button logout_btn;
    private TextView username_tv;
    private Intent loginIntent;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMember();
        initializeEvent();

        if (sp.getBoolean("isLogin",false)){
            Toast.makeText(MainActivity.this,"Login Successfully!",Toast.LENGTH_SHORT).show();
        }

    }

    public void initializeMember(){
        login_btn = findViewById(R.id.login_btn);
        logout_btn = findViewById(R.id.logout_btn);
        loginIntent = new Intent(MainActivity.this , LoginActivity.class);
        username_tv = findViewById(R.id.username_tv);
        sp = getSharedPreferences("account", Context.MODE_PRIVATE);

        //set username
        String username = sp.getString("username", "-");
        username_tv.setText(username);
    }

    public void initializeEvent(){
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getBoolean("isLogin",false)){
                    Toast.makeText(MainActivity.this,"Login Already!",Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(loginIntent);
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutProcess();
            }
        });
    }

    public void logoutProcess(){
        Toast.makeText(MainActivity.this,"Logout!",Toast.LENGTH_SHORT).show();

        username_tv.setText("-");
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin",false);
        editor.putBoolean("autoLogin",false);
        editor.apply();
    }

}
