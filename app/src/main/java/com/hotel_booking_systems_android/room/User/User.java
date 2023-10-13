package com.hotel_booking_systems_android.room.User;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity//a database table
public class User {

    @PrimaryKey(autoGenerate = true)//autoincrement=true
    private int uid;

    private String username;
    private String password;
    private String ic;
    private Long phone;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] avatar;

    public User() {
    }

    public User(int uid, String username, String password, String ic, Long phone, byte[] avatar) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.ic = ic;
        this.phone = phone;
        this.avatar = avatar;
    }

    public User(String username, String password, String ic, Long phone, byte[] avatar) {
        this.username = username;
        this.password = password;
        this.ic = ic;
        this.phone = phone;
        this.avatar = avatar;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ic='" + ic + '\'' +
                ", phone=" + phone +
                '}';
    }
}
