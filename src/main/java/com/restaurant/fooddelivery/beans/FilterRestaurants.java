package com.restaurant.fooddelivery.beans;

import org.springframework.lang.Nullable;

import javax.validation.Valid;

public class FilterRestaurants {
    @Valid
    @Nullable
    private Integer rating;

    @Valid
    @Nullable
    private Double price;

    @Valid
    @Nullable
    private String pinCode;

    public FilterRestaurants() {
    }

    public FilterRestaurants(@Nullable @Valid Integer rating,
                             @Nullable @Valid Double price,
                             @Nullable @Valid String pinCode) {
        this.rating = rating;
        this.price = price;
        this.pinCode = pinCode;
    }

    @Nullable
    public Integer getRating() {
        return rating;
    }

    public void setRating(@Nullable Integer rating) {
        this.rating = rating;
    }

    @Nullable
    public Double getPrice() {
        return price;
    }

    public void setPrice(@Nullable Double price) {
        this.price = price;
    }

    @Nullable
    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(@Nullable String pinCode) {
        this.pinCode = pinCode;
    }
}
