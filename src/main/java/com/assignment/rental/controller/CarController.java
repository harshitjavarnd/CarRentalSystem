package com.assignment.rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.rental.dto.CarDTO;
import com.assignment.rental.dto.TransactionDTO;
import com.assignment.rental.service.CarService;

@RestController
@CrossOrigin
@RequestMapping("/car")
public class CarController {
	@Autowired
	private CarService carService;
	
	@PostMapping("/save")  
	public ResponseEntity<String> saveCar(@RequestBody CarDTO car)  
	{   
		try {
			String message = carService.addCar(car);
			if(message.equals("Car Added Successfully")) {
				return new ResponseEntity<String>("Car Added Successfully", HttpStatus.CREATED);
			}
			else {
				return new ResponseEntity<String>("Error Occurred", HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Something went wrong!", HttpStatus.BAD_REQUEST);
		}
	}  
	
	@PostMapping("/bookcar/{id}")  
	public ResponseEntity<String> bookCar(@RequestBody CarDTO car, @PathVariable int id)   
	{   
		try {
			String message = carService.bookCar(car, id);
			if(message.equals("Incorrect Customer ID"))
			{
				return new ResponseEntity<String>("Incorrect Customer ID", HttpStatus.NOT_FOUND);
			}
			else if(message.equals("Incorrect Car ID")) {
				return new ResponseEntity<String>("Incorrect Car ID", HttpStatus.NOT_FOUND);
			}
			else if(message.equals("Car Already Booked!")) {
				return new ResponseEntity<String>("Car Already Booked", HttpStatus.BAD_REQUEST);
			}
			else if(message.equals("You are blocked!")) {
				return new ResponseEntity<String>("You are blocked", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<String>("Car Booked Successfully Successfully", HttpStatus.CREATED);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Something Went Wrong! Please check for log files", HttpStatus.BAD_REQUEST);
		}
	}  
	
	@DeleteMapping("/delete/{carid}")  
	public ResponseEntity<String> deleteBook(@PathVariable("carid") int carid)   
	{  
		try {
			String message = carService.deleteCar(carid);  
			if(message.equals("Data Deleted Successfully")) {
				return new ResponseEntity<String>("Data Deleted Successfully", HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("Incorrect Car ID", HttpStatus.NOT_FOUND);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Something Went Wrong! Please check for log files", HttpStatus.BAD_REQUEST);
		}
	}  
	
	@GetMapping("/booked")
    public ResponseEntity<List<CarDTO>> getBookedCars(){
		List<CarDTO> cars = carService.getBookedCars();
		return new ResponseEntity<>(cars, HttpStatus.OK);
    }
	
	@GetMapping("/available")
    public ResponseEntity<List<CarDTO>> getAvailableCars(){
        List<CarDTO> cars = carService.getAvailableCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }
	
	@PostMapping("/edit")  
	public ResponseEntity<String> editCar(@RequestBody CarDTO car)  
	{   
		try {
			String message = carService.addCar(car);
			if(message.equals("Car Added Successfully")) {
				return new ResponseEntity<String>("Car Edited Successfully", HttpStatus.CREATED);
			}
			else {
				return new ResponseEntity<String>("Error Occurred", HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Something went wrong!", HttpStatus.BAD_REQUEST);
		}
	}  
	@GetMapping("/trans")
    public ResponseEntity<List<TransactionDTO>> getTransactions(){
        List<TransactionDTO> transaction = carService.getTransactions();
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
	
}
