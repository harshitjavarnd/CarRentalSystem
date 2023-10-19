package com.assignment.rental.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.rental.dto.CustomerDTO;
import com.assignment.rental.dto.loginDTO;
import com.assignment.rental.entity.Customer;
import com.assignment.rental.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Override
	public void addCustomer(CustomerDTO c) {
		try {
			Customer customer = new Customer();
			customer.setId(c.getId());
			customer.setName(c.getName());
			customer.setContact(c.getContact());
			customer.setAddress(c.getAddress());
			customer.setBlocked(0);
			customer.setPassword(c.getPassword());
			customerRepository.save(customer);
		}
		catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	@Override
	public String deleteCustomer(int id)   
	{  
		try {
			Optional<Customer> cust = customerRepository.findById(id);  
			if(cust.isEmpty()) {
				return "Not Exist";
			}
			customerRepository.deleteById(id);
			return "Customer Deleted Successfully";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}  
	@Override
	public String blockCustomer(int id)   
	{  
		try {
			Optional<Customer> cust = customerRepository.findById(id); 
			if(cust.isEmpty()) {
				return "Not Exist";
			}
			Customer customer = cust.get();
			customer.setBlocked(1);
			customerRepository.save(customer);
			return "Customer Blocked Successfully";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	} 
	@Override
	public int login(loginDTO login) {
		Optional<Customer> user = customerRepository.findById(login.getId());
		Customer cust = user.get();
		String password = cust.getPassword();
		System.out.print(login.getId()+" "+password+" "+cust.getId()+" "+cust.getPassword());
		if(login.getId() == cust.getId() && login.getPassword().equals(password)) {
			return 1;
		}
		return 0;
	}
}
