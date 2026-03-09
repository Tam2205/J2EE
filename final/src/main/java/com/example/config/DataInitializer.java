package com.example.config;

import com.example.model.AdditionalService;
import com.example.model.Room;
import com.example.model.Vehicle;
import com.example.repository.AdditionalServiceRepository;
import com.example.repository.RoomRepository;
import com.example.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AdditionalServiceRepository serviceRepository;

    @Override
    public void run(String... args) {
        if (roomRepository.count() > 0) return;

        String[][] categories = {
                {"STANDARD", "Phong tieu chuan", "500000", "100000", "1"},
                {"SUPERIOR", "Phong superior", "800000", "150000", "2"},
                {"DELUXE", "Phong deluxe", "1200000", "200000", "3"},
                {"SUITE", "Phong suite", "2000000", "350000", "4"},
                {"PRESIDENTIAL", "Phong presidential", "5000000", "800000", "5"}
        };

        for (String[] cat : categories) {
            for (int i = 1; i <= 10; i++) {
                Room room = new Room();
                room.setRoomNumber("P" + cat[4] + String.format("%02d", i));
                room.setCategory(cat[0]);
                room.setDescription(cat[1]);
                room.setPricePerNight(Double.parseDouble(cat[2]));
                room.setPricePerHour(Double.parseDouble(cat[3]));
                room.setFloor(Integer.parseInt(cat[4]));
                room.setStatus("AVAILABLE");
                roomRepository.save(room);
            }
        }

        createVehicle("Toyota Camry", "CAR", "51A-123.45", 1500000, 250000, "Sedan 5 cho");
        createVehicle("Honda City", "CAR", "51A-678.90", 1200000, 200000, "Sedan 5 cho");
        createVehicle("Honda SH 150i", "MOTORBIKE", "59-A1-12345", 250000, 50000, "Xe may tay ga");
        createVehicle("Yamaha NVX 155", "MOTORBIKE", "59-B2-67890", 200000, 40000, "Xe may tay ga");
        createVehicle("VinFast Klara S", "ELECTRIC_SCOOTER", "59-C3-11111", 180000, 35000, "Xe may dien");
        createVehicle("Giant Escape 3", "BICYCLE", "N/A", 80000, 20000, "Xe dap the thao");
        createVehicle("Trek FX 1", "BICYCLE", "N/A", 60000, 15000, "Xe dap duong pho");

        createService("Giat ui", 100000, "Giat ui quan ao", "LAUNDRY");
        createService("Spa & Massage", 500000, "Dich vu spa va massage thu gian", "SPA");
        createService("An sang buffet", 150000, "Buffet sang da dang", "F&B");
        createService("Minibar", 200000, "Do uong va snack trong phong", "F&B");
        createService("Dua don san bay", 400000, "Dich vu dua don san bay", "TRANSPORT");
        createService("Phong Gym", 100000, "Su dung phong tap gym", "FITNESS");
        createService("Room Service", 50000, "Phuc vu tai phong", "F&B");
        createService("Thue ao choang tam", 30000, "Thue ao choang cao cap", "OTHER");
        createService("Be boi", 0, "Su dung be boi (mien phi)", "FITNESS");
        createService("Giu hanh ly", 50000, "Gui hanh ly sau checkout", "OTHER");

        System.out.println("==> Da khoi tao du lieu mau cho La Ca Hotel!");
    }

    private void createVehicle(String name, String type, String plate, double dayPrice, double hourPrice, String desc) {
        Vehicle v = new Vehicle();
        v.setName(name);
        v.setType(type);
        v.setLicensePlate(plate);
        v.setStatus("AVAILABLE");
        v.setPricePerDay(dayPrice);
        v.setPricePerHour(hourPrice);
        v.setDescription(desc);
        vehicleRepository.save(v);
    }

    private void createService(String name, double price, String desc, String category) {
        AdditionalService s = new AdditionalService();
        s.setName(name);
        s.setPrice(price);
        s.setDescription(desc);
        s.setCategory(category);
        s.setAvailable(true);
        serviceRepository.save(s);
    }
}
