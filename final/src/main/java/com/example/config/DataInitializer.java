package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.model.AdditionalService;
import com.example.model.Room;
import com.example.model.User;
import com.example.model.Vehicle;
import com.example.repository.AdditionalServiceRepository;
import com.example.repository.RoomRepository;
import com.example.repository.UserRepository;
import com.example.repository.VehicleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AdditionalServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Tao user mac dinh neu chua co (luon chay, khong phu thuoc room)
        if (userRepository.count() == 0) {
            createUser("admin", "admin123", "Quan tri vien", "ADMIN");
            createUser("staff", "staff123", "Nhan vien Le Tan", "STAFF");
            System.out.println("==> Da tao tai khoan mac dinh: admin/admin123, staff/staff123");
        }

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

        createVehicle("Honda SH 150i", "MOTORBIKE", "59-A1-12345", 250000, 50000, "Xe tay ga cao cap");
        createVehicle("Yamaha NVX 155", "MOTORBIKE", "59-B2-67890", 200000, 40000, "Xe tay ga the thao");
        createVehicle("Honda Air Blade", "MOTORBIKE", "59-C3-11111", 180000, 35000, "Xe tay ga pho thong");
        createVehicle("Honda Vision", "MOTORBIKE", "59-D4-22222", 150000, 30000, "Xe tay ga tiet kiem");
        createVehicle("Yamaha Exciter 155", "MOTORBIKE", "59-E5-33333", 220000, 45000, "Xe so the thao");
        createVehicle("Honda Wave Alpha", "MOTORBIKE", "59-F6-44444", 120000, 25000, "Xe so pho thong");
        createVehicle("Honda Lead 125", "MOTORBIKE", "59-G7-55555", 170000, 35000, "Xe tay ga nhe");
        createVehicle("Yamaha Janus", "MOTORBIKE", "59-H8-66666", 160000, 30000, "Xe tay ga thoi trang");

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

    private void createUser(String username, String password, String fullName, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setRole(role);
        user.setActive(true);
        user.setCreatedAt(java.time.LocalDateTime.now());
        userRepository.save(user);
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
