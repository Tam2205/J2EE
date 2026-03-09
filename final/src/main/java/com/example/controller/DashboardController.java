package com.example.controller;

import com.example.model.Booking;
import com.example.service.BookingService;
import com.example.service.RoomService;
import com.example.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalRooms", roomService.countAll());
        model.addAttribute("occupiedRooms", roomService.countByStatus("OCCUPIED"));
        model.addAttribute("availableRooms", roomService.countByStatus("AVAILABLE"));
        model.addAttribute("activeVehicleRentals", vehicleService.countActiveRentals());

        model.addAttribute("roomsByCategory", roomService.getRoomsGroupedByCategory());

        List<Booking> activeBookings = bookingService.getActiveBookings();
        model.addAttribute("activeBookings", activeBookings);

        Map<String, Booking> roomBookingMap = new HashMap<>();
        for (Booking b : activeBookings) {
            roomBookingMap.put(b.getRoomId(), b);
        }
        model.addAttribute("roomBookingMap", roomBookingMap);

        model.addAttribute("activeRentals", vehicleService.getActiveRentals());
        model.addAttribute("categories", Arrays.asList("STANDARD", "SUPERIOR", "DELUXE", "SUITE", "PRESIDENTIAL"));

        return "dashboard";
    }
}
