package com.kata.config.serviceCustomers.models;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPOJOBuilder(withPrefix = "set")
public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String dateOfBirth;
	private Loyalty loyalty;
	private String shopCode;
	private String updatedAt;
	private String createdAt;
}
