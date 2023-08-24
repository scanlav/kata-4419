package com.kata.config.serviceCustomers.preparationDataCustomers;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import static com.kata.config.serviceCustomers.constants.ConstantsJson.*;

public class ServiceValues {

    public static String getFieldResponse(Response response, String field) {
        return response
                .getBody().jsonPath().getString(field);
    }

    public static String getId(Response response) {
        return getFieldResponse(response, ID);
    }

    public static String getPhoneNumber(Response response) {
        return getFieldResponse(response, PHONE_NUMBER);
    }

    public static String randomNumber() {
        return "+7" + RandomStringUtils.randomNumeric(10);
    }
}
