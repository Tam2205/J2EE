package com.example.repository;

import com.example.model.VehicleRental;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VehicleRentalRepository extends MongoRepository<VehicleRental, String> {

    List<VehicleRental> findByActive(boolean active);

    List<VehicleRental> findByBookingId(String bookingId);

    List<VehicleRental> findByRoomId(String roomId);

    List<VehicleRental> findByVehicleIdAndActive(String vehicleId, boolean active);
}
