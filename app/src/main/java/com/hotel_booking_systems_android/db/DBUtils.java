package com.hotel_booking_systems_android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DBUtils extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HotelDatabase.db";

    private static SQLiteDatabase dbObj = null;

    private static final int VERSION = 1;//Increment by 1 when update this database

    @RequiresApi(api = Build.VERSION_CODES.P)
    public DBUtils(@Nullable Context context) {
        super(context, DATABASE_NAME, VERSION, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        initializeData(db);
    }

    public void initializeData(SQLiteDatabase db){
        db.execSQL("CREATE TABLE user_account (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_name TEXT," +
                "password TEXT," +
                "IC TEXT," +
                "phone INTEGER," +
                "avatar BLOB);");

        db.execSQL("CREATE TABLE staff_account(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_name TEXT," +
                "password TEXT," +
                "newera_id INTEGER," +
                "phone INTEGER," +
                "autority INTEGER," +
                "avatar BLOB);");

        String userAccountInsert = "INSERT INTO user VALUES (null,'jielim36', 'jielim36', '040723-12-9999', 1234567890,0);";
        String staffAccountInsert = "INSERT INTO staff_account (user_name, password, newera_id, phone, autority) VALUES ('admin', 'admin', 2290025, 01116540123, 1,0);";

        db.execSQL(userAccountInsert);
        db.execSQL(staffAccountInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
