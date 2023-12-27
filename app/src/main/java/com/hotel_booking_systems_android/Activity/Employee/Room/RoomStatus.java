package com.hotel_booking_systems_android.Activity.Employee.Room;


import com.hotel_booking_systems_android.R;

public enum RoomStatus {
    AVAILABLE(0, R.drawable.ic_available),
    MAINTENANCE(1,R.drawable.ic_maintain),
    BOOKED(2,R.drawable.ic_booked),
    CLEANING(3,R.drawable.ic_cleaning);

    private final int value;
    private int imgId;

    RoomStatus(int value, int imgId) {
        this.value = value;
        this.imgId = imgId;
    }

    public int getValue() {
        return value;
    }

    public int getImgId() {
        return imgId;
    }
}
