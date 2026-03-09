package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "bookings")
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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public String getGuestPhone() { return guestPhone; }
    public void setGuestPhone(String guestPhone) { this.guestPhone = guestPhone; }
    public String getGuestIdCard() { return guestIdCard; }
    public void setGuestIdCard(String guestIdCard) { this.guestIdCard = guestIdCard; }
    public LocalDateTime getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDateTime checkIn) { this.checkIn = checkIn; }
    public LocalDateTime getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDateTime checkOut) { this.checkOut = checkOut; }
    public String getBookingType() { return bookingType; }
    public void setBookingType(String bookingType) { this.bookingType = bookingType; }
    public int getHoursBooked() { return hoursBooked; }
    public void setHoursBooked(int hoursBooked) { this.hoursBooked = hoursBooked; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getRoomCharge() { return roomCharge; }
    public void setRoomCharge(double roomCharge) { this.roomCharge = roomCharge; }
    public double getServiceCharge() { return serviceCharge; }
    public void setServiceCharge(double serviceCharge) { this.serviceCharge = serviceCharge; }
    public double getVehicleCharge() { return vehicleCharge; }
    public void setVehicleCharge(double vehicleCharge) { this.vehicleCharge = vehicleCharge; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public List<ServiceUsage> getServices() { return services; }
    public void setServices(List<ServiceUsage> services) { this.services = services; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
