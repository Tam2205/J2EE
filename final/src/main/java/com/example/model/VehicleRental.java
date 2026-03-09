package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "vehicle_rentals")
@Data
public class VehicleRental {

    @Id
    private String id;
    private String vehicleId;
    private String vehicleName;
    private String vehicleLicensePlate;
    private String bookingId;
    private String roomId;
    private String roomNumber;
    private String guestName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean active;
    private double totalAmount;
    private LocalDateTime createdAt;
}
