package com.hotel_booking_systems_android.room.manager;

import android.content.Context;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.hotel_booking_systems_android.room.Room.Room;
import com.hotel_booking_systems_android.room.Room.RoomDao;
import com.hotel_booking_systems_android.room.Room.RoomDatabase;
import com.hotel_booking_systems_android.room.User.UserDao;
import com.hotel_booking_systems_android.room.User.UserDatabase;

import java.util.List;

public class RoomDBEngine {

    private RoomDao roomDao;

    public RoomDBEngine(Context context){
        RoomDatabase userDatabase = RoomDatabase.getInstance(context);
        roomDao = userDatabase.getRoomDao();
    }

    public void insertRooms(Room ...rooms){
        roomDao.insertRooms(rooms);
    }

    public void deleteRooms(Room ...rooms){
        roomDao.deleteRooms(rooms);
    }

    public List<Room> getRoomsByFloorNumber(Integer floorNumber){
        return roomDao.getRoomsByFloorNumber(floorNumber);
    }

    public Room getRoomByFloorAndRoomNumber(Integer floorNumber , Integer roomNumber){
        return roomDao.getRoomByFloorAndRoomNumber(floorNumber,roomNumber);
    }

    public void updateRooms(Room ...rooms){
        roomDao.updateRooms(rooms);
    }

    public void updateRoom(Room room){
        roomDao.updateRoom(room);
    }

}
