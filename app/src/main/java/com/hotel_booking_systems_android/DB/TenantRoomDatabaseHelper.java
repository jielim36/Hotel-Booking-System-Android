package com.hotel_booking_systems_android.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hotel_booking_systems_android.DB.manager.UserDBEngine;
import com.hotel_booking_systems_android.bean.TenantRoom;

import java.util.ArrayList;
import java.util.List;

public class TenantRoomDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tenant_room_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tenant_room";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ROOM_ID = "room_id";
    private static final String COLUMN_STATUS = "status";

    public TenantRoomDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_ROOM_ID + " INTEGER,"
                + COLUMN_STATUS + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTenantRoom(int userId, int roomId, TenantRoom.Status status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_ROOM_ID, roomId);
        values.put(COLUMN_STATUS, status.getValue());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateTenantRoomStatus(int tenantRoomId, TenantRoom.Status newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, newStatus.getValue());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(tenantRoomId)}
        );
        db.close();
    }

    public void updateTenantRoomStatusByUserId(Integer userId, TenantRoom.Status newStatus) {
        // 如果 userId 为 -1，表示未找到对应用户，可以进行错误处理或者抛出异常
        if (userId == -1) {
            // 处理未找到用户的情况
            return;
        }

        // 使用 userId 更新 TenantRoom 表中对应记录的 status
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, newStatus.getValue());

        db.update(
                TABLE_NAME,
                values,
                COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(userId)}
        );
        db.close();
    }

    public List<TenantRoom> getTenantRoomsByUserIdAndStatus(int userId, TenantRoom.Status status) {
        List<TenantRoom> tenantRoomList = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_STATUS + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, new String[]{String.valueOf(userId), status.getValue()});

        if (cursor.moveToFirst()) {
            do {
                TenantRoom tenantRoom = new TenantRoom();
                tenantRoom.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                tenantRoom.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                tenantRoom.setRoomId(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOM_ID)));
                tenantRoom.setStatus(TenantRoom.Status.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))));

                tenantRoomList.add(tenantRoom);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tenantRoomList;
    }
}
