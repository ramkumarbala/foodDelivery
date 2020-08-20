package com.restaurant.fooddelivery.controller;

import com.restaurant.fooddelivery.beans.BookingOrder;
import com.restaurant.fooddelivery.beans.FilterRestaurants;
import com.restaurant.fooddelivery.exception.DataNotFoundException;
import com.restaurant.fooddelivery.service.FoodDeliveryService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;

@RestController
@RequestMapping("/foodDelivery")
public class FoodDeliveryController {

    private FoodDeliveryService foodDeliveryService;

    @Autowired
    public FoodDeliveryController(final FoodDeliveryService foodDeliveryService) {
        this.foodDeliveryService = foodDeliveryService;
    }

    @GetMapping("/getCatalog/{pinCode}")
    @Tag(description = "Get the list of restaurants at the specified location", name = "Get Catalog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully returned list of restaurants"),
            @ApiResponse(responseCode = "404", description = "No restaurants found at this location at the moment. Please check back later.")
    })
    public ResponseEntity getDeliveryCatalog(final @PathVariable String pinCode)  {
        return new ResponseEntity(foodDeliveryService.getFoodDeliveryCatalog(pinCode), HttpStatus.OK);
    }

    @PostMapping("/createOrder")
    @Tag(name = "Create food delivery order", description = "Make a food delivery order")
    @ApiResponses(value = {
            @ApiResponse(responseCode= "201", description = "Successfully created an order"),
            @ApiResponse(responseCode= "400", description = "Order already present in database. Use the updateOrder endpoint to update the order.")
    })
    public ResponseEntity createOrder(@Valid @RequestBody BookingOrder createOrderRequest){
        foodDeliveryService.createOrder(createOrderRequest);
        return new ResponseEntity("Successfully created an order", HttpStatus.CREATED);
    }

    @PostMapping("/updateOrder")
    @Tag(name = "Update an order", description = "Update a food delivery order")
    @ApiResponses(value = {
            @ApiResponse(responseCode= "200", description = "Successfully updated the order"),
            @ApiResponse(responseCode= "400", description = "Order not present in database. Use the createOrder endpoint to create the order.")
    })
    public ResponseEntity updateOrder(@Valid @RequestBody BookingOrder updateOrderRequest){
        foodDeliveryService.updateOrder(updateOrderRequest);
        return new ResponseEntity("Successfully updated the order", HttpStatus.OK);
    }

    @DeleteMapping("/cancelOrder/{orderNumber}")
    @Tag(name = "Delete an order", description = "Cancel a food delivery order")
    @ApiResponses(value = {
            @ApiResponse(responseCode= "200", description = "Successfully cancelled the order"),
            @ApiResponse(responseCode= "404", description =  "Order not present in database.")
    })
    public ResponseEntity deleteOrder(final @PathVariable String orderNumber){
        foodDeliveryService.deleteOrder(orderNumber);
        return new ResponseEntity("Successfully cancelled the order", HttpStatus.OK);
    }

    @GetMapping("/retrieveOrder/{orderNumber}")
    @Tag(name = "Retrieve an order", description = "Retrieve a food delivery order")
    @ApiResponses(value = {
            @ApiResponse(responseCode= "200", description =  "Successfully retrieved the order"),
            @ApiResponse(responseCode= "404", description = "Order not present in database.")
    })
    public ResponseEntity retrieveOrder(final @PathVariable String orderNumber){
        return new ResponseEntity(foodDeliveryService.retrieveOrder(orderNumber), HttpStatus.OK);
    }

    @Tag(name = "Retrieve restaurants based on criteria", description = "Retrieve the list of restaurants with the specified criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode= "200", description = "Successfully retrieved."),
            @ApiResponse(responseCode= "404", description = "No restaurants fit the criteria. Please remove one or more filter criteria and search again.")
    })
    @PostMapping("/filterRestaurants")
    public ResponseEntity filterRestaurants(@Valid @RequestBody FilterRestaurants filterRestaurantsRequest){
        return new ResponseEntity(foodDeliveryService.filterRestaurants(filterRestaurantsRequest), HttpStatus.OK);
    }


    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity dataNotFound(final DataNotFoundException ex){
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity badRequest(final BadRequestException ex){
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
