package com.restaurant.fooddelivery.repository;

import com.restaurant.fooddelivery.beans.BookingOrder;
import com.restaurant.fooddelivery.beans.FoodItem;
import com.restaurant.fooddelivery.beans.Restaurant;
import com.restaurant.fooddelivery.exception.DataNotFoundException;
import org.springframework.stereotype.Repository;

import javax.ws.rs.BadRequestException;
import java.util.*;

@Repository
public class OrdersRepositoryImpl implements OrdersRepository{
    public static final List<BookingOrder> ORDERS = ordersList();

    private static List<BookingOrder> ordersList() {
        final List<BookingOrder> orders = new ArrayList<>();

        final BookingOrder order1 = new BookingOrder("CUST001", "0001", "restaurant1", Arrays.asList(new FoodItem("Dosai", 50.0), new FoodItem("Podi Dosai", 60.0)));

        final BookingOrder order2 = new BookingOrder("CUST001", "0002", "restaurant5", Collections.singletonList(new FoodItem("Biriyani", 250.0)));

        final BookingOrder order3 = new BookingOrder("CUST003", "0003", "restaurant3", Arrays.asList(new FoodItem("Sushi", 250.0), new FoodItem("Tempura", 120.0)));

        final BookingOrder order4 = new BookingOrder("CUST002", "0004", "restaurant2", Arrays.asList(new FoodItem("Pasta", 250.0), new FoodItem("Tiramisu", 160.0)));

        final BookingOrder order5 = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);

        return orders;
    }

    @Override
    public BookingOrder retrieveOrder(final String orderId) {
        return ORDERS.stream().filter(o -> o.getOrderId().equalsIgnoreCase(orderId)).findFirst()
                .orElseThrow(() -> new DataNotFoundException(orderId, "Order Number not found in database"));
    }

    @Override
    public void createOrder(final BookingOrder order) {
        ORDERS.add(order);
    }

    @Override
    public void updateOrder(final BookingOrder order) {
        ORDERS.set(ORDERS.indexOf(order), order);
    }

    @Override
    public void deleteOrder(final BookingOrder order) {
        if(!ORDERS.contains(order)){
            throw new BadRequestException("Order not present in database");
        }
        ORDERS.remove(order);
    }

    @Override
    public void deleteOrder(final String orderId) {
        final BookingOrder deleteOrder = ORDERS.stream()
                .filter(o -> o.getOrderId().equalsIgnoreCase(orderId))
                .findFirst()
                .orElseThrow(() -> new DataNotFoundException(orderId, "Order not present in database."));

        ORDERS.remove(deleteOrder);
    }

    @Override
    public List<BookingOrder> getAllOrders() {
        return ordersList();
    }
}
