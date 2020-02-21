package com.intercorp.customermanager;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.intercorp.customermanager.model.Customer;
import com.intercorp.customermanager.model.CustomerStatistics;
import com.intercorp.customermanager.model.FullDataCustomer;
import com.intercorp.customermanager.services.CustomerService;

public class CustomerManagerTest {

	CustomerService customerService;

	@Before
	public void setUp() {
		customerService = new CustomerService();
		CustomerService.clearCustomers();
	}

	@Test
	public void createCustomerTest() {
		Customer customer = new Customer();
		customer.setName("Juan");
		customer.setSurename("Perez");
		customer.setAge(30);
		customer.setDateOfBirth(LocalDate.of(1990, 3, 15));

		customerService.save(customer);

		List<FullDataCustomer> customerList = customerService.listCustomers();
		assertEquals("There should be exactly one customer", 1, customerList.size());

	}

	@Test
	public void customerListTest() {
		ReflectionTestUtils.setField(customerService, "estimatedDeathAge", 90);
		Customer customer = new Customer();
		customer.setName("Juan");
		customer.setSurename("Perez");
		customer.setAge(30);
		customer.setDateOfBirth(LocalDate.of(1990, 3, 15));
		customerService.save(customer);

		customer = new Customer();
		customer.setName("Marcela");
		customer.setSurename("Perez");
		customer.setAge(35);
		customer.setDateOfBirth(LocalDate.of(1985, 8, 1));
		customerService.save(customer);

		List<FullDataCustomer> customerList = customerService.listCustomers();

		assertEquals("Number of customers is wrong", 2, customerList.size());

		customerList.forEach(customerResponse -> {
			LocalDate estimatedDeathDate;
			if ("Juan".equals(customerResponse.getName())) {
				estimatedDeathDate = LocalDate.of(2080, 3, 15);
			} else {
				estimatedDeathDate = LocalDate.of(2075, 8, 1);
			}

			assertEquals("Wrong estimated date of death", estimatedDeathDate, customerResponse.getEstimatedDeathDate());
		});
	}

	@Test
	public void customerStatisticsTest() {

		runStatisticsTest(3d, 1.8708286933869707, new int[] { 1, 2, 3, 6 });
		CustomerService.clearCustomers();

		runStatisticsTest(5d, 0d, new int[] { 5, 5, 5, 5, 5 });
		CustomerService.clearCustomers();

		runStatisticsTest(Double.NaN, Double.NaN, new int[] {});

	}

	public void runStatisticsTest(Double averageAge, Double standardDeviation, int... ages) {
		CustomerService customerService = new CustomerService();
		for (int age : ages) {
			Customer customer = new Customer();
			customer.setAge(age);
			customerService.save(customer);
		}

		CustomerStatistics customerStatistics = customerService.getCustomerStatistics();

		assertEquals("Average Age is not the expected one", averageAge, customerStatistics.getAverageAge());
		assertEquals("Standard Deviation is not the expected one", standardDeviation,
				customerStatistics.getStandardDeviation());
	}

}
