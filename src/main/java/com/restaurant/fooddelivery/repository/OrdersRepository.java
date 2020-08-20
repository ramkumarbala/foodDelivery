package com.restaurant.fooddelivery.repository;

import com.restaurant.fooddelivery.beans.BookingOrder;

import java.util.List;

public interface OrdersRepository {

    BookingOrder retrieveOrder(String orderId);

    void createOrder(BookingOrder order);

    void updateOrder(BookingOrder order);

    void deleteOrder(BookingOrder order);

    void deleteOrder(String orderId);

    List<BookingOrder> getAllOrders();
}
