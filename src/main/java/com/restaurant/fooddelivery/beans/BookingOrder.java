package com.restaurant.fooddelivery.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize
public class BookingOrder {
    @Valid
    @NotNull
    private String customerId;

    @Valid
    @NotNull
    private String orderId;

    @Valid
    private String restaurantId;

    @Valid
    private List<FoodItem> items;

    public BookingOrder() {
    }

    public BookingOrder(final String customerId,
                        final String orderId,
                        final String restaurantId,
                        final List<FoodItem> items) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.items = items;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<FoodItem> getItems() {
        return items;
    }

    public void setItems(List<FoodItem> items) {
        this.items = items;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
