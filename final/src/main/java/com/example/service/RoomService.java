package com.example.service;

import com.example.model.Room;
import com.example.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAllByOrderByRoomNumber();
    }

    public List<Room> getRoomsByCategory(String category) {
        return roomRepository.findByCategoryOrderByRoomNumber(category);
    }

    public Room getRoomById(String id) {
        return roomRepository.findById(id).orElse(null);
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Map<String, List<Room>> getRoomsGroupedByCategory() {
        List<Room> allRooms = getAllRooms();
        Map<String, List<Room>> grouped = allRooms.stream()
                .collect(Collectors.groupingBy(Room::getCategory, LinkedHashMap::new, Collectors.toList()));

        String[] order = {"STANDARD", "SUPERIOR", "DELUXE", "SUITE", "PRESIDENTIAL"};
        Map<String, List<Room>> ordered = new LinkedHashMap<>();
        for (String cat : order) {
            if (grouped.containsKey(cat)) {
                ordered.put(cat, grouped.get(cat));
            }
        }
        return ordered;
    }

    public long countByStatus(String status) {
        return roomRepository.findByStatus(status).size();
    }

    public long countAll() {
        return roomRepository.count();
    }
}
