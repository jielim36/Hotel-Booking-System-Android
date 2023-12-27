package com.hotel_booking_systems_android.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hotel_booking_systems_android.Activity.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.bean.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "item_database";
    private static final int DATABASE_VERSION = 3; // Increment the version due to schema change

    private static final String TABLE_NAME = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_PRICE = "item_price";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    private static final String COLUMN_STATUS = "status"; // New column

    public ItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USER_ID + " INTEGER DEFAULT 0,"
                + COLUMN_ITEM_NAME + " TEXT,"
                + COLUMN_ITEM_PRICE + " REAL,"
                + COLUMN_QUANTITY + " INTEGER,"
                + COLUMN_TOTAL_AMOUNT + " REAL,"
                + COLUMN_STATUS + " TEXT)"; // Add the new column
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            // Add the new column
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_STATUS + " TEXT");
        }
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, item.getUserId());
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_PRICE, item.getItemPrice());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_TOTAL_AMOUNT, item.getTotalAmount());
        values.put(COLUMN_STATUS, item.getStatus().getValue()); // Set the status
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // ... (other methods remain unchanged)

    public void updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, item.getUserId());
        values.put(COLUMN_ITEM_NAME, item.getItemName());
        values.put(COLUMN_ITEM_PRICE, item.getItemPrice());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_TOTAL_AMOUNT, item.getTotalAmount());
        values.put(COLUMN_STATUS, item.getStatus().getValue()); // Set the status

        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(item.getId())});
        db.close();
    }

    public List<Item> getUnpaidItemByUserId(int userId) {
        List<Item> itemList = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_STATUS + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, new String[]{String.valueOf(userId), Item.Status.UNPAID.getValue()});

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                item.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                item.setItemName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                item.setItemPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PRICE)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));
                item.setTotalAmount(cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_AMOUNT)));
//                Item.Status status = Item.Status.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
//                Log.e("status", status.getValue());
                item.setStatus(Item.Status.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))));

                itemList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return itemList;
    }

    public void checkoutUnpaidItems(List<Item> items) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Item item : items) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_STATUS, Item.Status.PAID.getValue()); // Set the status to PAID

            db.update(
                    TABLE_NAME,
                    values,
                    COLUMN_ID + "=? AND " + COLUMN_STATUS + "=?",
                    new String[]{String.valueOf(item.getId()), Item.Status.UNPAID.getValue()}
            );
        }
        db.close();
    }

}
