package com.kata.config.serviceCustomers.constants;

import static com.kata.config.serviceCustomers.connection.TestConfigProperties.getValue;

public class ConstantsService {

    public final static String BASE_PATH_API = getValue("api.base.url");
    public final static String CUSTOMERS = "/customers";
    public final static String CUSTOMERS_ID = "/customers/";
    public final static String PATH_GET_CUSTOMER_BY_PHONE_NUMBER = "/customers/filter";
    public final static String BAD_REQUEST = "Bad Request";
    public final static String NOT_FOUND = "Customer not found";
    public final static String[] MISSING_FIELDS = {
            "Mandatory field missing: phoneNumber",
            "Mandatory field missing: lastName",
            "Mandatory field missing: firstName"
    };
}
