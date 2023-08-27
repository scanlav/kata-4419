package com.kata.config.serviceCustomers.preparationResponses;

import com.kata.config.serviceCustomers.models.Customer;
import com.kata.config.serviceCustomers.specifications.Specifications;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ResponsesApiCustomers {

    public static Response responseGetAllCustomers() {
        return given().spec(Specifications.reqSpecGetAllCustomers())
                .get();
    }

    public static <T> Response responseGetCustomersId(T id) {
        return given().spec(Specifications.reqSpecGetCustomersId(id))
                .get();
    }

    public static <T> Response responseGetCustomerByPhoneNumber(T phoneNumber) {
        return given().spec(Specifications.reqSpecGetCustomersByPhoneNumber(phoneNumber))
                .get();
    }

    public static <T> Response responsePutCustomers(T id, String body, String phoneNumber) {
        return  given().spec(Specifications.reqSpecPutCustomers(id, body, phoneNumber))
                .put();
    }

    public static <T> Response responseDeleteCustomers(T id) {
        return given().spec(Specifications.reqSpecDeleteCustomers(id))
                .delete();
    }

    public static Response responsePostCustomers(String body, String phoneNumber) {
        return given().spec(Specifications.reqSpecPostCustomers(body, phoneNumber))
                .post();
    }

    public static Response responsePostCustomers(Customer body) {
        return given().spec(Specifications.reqSpecPostCustomers(body))
                .post();
    }

    public static Customer saveAsCustomer(Response response) {
        return response
                .then()
                .statusCode(201)
                .extract().as(Customer.class);
    }
}
