package com.hotel_booking_systems_android.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hotel_booking_systems_android.bean.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "feedback_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "feedback";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ROOM_NO = "room_no";
    private static final String COLUMN_CATEGORIES = "categories";
    private static final String COLUMN_CONTENT = "content";

    public FeedbackDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_USER_ID + " INTEGER,"
                + COLUMN_ROOM_NO + " TEXT,"
                + COLUMN_CATEGORIES + " TEXT,"
                + COLUMN_CONTENT + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No implementation needed for now
    }

    public void addFeedback(Feedback feedback) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, feedback.getUserId());
        values.put(COLUMN_ROOM_NO, feedback.getRoomNo());
        values.put(COLUMN_CATEGORIES, feedback.getCategories().getValue());
        values.put(COLUMN_CONTENT, feedback.getContent());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Feedback> getAllFeedbacks() {
        List<Feedback> feedbackList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Feedback feedback = new Feedback(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ROOM_NO)),
                        Feedback.Categories.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORIES))),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT))
                );
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return feedbackList;
    }
}