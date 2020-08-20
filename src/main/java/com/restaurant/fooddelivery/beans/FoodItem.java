package com.restaurant.fooddelivery.beans;

public class FoodItem {
    private String itemName;

    private double price;

    public FoodItem() {
    }

    public FoodItem(final String itemName,
                    final double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
