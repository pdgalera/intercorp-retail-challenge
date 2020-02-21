package com.intercorp.customermanager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.intercorp.customermanager.model.Customer;
import com.intercorp.customermanager.model.CustomerStatistics;
import com.intercorp.customermanager.model.FullDataCustomer;
import com.intercorp.customermanager.services.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "Customer Manager")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@RequestMapping(value = "creacliente", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Create Customer")
	public ResponseEntity<Customer> createCustomer(@Validated @RequestBody Customer customer) {

		customerService.save(customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@RequestMapping(value = "kpideclientes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Customer Statistics")
	public ResponseEntity<CustomerStatistics> getCustomerStatistics() {

		return new ResponseEntity<>(customerService.getCustomerStatistics(), HttpStatus.OK);
	}

	@RequestMapping(value = "listclientes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Customer List")
	public ResponseEntity<List<FullDataCustomer>> listCustomers() {

		return new ResponseEntity<>(customerService.listCustomers(), HttpStatus.OK);
	}

}
