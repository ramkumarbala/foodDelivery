package com.restaurant.fooddelivery.beans;

import java.util.List;

public class Restaurant {
    private String name;

    private String restaurantId;

    private String address;

    private String pinCode;

    private int rating;

    private List<FoodItem> items;

    public Restaurant() {
    }

    public Restaurant(final String name,
                      final String restaurantId,
                      final String address,
                      final String pinCode,
                      final int rating,
                      final List<FoodItem> items) {
        this.name = name;
        this.restaurantId = restaurantId;
        this.address = address;
        this.pinCode = pinCode;
        this.rating = rating;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public void setItems(List<FoodItem> items) {
        this.items = items;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
