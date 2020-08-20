package com.restaurant.fooddelivery.exception;

public class DataNotFoundException extends RuntimeException {

    private String orderNumber;
    private String message;

    public DataNotFoundException(String orderNumber, String message) {
        this.orderNumber = orderNumber;
        this.message = message;
    }

    public DataNotFoundException(String message) {
        this.message = message;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
