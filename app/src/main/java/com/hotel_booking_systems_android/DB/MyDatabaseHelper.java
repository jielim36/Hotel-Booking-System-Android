package com.hotel_booking_systems_android.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.hotel_booking_systems_android.Activity.Employee.Room.Room;
import com.hotel_booking_systems_android.Activity.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.Activity.Employee.Tenant.Tenant;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;

    // 定义建表SQL语句

    // 创建名为 TenantData 的 Table 用来存储房客信息
    private static final String SQL_CREATE_TENANT_DATA_TABLE =
            "CREATE TABLE TenantData (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name VARCHAR," +
                    "IC VARCHAR, ContactNumber VARCHAR, Gmail VARCHAR, RoomID VARCHAR, RoomType VARCHAR, RoomPrice VARCHAR," +
                    "CheckingDate VARCHAR, CheckingTime VARCHAR, CheckoutDate VARCHAR, CheckoutTime VARCHAR)";

    // 创建名为 HistoryData 的 Table 用来存储历史房客信息
    private static final String SQL_HISTORY_TENANT_DATA_TABLE =
            "CREATE TABLE HistoryData(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Name VARCHAR, IC VARCHAR, ContactNumber VARCHAR, Gmail VARCHAR, RoomID VARCHAR, RoomType VARCHAR, RoomPrice VARCHAR," +
                    "CheckingDate VARCHAR, CheckingTime VARCHAR, CheckoutDate VARCHAR, CheckoutTime VARCHAR)";

    // 创建名为 Rooms 的 Table 用来存储房间信息
    private static final String SQL_CREATE_TABLE_ROOMS =
            "CREATE TABLE Rooms(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "room_no TEXT UNIQUE, price TEXT, type TEXT, floor_no TEXT, max_people TEXT, describe TEXT, status TEXT)";

    private static final String SQL_ADD_NEW_COLUMN = "ALTER TABLE TenantData ADD COLUMN NewColumn VARCHAR";
    private static final String SQL_ADD_NEW_COLUMN_HISTORY = "ALTER TABLE HistoryData ADD COLUMN NewColumn VARCHAR";
    private static final String SQL_ADD_NEW_COLUMN_ROOMS = "ALTER TABLE Rooms ADD COLUMN NewColumn VACHAR";

    public MyDatabaseHelper(Context context) {
        super(context, "Hotel_Booking_System.DB", null, DATABASE_VERSION);
        Log.d("Database Version", "Database version specified: " + DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 数据库首次初始化时创建TenantData表
        db.execSQL(SQL_CREATE_TENANT_DATA_TABLE);
        db.execSQL(SQL_HISTORY_TENANT_DATA_TABLE);
        db.execSQL(SQL_CREATE_TABLE_ROOMS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // 执行SQL语句来更新这里的schema
            db.execSQL(SQL_ADD_NEW_COLUMN);
            db.execSQL(SQL_ADD_NEW_COLUMN_HISTORY);
            db.execSQL(SQL_ADD_NEW_COLUMN_ROOMS);
        }
    }

    // 搜索功能
    @SuppressLint("Range")
    public List<Tenant> searchData(String query) {
        List<Tenant> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // 执行数据库查询
        Cursor cursor = db.rawQuery("SELECT * FROM TenantData WHERE Name LIKE ?",
                new String[]{"%" + query + "%"});

        // 遍历查询结果并添加到搜索结果列表
        if (cursor.moveToFirst()) {
            do {
                Tenant tenant = new Tenant();
                tenant.setId(cursor.getString(cursor.getColumnIndex("id")));
                tenant.setName(cursor.getString(cursor.getColumnIndex("Name")));
                tenant.setIC(cursor.getString(cursor.getColumnIndex("IC")));
                tenant.setContactNumber(cursor.getString(cursor.getColumnIndex("ContactNumber")));
                tenant.setGmail(cursor.getString(cursor.getColumnIndex("Gmail")));
                tenant.setRoomID(cursor.getString(cursor.getColumnIndex("RoomID")));
                tenant.setRoomType(cursor.getString(cursor.getColumnIndex("RoomType")));
                tenant.setRoomPrice(cursor.getString(cursor.getColumnIndex("RoomPrice")));
                tenant.setCheckingDate(cursor.getString(cursor.getColumnIndex("CheckingDate")));
                tenant.setCheckingTime(cursor.getString(cursor.getColumnIndex("CheckingTime")));
                tenant.setCheckoutDate(cursor.getString(cursor.getColumnIndex("CheckoutDate")));
                tenant.setCheckoutTime(cursor.getString(cursor.getColumnIndex("CheckoutTime")));

                searchResults.add(tenant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return searchResults;
    }

    // 更新房间状态
    public boolean updateStatus(String roomNo, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);

        int rowsAffected = db.update("Rooms", values, "room_no=?", new String[]{roomNo});
        db.close();

        // 如果更新的行数大于 0，表示更新成功
        return rowsAffected > 0;
    }

    // 更新价钱
    public boolean updatePrice(String roomNo, String newPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", newPrice);

        int rowsAffected = db.update("Rooms", values, "room_no=?", new String[]{roomNo});
        db.close();

        // 如果更新的行数大于 0，表示更新成功
        return rowsAffected > 0;
    }

    // 查找房间状态为空的房间
    @SuppressLint("Range")
    public List<String> getAvailableRoomNumbers() {
        List<String> availableRooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT room_no FROM rooms WHERE status = ?",
                new String[]{String.valueOf(RoomStatus.AVAILABLE)});

        if (cursor.moveToFirst()) {
            do {
                String roomNo = cursor.getString(cursor.getColumnIndex("room_no"));
                availableRooms.add(roomNo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return availableRooms;
    }

    // 查找选择的房间的价钱和种类
    @SuppressLint("Range")
    public Room getRoomDetails(String roomId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Room room = null;

        Cursor cursor = db.rawQuery("SELECT * FROM Rooms WHERE room_no = ?", new String[]{roomId});

        if (cursor.moveToFirst()) {
            room = new Room();
            room.setRoom_no(cursor.getString(cursor.getColumnIndex("room_no")));
            room.setPrice(cursor.getString(cursor.getColumnIndex("price")));
            room.setType(cursor.getString(cursor.getColumnIndex("type")));
            room.setMax_people(cursor.getString(cursor.getColumnIndex("max_people")));
            RoomStatus roomStatus = RoomStatus.valueOf(cursor.getString(cursor.getColumnIndex("status")));
            room.setStatus(roomStatus);
            // Add other room details as required
        }

        cursor.close();
        db.close();
        return room;
    }

    public boolean insertRoom(String room_no , String price, String type, String floor_no, String max_people , String describe, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO Rooms(room_no, price, type, floor_no, max_people, describe, status) VALUES(?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, room_no);
        statement.bindString(2, price);
        statement.bindString(3, type);
        statement.bindString(4, floor_no);
        statement.bindString(5, max_people);
        statement.bindString(6, describe); // 添加这一行来绑定 describe 字段的值
        statement.bindString(7, status); // 添加这一行来绑定 status 字段的值
        statement.execute();

        return true;
    }

    @SuppressLint("Range")
    public List<Room> getAllRooms() {
        List<Room> allRooms = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Rooms", null);

        if (cursor.moveToFirst()) {
            do {
                Room room = new Room();
                room.setRoom_no(cursor.getString(cursor.getColumnIndex("room_no")));
                room.setPrice(cursor.getString(cursor.getColumnIndex("price")));
                room.setType(cursor.getString(cursor.getColumnIndex("type")));
                room.setFloor_no(cursor.getString(cursor.getColumnIndex("floor_no")));
                room.setMax_people(cursor.getString(cursor.getColumnIndex("max_people")));
                room.setDescribe(cursor.getString(cursor.getColumnIndex("describe")));
                RoomStatus roomStatus = RoomStatus.valueOf(cursor.getString(cursor.getColumnIndex("status")));
                room.setStatus(roomStatus);
                // Add other room details as required
                allRooms.add(room);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return allRooms;
    }

}
