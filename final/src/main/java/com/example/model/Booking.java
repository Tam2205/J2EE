package com.example.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "bookings")
@Data
public class Booking {

    @Id
    private String id;
    private String roomId;
    private String roomNumber;
    private String guestName;
    private String guestPhone;
    private String guestIdCard;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String bookingType;
    private int hoursBooked;
    private String status;
    private double roomCharge;
    private double serviceCharge;
    private double vehicleCharge;
    private double totalAmount;
    private String notes;
    private List<ServiceUsage> services = new ArrayList<>();
    private LocalDateTime createdAt;
}
