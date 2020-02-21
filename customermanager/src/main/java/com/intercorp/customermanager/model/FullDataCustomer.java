package com.intercorp.customermanager.model;

import java.time.LocalDate;

import io.swagger.annotations.ApiModelProperty;

public class FullDataCustomer extends Customer {

	@ApiModelProperty(example = "1990-01-01")
	private LocalDate estimatedDeathDate;
	
	public FullDataCustomer(Customer customer, int estimatedDeathAge) {
		setName(customer.getName());
		setSurename(customer.getSurename());
		setAge(customer.getAge());
		setDateOfBirth(customer.getDateOfBirth());
		this.estimatedDeathDate = customer.getDateOfBirth().plusYears(estimatedDeathAge); 
	}

	public LocalDate getEstimatedDeathDate() {
		return estimatedDeathDate;
	}

	public void setEstimatedDeathDate(LocalDate estimatedDeathDate) {
		this.estimatedDeathDate = estimatedDeathDate;
	}

}
