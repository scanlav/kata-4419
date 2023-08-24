package com.kata.config.serviceCustomers.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class IsToday<T> extends BaseMatcher<T> {
    @Override
    public boolean matches(Object actual) {
        LocalDateTime dateTime = LocalDateTime.parse(actual.toString(),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDate date = dateTime.toLocalDate();

        return Objects.equals(date, LocalDate.now());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Today");
    }
}
