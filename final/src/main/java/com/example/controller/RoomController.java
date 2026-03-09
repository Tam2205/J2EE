package com.example.controller;

import com.example.model.Booking;
import com.example.model.Room;
import com.example.service.AdditionalServiceService;
import com.example.service.BookingService;
import com.example.service.RoomService;
import com.example.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @GetMapping
    public String rooms(@RequestParam(required = false) String category, Model model) {
        if (category != null && !category.isEmpty()) {
            model.addAttribute("rooms", roomService.getRoomsByCategory(category));
            model.addAttribute("selectedCategory", category);
        } else {
            model.addAttribute("rooms", roomService.getAllRooms());
        }
        model.addAttribute("categories", Arrays.asList("STANDARD", "SUPERIOR", "DELUXE", "SUITE", "PRESIDENTIAL"));

        Map<String, Booking> roomBookingMap = new HashMap<>();
        for (Booking b : bookingService.getActiveBookings()) {
            roomBookingMap.put(b.getRoomId(), b);
        }
        model.addAttribute("roomBookingMap", roomBookingMap);

        return "rooms";
    }

    @GetMapping("/{id}")
    public String roomDetail(@PathVariable String id, Model model) {
        Room room = roomService.getRoomById(id);
        if (room == null) return "redirect:/rooms";

        model.addAttribute("room", room);

        Booking activeBooking = bookingService.getActiveBookingForRoom(id);
        model.addAttribute("activeBooking", activeBooking);

        if (activeBooking != null) {
            model.addAttribute("vehicleRentals", vehicleService.getRentalsByBooking(activeBooking.getId()));
        }

        model.addAttribute("availableServices", additionalServiceService.getAvailableServices());

        return "room-detail";
    }
}
