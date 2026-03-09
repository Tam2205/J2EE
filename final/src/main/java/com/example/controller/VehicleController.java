package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Vehicle;
import com.example.model.VehicleRental;
import com.example.service.BookingService;
import com.example.service.VehicleService;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public String vehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.getAllVehicles());
        model.addAttribute("activeRentals", vehicleService.getActiveRentals());
        model.addAttribute("activeBookings", bookingService.getActiveBookings());
        return "vehicles";
    }

    @PostMapping("/rent")
    public String rentVehicle(@RequestParam String vehicleId,
                              @RequestParam String bookingId,
                              @RequestParam double totalAmount,
                              RedirectAttributes redirectAttributes) {
        VehicleRental rental = new VehicleRental();
        rental.setVehicleId(vehicleId);
        rental.setBookingId(bookingId);
        rental.setTotalAmount(totalAmount);

        vehicleService.createRental(rental);
        redirectAttributes.addFlashAttribute("success", "Cho thue xe may thanh cong!");
        return "redirect:/vehicles";
    }

    @PostMapping("/return/{id}")
    public String returnVehicle(@PathVariable String id, RedirectAttributes redirectAttributes) {
        vehicleService.returnVehicle(id);
        redirectAttributes.addFlashAttribute("success", "Tra xe may thanh cong!");
        return "redirect:/vehicles";
    }

    @PostMapping("/add")
    public String addVehicle(@ModelAttribute Vehicle vehicle, RedirectAttributes redirectAttributes) {
        vehicle.setStatus("AVAILABLE");
        vehicleService.save(vehicle);
        redirectAttributes.addFlashAttribute("success", "Them xe may thanh cong!");
        return "redirect:/vehicles";
    }
}
