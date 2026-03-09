package com.example.repository;

import com.example.model.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    List<Vehicle> findByStatus(String status);

    List<Vehicle> findByType(String type);
}
