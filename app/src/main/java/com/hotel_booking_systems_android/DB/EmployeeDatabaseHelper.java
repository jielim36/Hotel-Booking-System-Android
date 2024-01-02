package com.hotel_booking_systems_android.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hotel_booking_systems_android.bean.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "employee_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "employee";
    private static final String COLUMN_EMP_ID = "empId";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";

    public EmployeeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_EMP_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_FULLNAME + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_EMAIL + " TEXT)";
        db.execSQL(CREATE_TABLE);

        // 初始化 admin 账号
        initializeAdmin(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级数据库的逻辑，可以根据需要进行处理
    }

    private void initializeAdmin(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMP_ID , 1);
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "admin123");
        values.put(COLUMN_FULLNAME, "Admin User");
        values.put(COLUMN_PHONE, "123456789");
        values.put(COLUMN_EMAIL, "admin@example.com");

        db.insert(TABLE_NAME, null, values);
    }
    @SuppressLint("Range")
    public Employee authenticateEmployee(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Employee authenticatedEmployee = null;

        // 查询数据库，根据提供的用户名和密码验证登录
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_EMP_ID, COLUMN_FULLNAME, COLUMN_PHONE, COLUMN_EMAIL},
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            authenticatedEmployee = new Employee();
            authenticatedEmployee.setEmpId(cursor.getInt(cursor.getColumnIndex(COLUMN_EMP_ID)));
            authenticatedEmployee.setUsername(username);
            authenticatedEmployee.setFullname(cursor.getString(cursor.getColumnIndex(COLUMN_FULLNAME)));
            authenticatedEmployee.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
            authenticatedEmployee.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
        }

        // 关闭游标和数据库连接
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return authenticatedEmployee;
    }
}

