package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rooms")
@Data
public class Room {

    @Id
    private String id;
    private String roomNumber;
    private String category;
    private String status;
    private double pricePerNight;
    private double pricePerHour;
    private int floor;
    private String description;
}
