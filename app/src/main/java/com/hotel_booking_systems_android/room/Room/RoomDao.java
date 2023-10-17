package com.hotel_booking_systems_android.room.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RoomDao {

    //CRUD
    @Insert
    void insertRooms(Room ...rooms);

    @Delete
    void deleteRooms(Room ...rooms);

    @Query("SELECT * FROM room WHERE floorNumber = :floorNumber")
    List<Room> getRoomsByFloorNumber(Integer floorNumber);

    @Query("SELECT * FROM room WHERE floorNumber = :floorNumber AND roomNumber = :roomNumber")
    Room getRoomByFloorAndRoomNumber(Integer floorNumber , Integer roomNumber);

    @Update
    void updateRooms(Room ...rooms);

    @Update
    void updateRoom(Room room);

}
