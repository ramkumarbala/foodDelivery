README 
======

Overview
--------

* Food Delivery API is a RESTful API to
  * Retrieve list of restaurants based on location
  * Create food orders, update existing orders, retrieve orders and delete orders
  * Narrow down the list of restaurants to fit user's criteria. There are three criteria available to the user:
    * Rating - If user specifies a rating, API will retrieve restaurants with rating equal to above the specified rating
    * Price - If user specifies a price, API will retrieve restaurants with item having prices equal to below the specified price
    * Pincode - If user specifies the pin code, API will narrow down restaurants only from specified location
  * User may choose any combination of the three filters.


Dependencies
------------

* http://openjdk.java.net/ [Java 8] 
* https://gradle.org/install/ [Gradle] 

Running
-------

 To clean and run all tests, use `gradle clean test`
 
 To run, use `gradle run`
 
 Sample query
 `curl -X GET "http://localhost:8090/foodDelivery/getCatalog/600010" -H "accept: */*"`
 
Swagger
-------

 * <http://localhost:8090/swagger-ui.html>
