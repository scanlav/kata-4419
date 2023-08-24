package com.kata.config.serviceCustomers.models;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder(withPrefix = "set")
public class Loyalty {

	private String bonusCardNumber;
	private String status;
	private String discountRate;

	public String getBonusCardNumber() {
		return bonusCardNumber;
	}

	public void setBonusCardNumber(String bonusCardNumber) {
		this.bonusCardNumber = bonusCardNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}
}
