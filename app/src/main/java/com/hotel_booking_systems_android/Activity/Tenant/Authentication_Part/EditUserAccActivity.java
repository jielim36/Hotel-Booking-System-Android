package com.hotel_booking_systems_android.Activity.Tenant.Authentication_Part;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hotel_booking_systems_android.DB.EmployeeDatabaseHelper;
import com.hotel_booking_systems_android.DB.User.User;
import com.hotel_booking_systems_android.DB.User.UserDBEngine;
import com.hotel_booking_systems_android.R;
import com.hotel_booking_systems_android.service.AccountSharedPreferences;

public class EditUserAccActivity extends AppCompatActivity {

    private EditText editUsername, editPassword, editConfirmPassword ,editIC, editPhone;
    private TextView btnSave;
    private AccountSharedPreferences accSp;
    private UserDBEngine userDBEngine;
    private ImageButton editAcc_back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_acc);

        // 初始化控件
        editUsername = findViewById(R.id.edit_username);
        editPassword = findViewById(R.id.edit_password);
        editConfirmPassword = findViewById(R.id.edit_confirm_password);
        editIC = findViewById(R.id.edit_ic);
        editPhone = findViewById(R.id.edit_phone);
        btnSave = findViewById(R.id.btn_save);
        accSp = AccountSharedPreferences.getInstance(getApplicationContext());
        userDBEngine = new UserDBEngine(this);
        editAcc_back_btn = findViewById(R.id.editAcc_back_btn);

        //set default value (original information)
        User user = userDBEngine.getUserById(accSp.getUserId());
        editUsername.setText(user.getUsername());
        editPassword.setText(user.getPassword());
        editIC.setText(user.getIc());
        editPhone.setText(String.valueOf(user.getPhone()));

        editAcc_back_btn.setOnClickListener(v -> {
            onBackPressed();
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入的用户信息
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                String confirmPassword = editConfirmPassword.getText().toString();
                String ic = editIC.getText().toString();
                String phone = editPhone.getText().toString();

                boolean isValid = accountInfoVerify(username, password , confirmPassword, ic, phone);
                if(isValid && !accSp.isStaff()){
                    updateNormalAccInfo(username,password,ic,phone);
                }else {
                    Toast.makeText(EditUserAccActivity.this, "Your Information is not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean accountInfoVerify(String username, String password, String confirmPassword, String ic , String phone){

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

        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Your password and confirm password is not matches", Toast.LENGTH_SHORT).show();
            return false;
        }

        //ic verify
        String icRegex = "\\d{2}(0[1-9]|1[0-2])(0[1-9]|1\\d|2\\d|3[0-1])-\\d{2}-\\d{4}";//ic format
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

    private void updateNormalAccInfo(String username, String password, String ic, String phone){
        Integer userId = accSp.getUserId();
        if(userId <= 0){
            Toast.makeText(this, "Invalid User Id", Toast.LENGTH_SHORT).show();
            return;
        }

        userDBEngine = new UserDBEngine(this);
        User userById = userDBEngine.getUserById(userId);
        userById.setUsername(username);
        userById.setPassword(password);
        userById.setIc(ic);
        userById.setPhone(userById.getPhone());
        userDBEngine.updateUsers(userById);

        Toast.makeText(this, "Update Account Successfully", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}