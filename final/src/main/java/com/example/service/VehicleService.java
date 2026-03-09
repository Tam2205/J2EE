package com.example.service;

import com.example.model.Booking;
import com.example.model.Vehicle;
import com.example.model.VehicleRental;
import com.example.repository.BookingRepository;
import com.example.repository.VehicleRentalRepository;
import com.example.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleRentalRepository vehicleRentalRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findByStatus("AVAILABLE");
    }

    public Vehicle getVehicleById(String id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public Vehicle save(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<VehicleRental> getActiveRentals() {
        return vehicleRentalRepository.findByActive(true);
    }

    public List<VehicleRental> getRentalsByBooking(String bookingId) {
        return vehicleRentalRepository.findByBookingId(bookingId);
    }

    public VehicleRental createRental(VehicleRental rental) {
        rental.setActive(true);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setStartTime(LocalDateTime.now());

        Vehicle vehicle = vehicleRepository.findById(rental.getVehicleId()).orElse(null);
        if (vehicle != null) {
            vehicle.setStatus("RENTED");
            rental.setVehicleName(vehicle.getName());
            rental.setVehicleLicensePlate(vehicle.getLicensePlate());
            vehicleRepository.save(vehicle);
        }

        if (rental.getBookingId() != null && !rental.getBookingId().isEmpty()) {
            Booking booking = bookingRepository.findById(rental.getBookingId()).orElse(null);
            if (booking != null) {
                rental.setRoomId(booking.getRoomId());
                rental.setRoomNumber(booking.getRoomNumber());
                rental.setGuestName(booking.getGuestName());

                double vehicleCharge = booking.getVehicleCharge() + rental.getTotalAmount();
                booking.setVehicleCharge(vehicleCharge);
                booking.setTotalAmount(booking.getRoomCharge() + booking.getServiceCharge() + vehicleCharge);
                bookingRepository.save(booking);
            }
        }

        return vehicleRentalRepository.save(rental);
    }

    public VehicleRental returnVehicle(String rentalId) {
        VehicleRental rental = vehicleRentalRepository.findById(rentalId).orElse(null);
        if (rental == null) return null;

        rental.setActive(false);
        rental.setEndTime(LocalDateTime.now());

        Vehicle vehicle = vehicleRepository.findById(rental.getVehicleId()).orElse(null);
        if (vehicle != null) {
            vehicle.setStatus("AVAILABLE");
            vehicleRepository.save(vehicle);
        }

        return vehicleRentalRepository.save(rental);
    }

    public long countActiveRentals() {
        return vehicleRentalRepository.findByActive(true).size();
    }
}
