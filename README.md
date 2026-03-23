# 🏨 La Ca Hotel Management System

Hệ thống quản lý khách sạn La Ca — ứng dụng web quản lý toàn diện các hoạt động khách sạn bao gồm phòng, đặt phòng, dịch vụ, xe cho thuê và nhật ký hoạt động.


![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-brightgreen)
![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-green)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.x-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5.3.2-purple)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📸 Tổng quan

Hệ thống được xây dựng trên nền tảng **Spring Boot 3.2.3** với **MongoDB**, cung cấp giao diện quản lý trực quan cho nhân viên và quản trị viên khách sạn.

## 🛠️ Công nghệ sử dụng

| Thành phần | Công nghệ |
|---|---|
| Backend | Spring Boot 3.2.3, Java 17 |
| Database | MongoDB |
| Template Engine | Thymeleaf |
| Frontend | Bootstrap 5.3.2, Bootstrap Icons |
| Bảo mật | Spring Security 6, BCrypt |
| Build Tool | Maven |

## ✨ Tính năng chính

### 🏠 Quản lý phòng
- 5 hạng phòng: **Standard, Superior, Deluxe, Suite, Presidential**
- Theo dõi trạng thái phòng real-time (Trống / Đang sử dụng / Bảo trì)
- Lọc phòng theo hạng, xem chi tiết và booking hiện tại
- Hệ thống khởi tạo sẵn **50 phòng** (10 phòng/hạng)

### 📋 Quản lý đặt phòng
- Hỗ trợ đặt phòng theo **đêm** hoặc **theo giờ**
- Quản lý thông tin khách (tên, SĐT, CCCD)
- Tính tiền tự động dựa trên loại đặt phòng
- Thêm dịch vụ, xe thuê vào booking
- Checkout và tính tổng hóa đơn

### 🚗 Quản lý xe cho thuê
- Danh mục xe máy cho thuê (Honda SH, Yamaha NVX, Air Blade, ...)
- Cho thuê xe theo ngày hoặc theo giờ
- Liên kết thuê xe với booking đang hoạt động
- Trả xe và tự động cập nhật phí vào booking

### 🛎️ Quản lý dịch vụ
- 6 loại dịch vụ: Giặt ủi, Spa, Ẩm thực, Vận chuyển, Thể dục, Khác
- Thêm/sửa/xóa dịch vụ, bật/tắt trạng thái khả dụng
- Gắn dịch vụ vào booking với số lượng tùy chỉnh

### 📊 Dashboard
- Thống kê tổng quan: tổng phòng, phòng đang sử dụng, phòng trống, xe đang cho thuê
- Hiển thị lưới phòng theo hạng với trạng thái trực quan
- Danh sách booking và thuê xe đang hoạt động

### 📝 Nhật ký hoạt động (Admin)
- Ghi log tất cả hành động: đặt phòng, checkout, dịch vụ, thuê/trả xe
- Lưu thông tin: người thực hiện, hành động, mô tả, thời gian, địa chỉ IP
- Lọc log theo tên người dùng

### 🔐 Xác thực & Phân quyền
- Đăng nhập bảo mật với **BCrypt** password hashing
- Phân quyền theo vai trò:
  - **ADMIN** — Toàn quyền hệ thống + xem nhật ký hoạt động
  - **STAFF** — Quản lý phòng, booking, dịch vụ, xe

## 🚀 Cài đặt & Chạy

### Yêu cầu
- **Java 17** trở lên
- **Maven 3.6+**
- **MongoDB** (đang chạy trên `localhost:27017`)

### Các bước

1. **Clone repository**
   ```bash
   git clone https://github.com/<your-username>/laca-hotel.git
   cd laca-hotel
   ```

2. **Cấu hình MongoDB**
   
   Đảm bảo MongoDB đã chạy. Cấu hình mặc định trong `src/main/resources/application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/hotel_management
   ```

