# LibraX - Library Management System (Modular Monolith)

## Giới thiệu & Cấu trúc kiến trúc
Dự án được tổ chức theo mô hình **Modular Monolith**, phân chia ranh giới mã nguồn theo từng domain nghiệp vụ (`book`, `member`, `borrowing`). Mỗi domain tuân thủ cấu trúc 3 tầng chuẩn: Controller - Service - Repository.

---

## Giải thích: Vì sao đây là Monolithic Architecture chứ chưa phải Microservices?

Dù mã nguồn đã được module hóa rõ ràng theo domain nghiệp vụ, hệ thống hiện tại **vẫn là một kiến trúc Monolith (Modular Monolith)** chứ chưa phải là Microservices vì các lý do cốt lõi sau:

### 1. Đóng gói và triển khai duy nhất (Single Deployment Artifact)
* Toàn bộ 3 domain (`book`, `member`, `borrowing`) được biên dịch, đóng gói thành **duy nhất một file thực thi** (`.jar` hoặc `.war`) thông qua một bản build Gradle chung.
* Toàn bộ hệ thống chạy trên **một tiến trình (single process / JVM)** duy nhất. Nếu cần deploy một thay đổi nhỏ ở domain `book`, toàn bộ ứng dụng vẫn phải build và deploy lại cùng nhau.

### 2. Giao tiếp nội bộ trong bộ nhớ (In-Memory Invocation)
* Khi domain `borrowing` cần kiểm tra trạng thái sách từ domain `book` hay kiểm tra tồn tại của độc giả từ domain `member`, nó thực hiện **gọi hàm Java trực tiếp (direct method call / in-memory function call)** thông qua cơ chế Dependency Injection của Spring.
* Không có sự xuất hiện của các giao thức mạng phân tán (Network Calls) như HTTP REST, gRPC, hay Message Broker (Kafka, RabbitMQ) giữa các domain như trong Microservices.

### 3. Chia sẻ tài nguyên & Cơ sở dữ liệu (Shared Database & Runtime)
* Cả 3 domain dùng chung một kết nối cơ sở dữ liệu (single database connection pool / datasource).
* Việc đảm bảo tính toàn vẹn dữ liệu (ví dụ: mượn sách thành công thì đổi trạng thái sách sang `BORROWED` và lưu `BorrowRecord`) được thực hiện cục bộ qua cơ chế Local Transaction (`@Transactional`), không cần giải pháp Distributed Transaction (Saga Pattern, 2PC) phức tạp của Microservices.

### 4. Vận hành và Scaling đồng bộ (Coupled Scaling & Lifecycle)
* Khi lượng người dùng mượn sách tăng cao đột biến, ta không thể scale riêng domain `borrowing` mà phải nhân bản toàn bộ ứng dụng Monolith.
* Nếu một lỗi nghiêm trọng xảy ra gây tràn bộ nhớ (Out Of Memory) hoặc crash tiến trình ở một module, toàn bộ ứng dụng và các module khác đều sẽ dừng hoạt động cùng lúc.

---

### Bảng so sánh tóm tắt

| Tiêu chí | Cấu trúc hiện tại (Modular Monolith) | Kiến trúc Microservices |
| :--- | :--- | :--- |
| **Đơn vị triển khai** | 1 file `.jar` / 1 tiến trình JVM | Nhiều service độc lập, nhiều container/process |
| **Giao tiếp liên domain** | In-memory Java method call | HTTP REST, gRPC, Message Queue (Kafka, RMQ) |
| **Cơ sở dữ liệu** | Chung 1 Database / Schema | Database-per-service (mỗi service 1 DB riêng) |
| **Quản lý Transaction** | Local Database Transaction (`@Transactional`) | Distributed Transactions (Saga, Event-driven) |
| **Độc lập triển khai (CI/CD)**| Thay đổi 1 module phải deploy lại cả app | Mỗi service có pipeline CI/CD riêng biệt |

> **Kết luận:** Mô hình Modular Monolith này mang lại lợi ích tốt nhất của cả hai thế giới: giữ được ranh giới nghiệp vụ sạch sẽ, dễ bảo trì, hạn chế spaghetti code mà vẫn duy trì tính đơn giản trong phát triển, kiểm thử và vận hành của Monolithic Architecture.
