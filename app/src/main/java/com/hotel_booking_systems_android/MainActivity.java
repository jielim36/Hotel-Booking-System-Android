package com.hotel_booking_systems_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel_booking_systems_android.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private Button loginAndLogout_btn;//include logout function
    private TextView username_tv;
    private Intent loginIntent;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMember();
        initializeEvent();
        initializeLoginState();

    }

    public void initializeLoginState(){

        if (sp.getBoolean("autoLogin",false)){
            Toast.makeText(MainActivity.this,"Auto Login!",Toast.LENGTH_SHORT).show();
            //set username
            String username = sp.getString("username", "-");
            username_tv.setText(username);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("isLogin",true);
            edit.apply();
        }
        //login_btn content and function is depending by isLogin boolean value
        Log.e("login","isLogin:" + sp.getBoolean("isLogin",false));
        if (sp.getBoolean("isLogin",false)){
            loginAndLogout_btn.setText("Logout");
            String username = sp.getString("username", "-");
            username_tv.setText(username);
        }else {
            loginAndLogout_btn.setText("Login");
        }

    }

    public void initializeMember(){
        loginAndLogout_btn = findViewById(R.id.loginAndLogout_btn);
        loginIntent = new Intent(MainActivity.this , LoginActivity.class);
        username_tv = findViewById(R.id.username_tv);
        sp = getSharedPreferences("account", Context.MODE_PRIVATE);

    }

    public void initializeEvent(){
        loginAndLogout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp.getBoolean("isLogin",false)){
                    logoutProcess();
                }else {
                    startActivity(loginIntent);
                }
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
        initializeLoginState();//reload login state
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isLogin",false);
        edit.apply();
    }
}
