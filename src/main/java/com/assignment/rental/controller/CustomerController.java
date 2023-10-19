package com.assignment.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rental.dto.CustomerDTO;
import com.assignment.rental.dto.loginDTO;
import com.assignment.rental.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController{

	@Autowired
	private CustomerService customerService;
	
	@PostMapping("/save")  
	public ResponseEntity<String> saveCustomer(@RequestBody CustomerDTO customer)   
	{   
		try {
			customerService.addCustomer(customer);
			return new ResponseEntity<String>("Customer Added Successfully", HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<>("Something Went Wrong! Please check the log files", HttpStatus.BAD_REQUEST);
		}
	}  
	@DeleteMapping("/delete/{customerid}")  
	public ResponseEntity<String> deleteCustomer(@PathVariable("customerid") int customerid)   
	{  
		try {
			String message = customerService.deleteCustomer(customerid);
			if(message.equals("Customer Deleted Successfully"))
				return new ResponseEntity<>("Customer Deleted Successfully", HttpStatus.OK);
			return new ResponseEntity<>("Customer doesn't exist", HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Something Went Wrong! Please check the log files", HttpStatus.BAD_REQUEST);
		}  
	} 
	@PutMapping("block/{customerid}")
	public ResponseEntity<String> blockCustomer(@PathVariable("customerid") int customerid)   
	{  	try {
			String message = customerService.blockCustomer(customerid);
			if(message.equals("Customer Blocked Successfully"))
				return new ResponseEntity<>("Customer Blocked Successfully", HttpStatus.OK);
			return new ResponseEntity<>("Customer doesn't exist", HttpStatus.NOT_FOUND);
		}
		catch(Exception e) {
			return new ResponseEntity<>("Something Went Wrong! Please check the log files", HttpStatus.BAD_REQUEST);
		}
	} 
	@PostMapping("/edit")  
	public ResponseEntity<String> editCustomer(@RequestBody CustomerDTO customer)   
	{   
		try {
			customerService.addCustomer(customer);
			return new ResponseEntity<String>("Customer Edited Successfully", HttpStatus.CREATED);
		}
		catch(Exception e){
			return new ResponseEntity<>("Something Went Wrong! Please check the log files", HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("/login")  
	public ResponseEntity<String> login(@RequestBody loginDTO login)   
	{   
		try {
			int i = customerService.login(login);
			if(i > 0)
				return new ResponseEntity<String>("Login Successfully", HttpStatus.OK);
			return new ResponseEntity<String>("Invalid Id or Password", HttpStatus.NOT_FOUND);
		}
		catch(Exception e){
			return new ResponseEntity<>("Something Went Wrong! Please check the log files", HttpStatus.BAD_REQUEST);
		}
	}  
	
}
