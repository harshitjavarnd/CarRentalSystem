package com.assignment.rental.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.rental.entity.Car;

public interface CarRepository extends JpaRepository<Car, Integer>{

}
