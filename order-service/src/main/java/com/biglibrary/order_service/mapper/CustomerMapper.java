package com.biglibrary.order_service.mapper;

import com.biglibrary.order_service.dto.CustomerDTO;
import com.biglibrary.order_service.models.embedded_models.Customer;

public class CustomerMapper {

	public static CustomerDTO mapToCustomerDTO(Customer customer) {
		CustomerDTO customerDTO = new CustomerDTO();

		customerDTO.setEmail(customer.email());
		customerDTO.setName(customer.name());
		customerDTO.setPhone(customer.phone());

		return customerDTO;
	}

	public static Customer mapToCustomer(CustomerDTO customerDTO) {
		return new Customer(customerDTO.getName(), customerDTO.getEmail(), customerDTO.getPhone());
	}

}
