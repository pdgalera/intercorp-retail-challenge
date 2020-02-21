package com.intercorp.customermanager.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.intercorp.customermanager.model.Customer;
import com.intercorp.customermanager.model.CustomerStatistics;
import com.intercorp.customermanager.model.FullDataCustomer;

@Service
public class CustomerService {

	@Value("${estimated.death.age}")
	private int estimatedDeathAge;

	private static List<Customer> customers = new ArrayList<>();

	public void save(Customer customer) {
		customers.add(customer);
	}

	public List<FullDataCustomer> listCustomers() {
		List<FullDataCustomer> responseCustomerList = new LinkedList<>();
		customers.forEach(customer -> {
			responseCustomerList.add(new FullDataCustomer(customer, this.estimatedDeathAge));
		});
		return responseCustomerList;
	}

	public CustomerStatistics getCustomerStatistics() {
		
		if (customers.isEmpty()) {
			return new CustomerStatistics(Double.NaN, Double.NaN);
		}
		
		double averageAge = customers.stream().mapToDouble(Customer::getAge).average().orElse(Double.NaN);

		double deviationSquaredAcc = 0;

		for (Customer customer : customers) {
			deviationSquaredAcc += Math.pow((customer.getAge() - averageAge), 2);
		}

		double standardDeviation = Math.sqrt(deviationSquaredAcc / customers.size());
		
		return new CustomerStatistics(averageAge, standardDeviation);
	}
	
	public static void clearCustomers() {
		customers.clear();
	}
}
