package com.kata.config.serviceCustomers.processingJson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataProvider {

    public static String readJsonAsString(String file) {
        try {
            return new String(Files
                    .readAllBytes(Paths
                        .get("src/main/resources/body-requests-json/" + file + ".json")));
        } catch (IOException e) {
            throw new RuntimeException("File read error");
        }
    }
}
