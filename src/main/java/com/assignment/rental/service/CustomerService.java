package com.assignment.rental.service;

import com.assignment.rental.dto.CustomerDTO;
import com.assignment.rental.dto.loginDTO;

public interface CustomerService {
	public void addCustomer(CustomerDTO c);
	public String deleteCustomer(int id);
	public String blockCustomer(int id);
	public int login(loginDTO login);
}
