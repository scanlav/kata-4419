package com.kata.config.serviceCustomers.models;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;

@Getter
@JsonPOJOBuilder(withPrefix = "set")
public class Loyalty {

	private String bonusCardNumber;
	private String status;
	private String discountRate;
}
