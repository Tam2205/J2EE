package com.example.repository;

import com.example.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByStatus(String status);

    List<Booking> findByRoomIdAndStatus(String roomId, String status);

    List<Booking> findByGuestNameContainingIgnoreCase(String guestName);

    List<Booking> findByStatusOrderByCheckInDesc(String status);
}
