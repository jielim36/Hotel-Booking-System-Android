package com.hotel_booking_systems_android.room.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Room {

    @PrimaryKey(autoGenerate = true)
    private int roomID;  // 主键
    private int floorNumber;//1-4
    private String roomNumber;//1-4
    private Integer roomType;//single,double,family,suite
    private double price;
    private Integer availability;  // State:Booked、Available、Maintenance, cleaning

    public final static int ROOM_TYPE_SINGLE = 1;//one single bed and public bathroom
    public final static int ROOM_TYPE_DOUBLE = 2;//two single beds and one bathroom
    public final static int ROOM_TYPE_FAMILY = 3;//two larger beds and one bathroom

    public final static int ROOM_TYPE_SUITE = 4;//two larger beds, include living room, one bathroom and kitchen
    public final static int AVAILABILITY_AVAILABLE = 1;
    public final static int AVAILABILITY_BOOKED = 2;
    public final static int AVAILABILITY_MAINTENANCE = 3;
    public final static int AVAILABILITY_CLEANING = 4;

    public Room(){

    }

    public Room(int roomID, int floorNumber, String roomNumber, Integer roomType, double price, Integer availability) {
        this.roomID = roomID;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.availability = availability;
    }

    public Room(int floorNumber, String roomNumber, Integer roomType, double price, Integer availability) {
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.availability = availability;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getRoomType() {
        return roomType;
    }

    public void setRoomType(Integer roomType) {
        this.roomType = roomType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", floorNumber=" + floorNumber +
                ", roomNumber='" + roomNumber + '\'' +
                ", roomType='" + roomType + '\'' +
                ", price=" + price +
                ", availability='" + availability + '\'' +
                '}';
    }
}
