package com.restaurant.fooddelivery.service;

import com.restaurant.fooddelivery.beans.BookingOrder;
import com.restaurant.fooddelivery.beans.FilterRestaurants;
import com.restaurant.fooddelivery.beans.Restaurant;

import java.util.List;

public interface FoodDeliveryService {

    List<Restaurant> getFoodDeliveryCatalog(String pinCode);

    void createOrder(BookingOrder createOrder);

    void updateOrder(BookingOrder updateOrderRequest);

    void deleteOrder(String orderNumber);

    BookingOrder retrieveOrder(String orderNumber);

    List<Restaurant> filterRestaurants(FilterRestaurants filterRestaurantsRequest);
}
