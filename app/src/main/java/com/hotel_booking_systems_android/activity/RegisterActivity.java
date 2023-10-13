package com.hotel_booking_systems_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel_booking_systems_android.MainActivity;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.room.User.User;
import com.hotel_booking_systems_android.room.manager.DBEngine;

public class RegisterActivity extends AppCompatActivity {

    private Intent homeActivity;
    private TextView backHome_tv;
    private EditText username_et;
    private EditText password_et;
    private EditText ic_et;
    private EditText phone_et;
    private Button register_btn;
    private DBEngine dbEngine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeViewAndActivity();
        initializeEvent();
    }

    public void initializeViewAndActivity(){
        dbEngine = new DBEngine(RegisterActivity.this);
        homeActivity = new Intent(RegisterActivity.this , MainActivity.class);
        backHome_tv = findViewById(R.id.back_home_tv);
        register_btn = findViewById(R.id.register_btn);

        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        ic_et = findViewById(R.id.ic_et);
        phone_et = findViewById(R.id.phone_et);
    }

    public void initializeEvent(){
        backHome_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(homeActivity);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCommit();
            }
        });
    }

    public void registerCommit(){
        String username = username_et.getText().toString();
        String password = password_et.getText().toString();
        String ic = ic_et.getText().toString();
        String phone = phone_et.getText().toString();

        if (!registerVerify(username,password,ic,phone)) {
            return;//cancel the register action if user's register information is incorrect
        }

        User user = new User(username,password,ic,Long.parseLong(phone),new byte[0]);
        dbEngine.insertUsers(user);
        Log.e("DBEngine","User register successfully:" + user);
    }

    public boolean registerVerify(String username, String password , String ic , String phone){

        //username and password verify
        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Username or password can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username.length() > 17) {
            Toast.makeText(this, "The length of username can't more than 17", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() > 24) {
            Toast.makeText(this, "The length of password can't more than 24", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username.length() < 4 || password.length() < 4) {
            Toast.makeText(this, "The length of username or password must greater than 4!", Toast.LENGTH_SHORT).show();
            return false;
        }
        String usernameRegex = "^[a-zA-Z0-9]+$";
        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]+$";//must contain at least one uppercase letter, lowercase letter and a number

        if (!username.matches(usernameRegex) || !password.matches(usernameRegex)) {
            Toast.makeText(this, "Username or password can only contain characters from a-z,A-Z,0-9", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.matches(passwordRegex)) {
            Toast.makeText(this, "Password must contain at least one uppercase letter, lowercase letter and a number", Toast.LENGTH_SHORT).show();
            return false;
        }

        //ic verify
        String icRegex = "\\d{2}(0[1-9]|1[0-2])(0[1-9]|1\\d|2\\d|3[0-1])-\\d[2]-\\d{4}";//ic format
        if (ic.isEmpty()){
            Toast.makeText(this, "IC can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!ic.matches(icRegex)){
            Toast.makeText(this, "IC format is not correct!", Toast.LENGTH_SHORT).show();
            return false;
        }

        String phoneRegex = "^60\\d{10}$";//The first two numbers must be '60', followed by any 10 numbers
        if (phone.isEmpty()){
            Toast.makeText(this, "Phone number can't be empty!", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!phone.matches(phoneRegex)){
            Toast.makeText(this, "Phone number format is not correct!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}