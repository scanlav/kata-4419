package com.kata.config.serviceCustomers.models;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@JsonPOJOBuilder(withPrefix = "set")
public class Customer {

	private Integer id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String dateOfBirth;
	private Loyalty loyalty;
	private String shopCode;
	private String updatedAt;
	private String createdAt;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return 	Objects.equals(firstName, customer.firstName) &&
				Objects.equals(lastName, customer.lastName) &&
				Objects.equals(phoneNumber, customer.phoneNumber) &&
				Objects.equals(email, customer.email) &&
				Objects.equals(dateOfBirth, customer.dateOfBirth) &&
				Objects.equals(shopCode, customer.shopCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, phoneNumber, email,
				dateOfBirth, loyalty, shopCode, updatedAt, createdAt);
	}
}
