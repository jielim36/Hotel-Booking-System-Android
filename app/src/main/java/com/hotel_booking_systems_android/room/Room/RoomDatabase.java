package com.hotel_booking_systems_android.room.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {com.hotel_booking_systems_android.room.Room.Room.class},version = 1 , exportSchema = false)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    public abstract RoomDao getRoomDao();
    private static RoomDatabase INSTANCE;

    public static  synchronized RoomDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder
                    (context.getApplicationContext(), RoomDatabase.class,"room_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

}
