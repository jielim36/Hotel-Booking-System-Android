package com.hotel_booking_systems_android.activity.TenantMainPage_Part.service;


import com.hotel_booking_systems_android.activity.TenantMainPage_Part.bean.Item;

import java.util.List;

public class ItemService {

    public void addItem(Item item){
        Item.getItemListInstance().add(item);
    }

    public void removeItem(Item item) {
        Item.getItemListInstance().remove(item);
    }

    public double getTotalAmount(){
        List<Item> itemListInstance = Item.getItemListInstance();
        double totalAmount = 0;
        for (Item item : itemListInstance){
            totalAmount += item.getTotalAmount();
        }
        return totalAmount;
    }

}
