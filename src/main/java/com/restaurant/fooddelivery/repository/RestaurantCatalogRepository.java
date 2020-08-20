package com.restaurant.fooddelivery.repository;

import com.restaurant.fooddelivery.beans.Restaurant;

import java.util.List;

public interface RestaurantCatalogRepository {

    List<Restaurant> getRestaurantsCatalog();
}
