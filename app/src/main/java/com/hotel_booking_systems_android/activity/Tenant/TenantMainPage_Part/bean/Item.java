package com.hotel_booking_systems_android.activity.Tenant.TenantMainPage_Part.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is record all item that user buy.
 */
public class Item {
    private int id;
    private String itemName;
    private double itemPrice;
    private int quantity;
    private double totalAmount;//itemPrice * quantity

    private static final List<Item> itemList = new ArrayList<>();

    static{
        itemList.add(new Item(1,"Room (Single Size)",150,1));
        itemList.add(new Item(2,"Room (Double Size)",250,1));
        itemList.add(new Item(3,"Room (Family Size)",400,1));
        itemList.add(new Item(4,"Room (Suite Size)",800,1));
        itemList.add(new Item(5,"Coca cola",5,5));
        itemList.add(new Item(6,"Indo Mee",3,10));
        itemList.add(new Item(7,"Pepsi",4,1));
    }

    public Item(int id, String itemName, double itemPrice, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.totalAmount = itemPrice*quantity;
    }

    public static List<Item> getItemListInstance(){
        return itemList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
