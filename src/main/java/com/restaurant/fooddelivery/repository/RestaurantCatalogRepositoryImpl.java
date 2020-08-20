package com.restaurant.fooddelivery.repository;

import com.restaurant.fooddelivery.beans.FoodItem;
import com.restaurant.fooddelivery.beans.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class RestaurantCatalogRepositoryImpl implements RestaurantCatalogRepository{


    private static final List<Restaurant> RESTAURANTS = restaurantsList();

    private static List<Restaurant> restaurantsList() {
        final List<Restaurant> restaurants = new ArrayList<>();

        final Restaurant restaurant1 = new Restaurant("So Tasty!",
                "restaurant1", "1, Cathedral Road",
                "600050",
                4,
                Arrays.asList(new FoodItem("Dosai", 50.0), new FoodItem("Ghee Dosai", 75.0), new FoodItem("Podi Dosai", 60.0)));

        final Restaurant restaurant2 = new Restaurant("Italian RestoBar",
                "restaurant2", "2, Chamiers Road",
                "600020",
                3,
                Arrays.asList(new FoodItem("Pasta", 250.0), new FoodItem("Pizza", 175.0), new FoodItem("Tiramisu", 160.0)));

        final Restaurant restaurant3 = new Restaurant("Nippon Foods",
                "restaurant3", "3, Taylors Road",
                "600010",
                5,
                Arrays.asList(new FoodItem("Sushi", 250.0), new FoodItem("Ramen", 275.0), new FoodItem("Tempura", 120.0)));

        final Restaurant restaurant4 = new Restaurant("Namma Biriyani",
                "restaurant4", "5, Taylors Road",
                "600010",
                5,
                Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0), new FoodItem("Prawn Biriyani", 220.0)));

        final Restaurant restaurant5 = new Restaurant("Andhra Bhojanam",
                "restaurant5", "8, Chamiers Road",
                "600020",
                2,
                Arrays.asList(new FoodItem("Andhra meals", 150.0), new FoodItem("Biriyani", 200.0), new FoodItem("Special meals", 220.0)));

        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        restaurants.add(restaurant3);
        restaurants.add(restaurant4);
        restaurants.add(restaurant5);

        return restaurants;
    }

    @Override
    public List<Restaurant> getRestaurantsCatalog() {
        return RESTAURANTS;
    }
}
