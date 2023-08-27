package com.kata.config.serviceCustomers.specifications;

import com.kata.config.serviceCustomers.constants.ConstantsService;
import com.kata.config.serviceCustomers.models.Customer;
import com.kata.config.serviceCustomers.processingJson.DataProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specifications {

    public static RequestSpecBuilder specBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(ConstantsService.BASE_PATH_API)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());
    }

    public static RequestSpecification reqSpecGetAllCustomers() {
        return specBuilder()
                .setBasePath(ConstantsService.CUSTOMERS)
                .build();
    }

    public static <T> RequestSpecification reqSpecGetCustomersId(T id) {
        return specBuilder()
                .setBasePath(ConstantsService.CUSTOMERS_ID + id)
                .build();
    }

    public static <T> RequestSpecification reqSpecGetCustomersByPhoneNumber(T phoneNumber) {
        return specBuilder()
                .setBasePath(ConstantsService.PATH_GET_CUSTOMER_BY_PHONE_NUMBER)
                .addQueryParam("phoneNumber", phoneNumber)
                .build();
    }

    public static <T> RequestSpecification reqSpecPutCustomers(T id, String body, String phoneNumber) {
        return specBuilder()
                .setBasePath(ConstantsService.CUSTOMERS_ID + id)
                .setBody(DataProvider.readJsonAsString(body)
                        .replace("{phoneNumber}", phoneNumber))
                .build();
    }

    public static <T> RequestSpecification reqSpecDeleteCustomers(T id) {
        return specBuilder()
                .setBasePath(ConstantsService.CUSTOMERS_ID + id)
                .build();
    }

    public static RequestSpecification reqSpecPostCustomers(String body, String phoneNumber) {
        return specBuilder()
                .setBody(DataProvider.readJsonAsString(body)
                        .replace("{phoneNumber}", phoneNumber))
                .setBasePath(ConstantsService.CUSTOMERS)
                .build();
    }

    public static RequestSpecification reqSpecPostCustomers(Customer body) {
        return specBuilder()
                .setBody(body)
                .setBasePath(ConstantsService.CUSTOMERS)
                .build();
    }
}
