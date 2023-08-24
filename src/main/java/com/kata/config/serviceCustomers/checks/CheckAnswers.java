package com.kata.config.serviceCustomers.checks;

import com.kata.config.serviceCustomers.matchers.DateMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

public class CheckAnswers {

    public static void checkRequiredFieldsCorrectCreatedCustomers(Response response,
                                                                  String firstName, String lastName,
                                                                  String phoneNumber) {
        response
                .then()
                .body("id", Matchers.notNullValue())
                .body("firstName", Matchers.equalTo(firstName))
                .body("lastName", Matchers.equalTo(lastName))
                .body("phoneNumber", Matchers.equalTo(phoneNumber))
                .body("updatedAt", DateMatchers.isToday())
                .body("createdAt", DateMatchers.isToday());
    }

    public static void checkAllFieldsCorrectCreatedCustomer(Response response, String firstName,
                                                            String lastName, String phoneNumber, String email,
                                                            String dateOfBirth) {
        checkRequiredFieldsCorrectCreatedCustomers(response, firstName, lastName, phoneNumber);
        response
                .then()
                .body("email", Matchers.equalTo(email))
                .body("dateOfBirth", Matchers.equalTo(dateOfBirth))
                .body("loyalty.bonusCardNumber", Matchers.notNullValue())
                .body("loyalty.status", Matchers.notNullValue())
                .body("loyalty.discountRate", Matchers.notNullValue());
    }

    public static void checkFieldCustomer(Response response, String field, String value) {
        response
                .then()
                .body(field, Matchers.equalTo(value));
    }

    public static boolean checkValidCustomer(String expected, String actual) {
        return expected.equals(actual);
    }

    public static void checkStatusCode(Response response, Integer statusCode) {
        response
                .then()
                .statusCode(statusCode);
    }

    public static void checkErrorMessage(Response response, String errors) {
        if (response.statusCode() == 404) {
            response
                    .then()
                    .body("errors", Matchers.hasItem(errors));
        } else if (response.statusCode() == 400) {
            response
                    .then()
                    .body("error", Matchers.equalTo(errors));
        }
    }

    public static void checkErrorMessage(Response response, String[] errors) {
        for (String error : errors) {
            response
                    .then()
                    .body("errors", Matchers.hasItem(error));
        }
    }

    public static void checkSchemaValidate(Response response) {
        response
                .then()
                .body(JsonSchemaValidator
                        .matchesJsonSchemaInClasspath("schema/customer-schema.json"));
    }
}
