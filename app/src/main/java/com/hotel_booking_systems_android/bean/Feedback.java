package com.hotel_booking_systems_android.bean;

import androidx.annotation.NonNull;

public class Feedback {

    private int userId;
    private String roomNo;
    private Categories categories;
    private String content;

    public Feedback(int userId, String roomNo, Categories categories, String content) {
        this.userId = userId;
        this.roomNo = roomNo;
        this.categories = categories;
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public enum Categories {
        SERVICE("SERVICE"), FOOD("FOOD"), ENVIRONMENT("ENVIRONMENT"), OTHER("OTHER");
        private final String value;
        Categories(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
}
