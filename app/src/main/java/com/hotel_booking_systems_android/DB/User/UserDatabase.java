package com.hotel_booking_systems_android.DB.User;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {User.class},version = 1 , exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {//It is a singleTon design pattern

    public abstract UserDao getUserDao();

    private static UserDatabase INSTANCE;

    public static  synchronized UserDatabase getInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder
                    (context.getApplicationContext(), UserDatabase.class,"user_database")
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
