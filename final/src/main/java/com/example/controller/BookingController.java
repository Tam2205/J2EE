package com.example.controller;

import com.example.model.AdditionalService;
import com.example.model.Booking;
import com.example.model.Room;
import com.example.model.ServiceUsage;
import com.example.service.AdditionalServiceService;
import com.example.service.BookingService;
import com.example.service.RoomService;
import com.example.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public String bookings(@RequestParam(required = false, defaultValue = "ACTIVE") String status, Model model) {
        model.addAttribute("bookings", bookingService.getBookingsByStatus(status));
        model.addAttribute("currentStatus", status);
        return "bookings";
    }

    @GetMapping("/new")
    public String newBooking(@RequestParam(required = false) String roomId, Model model) {
        Booking booking = new Booking();

        if (roomId != null) {
            Room room = roomService.getRoomById(roomId);
            if (room != null) {
                booking.setRoomId(roomId);
                booking.setRoomNumber(room.getRoomNumber());
                model.addAttribute("selectedRoom", room);
            }
        }

        booking.setCheckIn(LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 0)));
        booking.setCheckOut(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(12, 0)));
        booking.setBookingType("NIGHTLY");

        model.addAttribute("booking", booking);
        model.addAttribute("availableRooms", roomService.getAllRooms().stream()
                .filter(r -> "AVAILABLE".equals(r.getStatus())).toList());

        return "booking-form";
    }

    @PostMapping("/save")
    public String saveBooking(@ModelAttribute Booking booking, RedirectAttributes redirectAttributes) {
        bookingService.createBooking(booking);
        redirectAttributes.addFlashAttribute("success", "Dat phong thanh cong!");
        return "redirect:/bookings";
    }

    @PostMapping("/{id}/checkout")
    public String checkout(@PathVariable String id, RedirectAttributes redirectAttributes) {
        bookingService.checkout(id);
        redirectAttributes.addFlashAttribute("success", "Tra phong thanh cong!");
        return "redirect:/bookings";
    }

    @GetMapping("/{id}")
    public String bookingDetail(@PathVariable String id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        if (booking == null) return "redirect:/bookings";

        model.addAttribute("booking", booking);
        model.addAttribute("vehicleRentals", vehicleService.getRentalsByBooking(id));
        model.addAttribute("availableServices", additionalServiceService.getAvailableServices());
        model.addAttribute("availableVehicles", vehicleService.getAvailableVehicles());

        Room room = roomService.getRoomById(booking.getRoomId());
        model.addAttribute("room", room);

        return "booking-detail";
    }

    @PostMapping("/{id}/add-service")
    public String addService(@PathVariable String id,
                             @RequestParam String serviceId,
                             @RequestParam int quantity,
                             RedirectAttributes redirectAttributes) {
        AdditionalService service = additionalServiceService.getServiceById(serviceId);
        if (service != null) {
            ServiceUsage usage = new ServiceUsage();
            usage.setServiceId(serviceId);
            usage.setServiceName(service.getName());
            usage.setUnitPrice(service.getPrice());
            usage.setQuantity(quantity);
            bookingService.addService(id, usage);
            redirectAttributes.addFlashAttribute("success", "Da them dich vu!");
        }
        return "redirect:/bookings/" + id;
    }
}