3. **Build & chạy ứng dụng**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Truy cập ứng dụng**
   
   Mở trình duyệt tại: [http://localhost:8080](http://localhost:8080)

### Tài khoản mặc định

| Vai trò | Username | Password |
|---|---|---|
| Quản trị viên | `admin` | `admin123` |
| Nhân viên | `staff` | `staff123` |

> ⚠️ **Lưu ý**: Dữ liệu mẫu (phòng, dịch vụ, xe, tài khoản) được tự động khởi tạo khi ứng dụng chạy lần đầu.

## 📁 Cấu trúc dự án

```
src/main/java/com/example/
├── config/
│   ├── SecurityConfig.java          # Cấu hình Spring Security
│   ├── DataInitializer.java         # Khởi tạo dữ liệu mẫu
│   └── AuthenticationEventListener.java
├── controller/
│   ├── DashboardController.java     # Trang chủ & thống kê
│   ├── AuthController.java          # Đăng nhập
│   ├── RoomController.java          # Quản lý phòng
│   ├── BookingController.java       # Quản lý đặt phòng
│   ├── VehicleController.java       # Quản lý xe cho thuê
│   ├── ServiceController.java       # Quản lý dịch vụ
│   └── ActivityLogController.java   # Nhật ký hoạt động
├── model/
│   ├── User.java                    # Người dùng
│   ├── Room.java                    # Phòng
│   ├── Booking.java                 # Đặt phòng
│   ├── Vehicle.java                 # Xe
│   ├── VehicleRental.java           # Thuê xe
│   ├── AdditionalService.java       # Dịch vụ bổ sung
│   ├── ServiceUsage.java            # Sử dụng dịch vụ (embedded)
│   └── ActivityLog.java             # Nhật ký hoạt động
├── repository/                      # MongoDB Repositories
└── service/                         # Business Logic Services

src/main/resources/
├── application.properties           # Cấu hình ứng dụng
├── templates/                       # Thymeleaf templates
│   ├── dashboard.html
│   ├── login.html
│   ├── rooms.html
│   ├── bookings.html
│   ├── vehicles.html
│   ├── services.html
│   └── activity-logs.html
└── static/css/style.css             # Custom CSS
```

## 🔗 API Endpoints

| Method | Endpoint | Mô tả |
|---|---|---|
| GET | `/` | Dashboard — tổng quan hệ thống |
| GET | `/login` | Trang đăng nhập |
| GET | `/rooms` | Danh sách phòng |
| GET | `/rooms/{id}` | Chi tiết phòng |
| GET | `/bookings` | Danh sách đặt phòng |
| GET | `/bookings/new` | Form đặt phòng mới |
| GET | `/bookings/{id}` | Chi tiết đặt phòng |
| POST | `/bookings/save` | Tạo đặt phòng |
| POST | `/bookings/{id}/checkout` | Checkout |
| POST | `/bookings/{id}/add-service` | Thêm dịch vụ vào booking |
| GET | `/vehicles` | Danh sách xe & thuê xe đang hoạt động |
| POST | `/vehicles/rent` | Thuê xe |
| POST | `/vehicles/return/{id}` | Trả xe |
| POST | `/vehicles/add` | Thêm xe mới |
| GET | `/services` | Danh sách dịch vụ |
| POST | `/services/save` | Tạo/cập nhật dịch vụ |
| POST | `/services/delete/{id}` | Xóa dịch vụ |
| POST | `/services/toggle/{id}` | Bật/tắt dịch vụ |
| GET | `/activity-logs` | Nhật ký hoạt động (Admin) |

## 💰 Bảng giá phòng mặc định

| Hạng phòng | Giá/đêm | Giá/giờ |
|---|---|---|
| Standard | 500.000 VND | 100.000 VND |
| Superior | 800.000 VND | 150.000 VND |
| Deluxe | 1.200.000 VND | 200.000 VND |
| Suite | 2.000.000 VND | 350.000 VND |
| Presidential | 5.000.000 VND | 800.000 VND |

## 👥 Tác giả

- Dự án môn học **J2EE**

## 📄 License

Dự án này được phân phối dưới giấy phép [MIT License](LICENSE).
