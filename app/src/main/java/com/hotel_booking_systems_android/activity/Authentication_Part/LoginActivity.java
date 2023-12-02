package com.hotel_booking_systems_android.activity.Authentication_Part;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel_booking_systems_android.MainActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.room.User.User;
import com.hotel_booking_systems_android.room.manager.UserDBEngine;


public class LoginActivity extends AppCompatActivity {

    private TextView home_tv;
    private TextView register_tv;
    private Intent homeActivity;
    private Intent registerActivity;
    private EditText username_et;
    private EditText password_et;
    private Button userLogin_btn;
    private Button staffLogin_btn;
    private CheckBox rememberMe_cb;
    private CheckBox autoLogin_cb;
    private UserDBEngine userDBEngine;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeMember();
        initializeEvent();
        if (sp.getBoolean("isLogin",false)){
            Toast.makeText(LoginActivity.this,"Login Successfully!",Toast.LENGTH_SHORT).show();
            startActivity(homeActivity);
        }

    }

    public void initializeMember(){
        userDBEngine = new UserDBEngine(LoginActivity.this);
        sp = getSharedPreferences("account", Context.MODE_PRIVATE);
        home_tv = findViewById(R.id.back_home_tv);
        register_tv = findViewById(R.id.register_tv);
        homeActivity = new Intent(LoginActivity.this , MainActivity.class);
        registerActivity = new Intent(LoginActivity.this , RegisterActivity.class);
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        userLogin_btn = findViewById(R.id.user_login_btn);
        staffLogin_btn = findViewById(R.id.staff_login_btn);
        rememberMe_cb = findViewById(R.id.remember_me_cb);
        autoLogin_cb = findViewById(R.id.auto_login_cb);

        //if user selected the rememberMe checkbox, we will get the username and password save in local and put it into the edit text box
        //if user selected the autoLogin checkbox, we get the data from local and auto login
        boolean rememberMe = sp.getBoolean("rememberMe",false);
        boolean autoLogin = sp.getBoolean("autoLogin",false);

        if (rememberMe){
            String username = sp.getString("username","");
            String password = sp.getString("password","");
            username_et.setText(username);
            password_et.setText(password);
            rememberMe_cb.setChecked(true);
        }else{
            username_et.setText("");
            password_et.setText("");
        }

        if (autoLogin){
            autoLogin_cb.setChecked(true);
            Toast.makeText(LoginActivity.this,"Auto Login!",Toast.LENGTH_SHORT).show();
        }

    }

    public void initializeEvent(){
        home_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeActivity);
            }
        });

        register_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(registerActivity);
            }
        });

        userLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginVerify();
            }
        });
        staffLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rememberMe_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        autoLogin_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (autoLogin_cb.isChecked()){
                    rememberMe_cb.setChecked(true);
                }
            }
        });
    }

    public void loginVerify(){
        if (username_et.getText() == null || password_et.getText() == null){
            Toast.makeText(LoginActivity.this,"Login information can't be empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        String username = username_et.getText().toString();
        String password = password_et.getText().toString();

        User user = userDBEngine.getUser(username,password);
        if (user == null){
            Toast.makeText(LoginActivity.this , "Username or password is incorrect...",Toast.LENGTH_SHORT).show();
            return;
        }

        //if login successfully, save account information into local if user selected the rememberMe or autoLogin checkbox
        SharedPreferences.Editor editor = sp.edit();
        if (rememberMe_cb.isChecked()){
            editor.putString("username",user.getUsername());
            editor.putString("password",user.getPassword());
        }
        //update checkbox state for next time login
        editor.putBoolean("rememberMe",rememberMe_cb.isChecked());
        editor.putBoolean("autoLogin",autoLogin_cb.isChecked());


        editor.putBoolean("isLogin",true);//save the login state
        editor.apply();

        startActivity(homeActivity);
    }
}