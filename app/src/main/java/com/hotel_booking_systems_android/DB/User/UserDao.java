package com.hotel_booking_systems_android.DB.User;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao//Dao is use for manipulate user table
public interface UserDao {

    @Insert
    void insertUsers(User ...users);

    @Update
    void updateStudents(User ...students);

    @Query("SELECT uid,username,password,ic,phone FROM user")
    List<User> getAllUsers();

    @Query("SELECT uid,username,password,ic,phone FROM user WHERE username = :username AND password = :password LIMIT 0,1")
    User getUser(String username,String password);

    @Query("SELECT * FROM user WHERE username = :username")
    User findByUsername(String username);

    @Insert
    void insertAll(User... users);

    @Delete
    void deleteUsers(User ...users);

}
