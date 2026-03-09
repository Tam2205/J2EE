package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicles")
@Data
public class Vehicle {

    @Id
    private String id;
    private String name;
    private String type;
    private String licensePlate;
    private String status;
    private double pricePerDay;
    private double pricePerHour;
    private String description;
}
