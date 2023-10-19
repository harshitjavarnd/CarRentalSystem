package com.assignment.rental.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.assignment.rental.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.rental.dto.CarDTO;
import com.assignment.rental.dto.TransactionDTO;
import com.assignment.rental.entity.Car;
import com.assignment.rental.repository.CarRepository;
import com.assignment.rental.repository.CustomerRepository;
@Service
public class CarServiceImpl implements CarService {
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public String addCar(CarDTO c) {
		try {
			Car car = new Car();
			car.setId(c.getId());
			car.setName(c.getName());
			car.setType(c.getType());
			car.setStatus("Available");
			carRepository.save(car);
			return "Car Added Successfully";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
	@Override
	public String bookCar(CarDTO c, int id) {
		Optional<Car> car = carRepository.findById(c.getId());
		Optional<Customer> cust = customerRepository.findById(id);
		Customer customer = cust.get();
		Car updateCar = car.get();
		if(cust.isEmpty()) {
			return "Incorrect Customer ID";
		}
		if(car.isEmpty()) {
			return "Incorrect Car ID";
		}
		if(updateCar.getStatus().equals("Booked")) {
			return "Car Already Booked!";
		}
		if(customer.getBlocked() == 1) {
			return "You are blocked!";
		}
		try {
			int type;
		
			updateCar.setDistance(c.getDistance());
			if(c.getDuration().equalsIgnoreCase("daily")) {
				updateCar.setDuration(1);
			}
			else if(c.getDuration().equalsIgnoreCase("weekly")) {
				updateCar.setDuration(7);
			}
			else {
				updateCar.setDuration(30);
			}
			if(updateCar.getType().equalsIgnoreCase("Non AC")) {
				type = 2;
			}
			else {
				type = 4;
			}
			int fare = calculateFare(updateCar.getDuration(), updateCar.getDistance(), type);
			updateCar.setFare(fare);
			updateCar.setStatus("Booked");
			updateCar.setCustomer(customer);
			updateCar.setPayment(c.getPayment());
			carRepository.save(updateCar);
			return "Car Booked Successfully";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}
	@Override
	public int calculateFare(int duration, int distance, int status) {
		return duration * distance * status * 5;
	}
	
	@Override
	public String deleteCar(int id)   
	{  
		try {
			carRepository.deleteById(id);
			return "Data Deleted Successfully";
		}
		catch(Exception e) {
			return e.getMessage();
		}
	}  
	
	@Override
    public List<CarDTO> getBookedCars() {
		List<CarDTO> cars = new ArrayList<CarDTO>();
        for(Car c: carRepository.findAll()) {
        	if(c.getStatus().equalsIgnoreCase("Booked")) {
        		if(c.getCustomer() != null) {
		        	CarDTO obj = new CarDTO();
		        	obj.setId(c.getId());
		        	obj.setName(c.getName());
		        	obj.setType(c.getType());
		        	obj.setStatus(c.getStatus());
		        	obj.setDistance(c.getDistance());
		        	obj.setDuration(c.getDuration()+"");
		        	obj.setFare(c.getFare());
		        	obj.setCust_id(c.getCustomer().getId());
		        	obj.setPayment(c.getPayment());
		        	cars.add(obj);
        		}
        	}
        }
        return cars;
    }
	public List<CarDTO> getAvailableCars() {
		List<CarDTO> cars = new ArrayList<CarDTO>();
        for(Car c: carRepository.findAll()) {
        	if(c.getStatus().equalsIgnoreCase("Available"))
        	{
	        	CarDTO obj = new CarDTO();
	        	obj.setId(c.getId());
	        	obj.setName(c.getName());
	        	obj.setType(c.getType());
	        	obj.setStatus(c.getStatus());
	        	cars.add(obj);
        	}
        }
        return cars;
    }
	public List<TransactionDTO> getTransactions(){
		List<Car> cars = carRepository.findAll();
		List<TransactionDTO> trans = new ArrayList<>();
		for(Car car: cars) {
			if (car.getCustomer() != null) {
				TransactionDTO t = new TransactionDTO();
				t.setCustomerId(car.getCustomer().getId());
				t.setCustomerName(car.getCustomer().getName());
				t.setContact(car.getCustomer().getContact());
				t.setAddress(car.getCustomer().getAddress());
				t.setCarId(car.getId());
				t.setCarName(car.getName());
				t.setDistance(car.getDistance());
				t.setDuration(car.getDuration());
				t.setPayment(car.getPayment());
				t.setStatus(car.getStatus());
				t.setType(car.getType());
				t.setFare(car.getFare());
				
				trans.add(t);
			}
		}
		return trans;
	}
}
