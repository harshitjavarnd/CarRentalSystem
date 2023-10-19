package com.assignment.rental.service;

import java.util.List;

import com.assignment.rental.dto.CarDTO;
import com.assignment.rental.dto.TransactionDTO;

public interface CarService {
	public String addCar(CarDTO c);
	public String deleteCar(int id);
	public String bookCar(CarDTO c, int id);
	public int calculateFare(int duration, int distance, int status);
    public List<CarDTO> getBookedCars();
    public List<CarDTO> getAvailableCars();
    public List<TransactionDTO> getTransactions();
}
