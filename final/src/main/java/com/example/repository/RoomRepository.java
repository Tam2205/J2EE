package com.example.repository;

import com.example.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String> {

    List<Room> findByCategoryOrderByRoomNumber(String category);

    List<Room> findByStatus(String status);

    Optional<Room> findByRoomNumber(String roomNumber);

    List<Room> findAllByOrderByRoomNumber();
}
