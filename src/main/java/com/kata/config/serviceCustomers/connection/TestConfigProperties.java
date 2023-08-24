package com.kata.config.serviceCustomers.connection;

import java.io.IOException;
import java.util.Properties;

public class TestConfigProperties {

    private static Properties properties;

    public static String getValue(String propertyName) {
            if (properties == null) {
                loadTestProperties();
            }
            return properties.getProperty(propertyName);
    }

    private static void loadTestProperties() {
        properties = new Properties();
        try {
            properties.load(TestConfigProperties.class.getClassLoader()
                    .getResourceAsStream("./test.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test properties");
        }
    }
}
