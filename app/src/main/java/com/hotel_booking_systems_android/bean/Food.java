package com.hotel_booking_systems_android.bean;

import java.util.ArrayList;
import java.util.List;

public class Food {

    private int id;
    private String name;
    private String description;
    private String imagePath;
    private double price;

    private static final List<Food> foodList = new ArrayList<>();

    // 初始化数据
    static {
        foodList.add(new Food(1, "Chicken Rice", "This is a Chicken Rice", "xx", 15));
        foodList.add(new Food(2, "Pizza", "This is a Pizza", "xx", 30));
        foodList.add(new Food(3, "Sushi", "This is a Sushi", "xx", 20));
    }

    public Food(int id, String name, String imagePath, double price) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
    }

    public Food(int id, String name, String description, String imagePath, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
