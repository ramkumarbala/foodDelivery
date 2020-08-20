package com.restaurant.fooddelivery.service;

import com.restaurant.fooddelivery.beans.BookingOrder;
import com.restaurant.fooddelivery.beans.FilterRestaurants;
import com.restaurant.fooddelivery.beans.FoodItem;
import com.restaurant.fooddelivery.beans.Restaurant;
import com.restaurant.fooddelivery.exception.DataNotFoundException;
import com.restaurant.fooddelivery.repository.OrdersRepository;
import com.restaurant.fooddelivery.repository.RestaurantCatalogRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class FoodDeliveryServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private RestaurantCatalogRepository catalogRepository;


    private FoodDeliveryServiceImpl service;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        service = new FoodDeliveryServiceImpl(ordersRepository, catalogRepository);
    }


    @Test
    public void testSuccessfulCatalogRetrieval(){
        // given
        // DB has one restaurant for pincode 600050
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

        final List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        when(catalogRepository.getRestaurantsCatalog()).thenReturn(restaurants);

        // when
        // I make a call to service to retrieve restaurant list for pin code 600050
        final List<Restaurant> actualResponse = service.getFoodDeliveryCatalog("600050");

        // then
        // I get one restaurant details
        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.size());
        assertEquals("So Tasty!", actualResponse.get(0).getName());
    }

    @Test
    public void testSuccessfulCreateOrder(){
        // given
        // when i make an entry in the DB to create new booking order it is successful

        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        doNothing().when(ordersRepository).createOrder(isA(BookingOrder.class));

        // when
        // I call the service to create an order
        service.createOrder(order);


        // then
        // service call is successful and DB entry is made
        verify(ordersRepository, times(1)).createOrder(order);
    }

    @Test
    public void testSuccessfulUpdateOrder(){
        // given
        // when i update a existing booking order in DB, it is successful

        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        when(ordersRepository.getAllOrders()).thenReturn(Collections.singletonList(order));
        doNothing().when(ordersRepository).updateOrder(isA(BookingOrder.class));

        // when
        // I call the service to update an existing order
        service.updateOrder(order);


        // then
        // service call is successful and DB entry is updated
        verify(ordersRepository, times(1)).updateOrder(order);
    }

    @Test
    public void testSuccessfulDeleteOrder(){
        // given
        // when i update the DB to delete a booking order, it is successful
        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        when(ordersRepository.getAllOrders()).thenReturn(Collections.singletonList(order));
        doNothing().when(ordersRepository).deleteOrder(isA(String.class));

        // when
        // I call the service to delete an existing order
        service.deleteOrder("0005");

        // then
        // service call is successful and DB entry is deleted
        verify(ordersRepository, times(1)).deleteOrder("0005");
    }

    @Test
    public void testSuccessfulRetrieveOrder(){
        // given
        // when I query the DB to retrieve a booking order 0005, it is successful and I get the order
        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        when(ordersRepository.retrieveOrder("0005")).thenReturn(order);

        // when
        // I call the service to retrieve an order 0005
        final BookingOrder actualResponse = service.retrieveOrder("0005");

        // then
        // I get a successful response with 200 code and the order details are valid
        assertNotNull(actualResponse);
        assertEquals("0005", actualResponse.getOrderId());
    }

    @Test
    public void testSuccessfulFilter(){
        // given

        final FilterRestaurants filter = new FilterRestaurants( null, null, "600050");
        // DB has restaurant for pincode 600050
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

        final List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        when(catalogRepository.getRestaurantsCatalog()).thenReturn(restaurants);

        // when
        // I make a service call to retrieve restaurants based on pin code 600010
        final List<Restaurant> actualResponse = service.filterRestaurants(filter);

        // then
        // I get a successful response with 200 code and the expected restaurant details
        assertNotNull(actualResponse);
        assertEquals(1, actualResponse.size());
        assertEquals("So Tasty!", actualResponse.get(0).getName());
    }


    @Test(expected = DataNotFoundException.class)
    public void testNoRestaurantsFoundInPinCode(){
        // given
        // DB has no restaurants for pin code 600010
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

        final List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        when(catalogRepository.getRestaurantsCatalog()).thenReturn(restaurants);
        // when
        // I make a service call to get list of restaurants for pin code 600010
        service.getFoodDeliveryCatalog("600010");

        // then
        // I get an exception
    }

    @Test(expected = BadRequestException.class)
    public void testCreateDuplicateOrder(){
        // given
        // DB has one entry with id 0005
        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        when(ordersRepository.getAllOrders()).thenReturn(Collections.singletonList(order));

        // when
        // I attempt to create a duplicate order in the service
         service.createOrder(order);

        // then
        // Exception occurs

    }

    @Test(expected = BadRequestException.class)
    public void testUpdateNonExistentOrder(){
        // given
        // DB has no entry with id 0004
        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        when(ordersRepository.getAllOrders()).thenReturn(Collections.singletonList(order));

        // when
        // I attempt to update a non-existent order in the service
        final BookingOrder requestOrder = new BookingOrder("CUST005", "0004", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        service.updateOrder(requestOrder);

        // then
        // Exception occurs
    }

    @Test(expected = DataNotFoundException.class)
    public void testDeleteNonExistentOrder(){
        // given
        // DB has no entry with id 0001
        final BookingOrder order = new BookingOrder("CUST005", "0005", "restaurant4", Arrays.asList(new FoodItem("Chicken Biriyani", 150.0), new FoodItem("Mutton Biriyani", 205.0)));
        when(ordersRepository.getAllOrders()).thenReturn(Collections.singletonList(order));

        // when
        // I attempt to delete a non-existent order in the service
        service.deleteOrder("0001");


        // then
        // Exception occurs
    }

    @Test(expected = DataNotFoundException.class)
    public void testServiceFailsToRetrieveListForCriteria(){
        // given
        final FilterRestaurants filter = new FilterRestaurants(null, null, "600010");
        // DB has no restaurant for pincode 600010
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

        final List<Restaurant> restaurants = Arrays.asList(restaurant1, restaurant2);

        when(catalogRepository.getRestaurantsCatalog()).thenReturn(restaurants);

        // when
        // I make a service call to retrieve restaurants based on pin code 600010
        final List<Restaurant> actualResponse = service.filterRestaurants(filter);

        // then
        // Exception occurs
    }
}
