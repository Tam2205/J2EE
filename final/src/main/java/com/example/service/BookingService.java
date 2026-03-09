package com.example.service;

import com.example.model.Booking;
import com.example.model.Room;
import com.example.model.ServiceUsage;
import com.example.model.Vehicle;
import com.example.model.VehicleRental;
import com.example.repository.BookingRepository;
import com.example.repository.RoomRepository;
import com.example.repository.VehicleRentalRepository;
import com.example.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private VehicleRentalRepository vehicleRentalRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getActiveBookings() {
        return bookingRepository.findByStatusOrderByCheckInDesc("ACTIVE");
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatusOrderByCheckInDesc(status);
    }

    public Booking getActiveBookingForRoom(String roomId) {
        List<Booking> bookings = bookingRepository.findByRoomIdAndStatus(roomId, "ACTIVE");
        return bookings.isEmpty() ? null : bookings.get(0);
    }

    public Booking createBooking(Booking booking) {
        booking.setStatus("ACTIVE");
        booking.setCreatedAt(LocalDateTime.now());

        Room room = roomRepository.findById(booking.getRoomId()).orElse(null);
        if (room != null) {
            booking.setRoomNumber(room.getRoomNumber());

            if ("HOURLY".equals(booking.getBookingType())) {
                booking.setRoomCharge(room.getPricePerHour() * booking.getHoursBooked());
                if (booking.getCheckIn() != null && booking.getHoursBooked() > 0) {
                    booking.setCheckOut(booking.getCheckIn().plusHours(booking.getHoursBooked()));
                }
            } else {
                if (booking.getCheckIn() != null && booking.getCheckOut() != null) {
                    long nights = Duration.between(booking.getCheckIn(), booking.getCheckOut()).toDays();
                    if (nights <= 0) nights = 1;
                    booking.setRoomCharge(room.getPricePerNight() * nights);
                }
            }

            room.setStatus("OCCUPIED");
            roomRepository.save(room);
        }

        booking.setTotalAmount(booking.getRoomCharge());
        return bookingRepository.save(booking);
    }

    public Booking checkout(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return null;

        booking.setStatus("COMPLETED");

        double total = booking.getRoomCharge() + booking.getServiceCharge() + booking.getVehicleCharge();
        booking.setTotalAmount(total);

        Room room = roomRepository.findById(booking.getRoomId()).orElse(null);
        if (room != null) {
            room.setStatus("AVAILABLE");
            roomRepository.save(room);
        }

        List<VehicleRental> rentals = vehicleRentalRepository.findByBookingId(bookingId);
        for (VehicleRental rental : rentals) {
            if (rental.isActive()) {
                rental.setActive(false);
                rental.setEndTime(LocalDateTime.now());
                vehicleRentalRepository.save(rental);

                Vehicle vehicle = vehicleRepository.findById(rental.getVehicleId()).orElse(null);
                if (vehicle != null) {
                    vehicle.setStatus("AVAILABLE");
                    vehicleRepository.save(vehicle);
                }
            }
        }

        return bookingRepository.save(booking);
    }

    public Booking addService(String bookingId, ServiceUsage serviceUsage) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) return null;

        serviceUsage.setUsageDate(LocalDateTime.now());
        serviceUsage.setTotalPrice(serviceUsage.getUnitPrice() * serviceUsage.getQuantity());
        booking.getServices().add(serviceUsage);

        double serviceCharge = booking.getServices().stream()
                .mapToDouble(ServiceUsage::getTotalPrice).sum();
        booking.setServiceCharge(serviceCharge);
        booking.setTotalAmount(booking.getRoomCharge() + serviceCharge + booking.getVehicleCharge());

        return bookingRepository.save(booking);
    }
}
