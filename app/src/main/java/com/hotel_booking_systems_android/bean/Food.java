package com.hotel_booking_systems_android.bean;

import com.hotel_booking_systems_android.R;

import java.util.ArrayList;
import java.util.List;

public class Food {

    private int id;
    private String name;
    private String description;
    private double price;
    private int img;

    private static final List<Food> foodList = new ArrayList<>();

    // 初始化数据
    static {
        foodList.add(new Food(1, "Chicken Rice", "This is a Chicken Rice" , 15, R.drawable.food_chicken_rice));
        foodList.add(new Food(2, "Pizza", "This is a Pizza", 30, R.drawable.food_pizza));
        foodList.add(new Food(3, "Sushi", "This is a Sushi", 20, R.drawable.food_sushi));
    }

    public Food(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Food(int id, String name, String description, double price, int img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.img = img;
    }

    public static List<Food> getFoodListInstance() {
        return foodList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
