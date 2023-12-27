package com.hotel_booking_systems_android.bean;

public class TenantRoom {
    private int id;
    private int userId;
    private int roomId;
    private Status status;

    public enum Status {
        CHECKED_IN("CHECKED_IN"),
        CHECKED_OUT("CHECKED_OUT");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public TenantRoom(int id, int userId, int roomId, Status status) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.status = status;
    }

    public TenantRoom() {
    }

    // Getters and setters for all attributes

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
