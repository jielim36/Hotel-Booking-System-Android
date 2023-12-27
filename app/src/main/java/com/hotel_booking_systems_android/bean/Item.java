package com.hotel_booking_systems_android.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is record all item that user buy.
 */
public class Item {
    private Integer id;
    private Integer userId;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private double totalAmount;//itemPrice * quantity
    private Status status;

    public enum Status {
        PAID("PAID"),
        UNPAID("UNPAID");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }



    public Item(Integer id, Integer userID, String itemName, double itemPrice, int quantity , Status status) {
        this.id = id;
        this.userId = userID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.totalAmount = itemPrice*quantity;
        this.status = status;
    }

    public Item() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                '}';
    }
}
