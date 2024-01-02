package com.hotel_booking_systems_android.bean;

public class Tenant {
    private String id;
    private String Name;
    private String IC;
    private String ContactNumber;
    private String Gmail;
    private String RoomID;
    private String RoomType;
    private String RoomPrice;
    private String CheckingDate;
    private String CheckingTime;
    private String CheckoutDate;
    private String CheckoutTime;

     String titles;

    public Tenant() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getIC() {
        return IC;
    }

    public void setIC(String IC) {
        this.IC = IC;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String ContactNumber) {
        this.ContactNumber = ContactNumber;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String Gmail) {
        this.Gmail = Gmail;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String Population) {
        this.RoomType = Population;
    }

    public String getRoomPrice() {
        return RoomPrice;
    }

    public void setRoomPrice(String RoomPrice) {
        this.RoomPrice = RoomPrice;
    }

    public String getCheckingDate() {
        return CheckingDate;
    }

    public void setCheckingDate(String CheckingDate) {
        this.CheckingDate = CheckingDate;
    }

    public String getCheckingTime() {
        return CheckingTime;
    }

    public void setCheckingTime(String CheckingTime) {
        this.CheckingTime = CheckingTime;
    }

    public String getCheckoutDate() {
        return CheckoutDate;
    }

    public void setCheckoutDate(String CheckoutDate) {
        this.CheckoutDate = CheckoutDate;
    }

    public String getCheckoutTime() {
        return CheckoutTime;
    }

    public void setCheckoutTime(String CheckoutTime) {
        this.CheckoutTime = CheckoutTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
