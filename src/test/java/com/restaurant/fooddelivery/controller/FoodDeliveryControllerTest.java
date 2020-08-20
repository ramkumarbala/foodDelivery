package com.restaurant.fooddelivery.controller;

import com.restaurant.fooddelivery.beans.BookingOrder;
import com.restaurant.fooddelivery.beans.FilterRestaurants;
import com.restaurant.fooddelivery.beans.FoodItem;
import com.restaurant.fooddelivery.beans.Restaurant;
import com.restaurant.fooddelivery.exception.DataNotFoundException;
import com.restaurant.fooddelivery.service.FoodDeliveryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class FoodDeliveryControllerTest {

    @Mock
    private FoodDeliveryService service;

    private FoodDeliveryController controller;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new FoodDeliveryController(service);
    }

    @Test
    public void testSuccessfulRestaurantRetrieval(){
        // given
        // Service responds with one restaurant for pin code 600010
        List<Restaurant> expectedResponse = Collections.singletonList(
         new Restaurant("Nippon Foods",
                "restaurant3", "3, Taylors Road",
                "600010",
                5,
                Arrays.asList(new FoodItem("Sushi", 250.0), new FoodItem("Ramen", 275.0), new FoodItem("Tempura", 120.0))));

        when(service.getFoodDeliveryCatalog("600010")).thenReturn(expectedResponse);

        // when
        // I request the API for list of restaurants for pin code 600010
        final ResponseEntity actualResponse = controller.getDeliveryCatalog("600010");

        // then
        // I get a success response with 200 code and expected restaurant details
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatusCodeValue());
        List<Restaurant> restaurantResponse = (List<Restaurant>) actualResponse.getBody();
        assertNotNull(restaurantResponse);
        assertEquals(1, restaurantResponse.size());
        assertEquals("Nippon Foods", restaurantResponse.get(0).getName());
    }

    @Test
    public void testSuccessfulCreateOrder(){
        // given
        // when i call the service to create new booking order it is successful

        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        doNothing().when(service).createOrder(isA(BookingOrder.class));

        // when
        // I create an order in the API
        final ResponseEntity actualResponse = controller.createOrder(order);


        // then
        // service call is successful and code returned is 201
        verify(service, times(1)).createOrder(order);
        assertNotNull(actualResponse);
        assertEquals(201, actualResponse.getStatusCodeValue());
    }

    @Test
    public void testSuccessfulUpdateOrder(){
        // given
        // when i call the service to update a booking order, it is successful

        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        doNothing().when(service).updateOrder(isA(BookingOrder.class));

        // when
        // I update an order in the API
        final ResponseEntity actualResponse = controller.updateOrder(order);


        // then
        // service call is successful and code returned is 200
        verify(service, times(1)).updateOrder(order);
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatusCodeValue());
    }

    @Test
    public void testSuccessfulDeleteOrder(){
        // given
        // when i call the service to delete a booking order, it is successful

        doNothing().when(service).deleteOrder(isA(String.class));

        // when
        // I delete an order in the API
        final ResponseEntity actualResponse = controller.deleteOrder("0001");


        // then
        // service call is successful and code returned is 200
        verify(service, times(1)).deleteOrder("0001");
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatusCodeValue());
    }

    @Test
    public void testSuccessfulRetrieveOrder(){
        // given
        // when i call the service to retrieve a booking order 0005, it is successful and I get the order
        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        when(service.retrieveOrder("0005")).thenReturn(order);

        // when
        // I call the API to retrieve an order 0005
        final ResponseEntity actualResponse = controller.retrieveOrder("0005");

        // then
        // I get a successful response with 200 code and the order details are valid
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatusCodeValue());
        final BookingOrder actualRetrievedOrder = (BookingOrder) actualResponse.getBody();
        assertNotNull(actualRetrievedOrder);
        assertEquals("0005", actualRetrievedOrder.getOrderId());
    }

    @Test
    public void testSuccessfulFilter(){
        // given
        // the service call to filter restaurants based on criteria postcode is successful and I get one restaurant

        final FilterRestaurants filter = new FilterRestaurants( null, null, "600010");
        List<Restaurant> expectedResponse = Collections.singletonList(
                new Restaurant("Nippon Foods",
                        "restaurant3", "3, Taylors Road",
                        "600010",
                        5,
                        Arrays.asList(new FoodItem("Sushi", 250.0), new FoodItem("Ramen", 275.0), new FoodItem("Tempura", 120.0))));

        when(service.filterRestaurants(filter)).thenReturn(expectedResponse);

        // when
        // I make a call to API to retrieve restaurants based on pin code 600010
        final ResponseEntity actualResponse = controller.filterRestaurants(filter);

        // then
        // I get a successful response with 200 code and the expected restaurant details
        assertNotNull(actualResponse);
        assertEquals(200, actualResponse.getStatusCodeValue());
        List<Restaurant> restaurantResponse = (List<Restaurant>) actualResponse.getBody();
        assertNotNull(restaurantResponse);
        assertEquals(1, restaurantResponse.size());
        assertEquals("Nippon Foods", restaurantResponse.get(0).getName());
    }

    @Test(expected = DataNotFoundException.class)
    public void testNoRestaurantsFoundInPinCode(){
        // given
        // Service responds with no restaurants for pin code 600010
        when(service.getFoodDeliveryCatalog("600010")).thenThrow(new DataNotFoundException("No restaurants found at this location at the moment. Please check back later."));

        // when
        // I request the API for list of restaurants for pin code 600010
        final ResponseEntity actualResponse = controller.getDeliveryCatalog("600010");

        // then
        // I get a failure response with 404 code
        assertNotNull(actualResponse);
        assertEquals(404, actualResponse.getStatusCodeValue());
    }

    @Test(expected = BadRequestException.class)
    public void testCreateDuplicateOrder(){
        // given
        // when i call the service to create a new booking order with existing id it fails

        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        doThrow(BadRequestException.class).when(service).createOrder(isA(BookingOrder.class));

        // when
        // I attempt to create a duplicate order in the API
        final ResponseEntity actualResponse = controller.createOrder(order);


        // then
        // The code returned is 201
        verify(service, times(1)).createOrder(order);
        assertNotNull(actualResponse);
        assertEquals(400, actualResponse.getStatusCodeValue());
    }

    @Test(expected = BadRequestException.class)
    public void testUpdateNonExistentOrder(){
        // given
        // when i call the service to update a booking order which does not exist in the DB it fails

        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        doThrow(BadRequestException.class).when(service).updateOrder(isA(BookingOrder.class));

        // when
        // I attempt to update a non-existent order in the API
        final ResponseEntity actualResponse = controller.updateOrder(order);


        // then
        // The code returned is 400
        verify(service, times(1)).updateOrder(order);
        assertNotNull(actualResponse);
        assertEquals(400, actualResponse.getStatusCodeValue());
    }

    @Test(expected = DataNotFoundException.class)
    public void testDeleteNonExistentOrder(){
        // given
        // when i call the service to delete a booking order which does not exist in the DB it fails

        doThrow(DataNotFoundException.class).when(service).deleteOrder(isA(String.class));

        // when
        // I attempt to delete a non-existent order in the API
        final ResponseEntity actualResponse = controller.deleteOrder("0001");


        // then
        // The code returned is 404
        verify(service, times(1)).deleteOrder("0001");
        assertNotNull(actualResponse);
        assertEquals(404, actualResponse.getStatusCodeValue());
    }

    @Test(expected = DataNotFoundException.class)
    public void testServiceFailsToRetrieveListForCriteria(){
        // given
        // Service responds with no restaurants for filter with pin code 600010
        final FilterRestaurants filter = new FilterRestaurants(null, null, "600010");
        when(service.filterRestaurants(filter)).thenThrow(new DataNotFoundException("No restaurants fit the criteria. Please remove one or more filter criteria and search again."));

        // when
        // I request the API for list of restaurants for pin code 600010
        final ResponseEntity actualResponse = controller.filterRestaurants(filter);

        // then
        // I get a failure response with 404 code
        assertNotNull(actualResponse);
        assertEquals(404, actualResponse.getStatusCodeValue());
    }
}