package com.restaurant.fooddelivery.service;

import com.restaurant.fooddelivery.beans.BookingOrder;
import com.restaurant.fooddelivery.beans.FilterRestaurants;
import com.restaurant.fooddelivery.beans.Restaurant;
import com.restaurant.fooddelivery.exception.DataNotFoundException;
import com.restaurant.fooddelivery.repository.OrdersRepository;
import com.restaurant.fooddelivery.repository.RestaurantCatalogRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodDeliveryServiceImpl implements FoodDeliveryService{

    private OrdersRepository ordersRepository;
    private RestaurantCatalogRepository catalogRepository;


    @Autowired
    public FoodDeliveryServiceImpl(final OrdersRepository ordersRepository,
                                   final RestaurantCatalogRepository catalogRepository) {
        this.ordersRepository = ordersRepository;
        this.catalogRepository = catalogRepository;
    }


    public List<Restaurant> getFoodDeliveryCatalog(final String pinCode) {
        List<Restaurant> restaurantsCatalog = catalogRepository.getRestaurantsCatalog()
                .stream()
                .filter(restaurant -> restaurant.getPinCode().equalsIgnoreCase(pinCode))
                .collect(Collectors.toList());

        if (restaurantsCatalog.size() == 0){
            throw new DataNotFoundException("No restaurants found at this location at the moment. Please check back later.");
        }
        return restaurantsCatalog;
    }

    public void createOrder(final BookingOrder createOrderRequest) {

        if (ordersRepository.getAllOrders().stream()
                .anyMatch(o -> o.getOrderId().equalsIgnoreCase(createOrderRequest.getOrderId()))) {
            throw new BadRequestException("Order not present in database. Use the createOrder endpoint to create the order.");
        }
        ordersRepository.createOrder(createOrderRequest);
    }

    public void updateOrder(final BookingOrder updateOrderRequest) {
        if(ordersRepository.getAllOrders().stream()
                .noneMatch(o -> o.getOrderId().equalsIgnoreCase(updateOrderRequest.getOrderId()))){
            throw new BadRequestException("Order not present in database. Use the createOrder endpoint to create the order.");
        }
        ordersRepository.updateOrder(updateOrderRequest);
    }

    public void deleteOrder(final String orderNumber) {
        if (ordersRepository.getAllOrders().stream()
                .noneMatch(o -> o.getOrderId().equalsIgnoreCase(orderNumber))){
            throw new DataNotFoundException(orderNumber, "Order not present in database.");
        }
        ordersRepository.deleteOrder(orderNumber);
    }

    public BookingOrder retrieveOrder(final String orderNumber) {
        return ordersRepository.retrieveOrder(orderNumber);
    }

    public List<Restaurant> filterRestaurants(final FilterRestaurants filterRestaurantsRequest) {

        List<Restaurant> filteredRestaurants = catalogRepository.getRestaurantsCatalog();

        if(StringUtils.isNotBlank(filterRestaurantsRequest.getPinCode())){
            filteredRestaurants =  filteredRestaurants
                    .stream()
                    .filter (r -> r.getPinCode().equalsIgnoreCase(filterRestaurantsRequest.getPinCode()))
                    .collect(Collectors.toList());
        }

        if (filterRestaurantsRequest.getPrice() != null){
            filteredRestaurants = filteredRestaurants
                    .stream()
                    .filter(r -> r.getItems().stream().anyMatch(foodItem -> foodItem.getPrice() <= filterRestaurantsRequest.getPrice()))
                    .collect(Collectors.toList());
        }

        if (filterRestaurantsRequest.getRating() != null){
            filteredRestaurants = filteredRestaurants
                    .stream()
                    .filter(r -> r.getRating() >= filterRestaurantsRequest.getRating())
                    .collect(Collectors.toList());
        }


        if (filteredRestaurants.size() == 0){
            throw new DataNotFoundException("No restaurants fit the criteria. Please remove one or more filter criteria and search again.");
        }

        return filteredRestaurants;
    }
}
