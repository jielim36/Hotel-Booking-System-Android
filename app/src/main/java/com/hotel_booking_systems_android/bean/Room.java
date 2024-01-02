package com.hotel_booking_systems_android.bean;


import com.hotel_booking_systems_android.Activity.Employee.Room.RoomStatus;
import com.hotel_booking_systems_android.R;

public class Room {
        private String room_no;
        private String price;
        private String type;
        private String floor_no;
        private String max_people;
        private String describe;
        //    String title;
        private RoomStatus status;
        private int images;


        public Room() {
            room_no = "0";
            price = "0";
            type = "Single";
            floor_no = "0";
            max_people = "0";
            describe = "no describe";
            status = RoomStatus.AVAILABLE;
            images = getImagesByType(type); // default single
        }
        public String getRoom_no() {
            return room_no;
        }

        public void setRoom_no(String room_no) {
            this.room_no = room_no;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFloor_no() {
            return floor_no;
        }

        public void setFloor_no(String floor_no) {
            this.floor_no = floor_no;
        }

        public String getMax_people() {
            return max_people;
        }

        public void setMax_people(String max_people) {
            this.max_people = max_people;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public RoomStatus getStatus() {
            return status;
        }

        public void setStatus(RoomStatus status) {
            this.status = status;
        }

        public int getImages() {
            return images;
        }

        public void setImages(int images) {
            this.images = images;
        }

        public int getImagesByType(String type) {
            if (type.equals("Single")) {
                return R.drawable.room_pic_single;
            } else if (type.equals("Double")) {
                return R.drawable.room_pic_double;
            } else if (type.equals("Family")) {
                return R.drawable.room_pic_family;
            } else if (type.equals("Suite")) {
                return R.drawable.room_pic_suite;
            }
            return 0;
        }
}

