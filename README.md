📦 Java Web Management System (MVC Architecture)
Chào mừng bạn đến với dự án Hệ thống Quản lý Sản phẩm & Người dùng. Đây là một ứng dụng web hoàn chỉnh được xây dựng trên nền tảng Java Web (Servlet/JSP) theo mô hình MVC, tích hợp phân quyền đa cấp bậc (RBAC) và chức năng tải lên hình ảnh thực tế.
🚀 Tính năng nổi bật
1. Phân quyền người dùng (Authorization)
Hệ thống chia làm 4 vai trò (Role) với quyền hạn tăng dần:
Role 1 (Admin): Toàn quyền quản trị hệ thống.
Role 2 (Manager): Quản lý toàn bộ nhưng không được phép xóa hoặc cập nhật tài khoản của Admin.
Role 3 (Staff): Được xem/thêm/sửa Sản phẩm & Danh mục; không được quyền xóa hoặc truy cập quản lý Tài khoản.
Role 4 (User): Chỉ có quyền xem danh sách sản phẩm thông qua giao diện Portfolio (view_product.jsp).
2. Quản lý Sản phẩm & Hình ảnh
Hỗ trợ Upload ảnh trực tiếp từ máy tính lên Server thay vì nhập link thủ công.
Tự động lưu trữ và hiển thị ảnh trong thư mục productImages.
Giao diện danh sách sản phẩm được tối ưu hóa với hiệu ứng phóng to ảnh khi di chuột.
3. Giao diện chuyên nghiệp (UI/UX)
Sử dụng Bootstrap 5 để tạo các Form nhập liệu đồng bộ (Card style) cho tất cả các trang Add/Update.
Trang view_product được thiết kế hiện đại, có Modal hiển thị chi tiết sản phẩm và Badge giảm giá.
Sử dụng JSTL để đánh số thứ tự liên tục (1, 2, 3...) giúp danh sách luôn đẹp mắt bất kể ID trong Database bị nhảy số.

🛠 Công nghệ sử dụng
Thành phần
Công nghệ
Ngôn ngữ
Java (JDK 8+)
Framework
Java Servlet, JSP
Database
SQL Server (JDBC)
Thư viện thẻ
JSTL 1.2, SQL Taglib
Frontend
Bootstrap 5, Bootstrap Icons, CSS3
Kiến trúc
MVC (Model-View-Controller)

📂 Cấu trúc dự án tiêu biểu
controller/MainController.java: Bộ điều hướng trung tâm, xử lý xác thực và phân quyền.
controller/ProductController.java: Xử lý logic nghiệp vụ sản phẩm và Multi-part upload file.
model/dao/: Chứa các lớp DAO (Data Access Object) thực thi các câu lệnh SQL.
web/WEB-INF/jspf/: Chứa các mảnh giao diện dùng chung như header.jspf, footer.jspf.

⚙️ Hướng dẫn cài đặt
Database: Chạy các script SQL để tạo bảng Account, Category, Product.
Cấu hình: Cập nhật thông tin kết nối DB (User, Pass, Port) trong file web.xml.
Thư viện: Đảm bảo đã thêm các file .jar cần thiết (SQL Driver, JSTL) vào thư mục lib.
Thư mục ảnh: Tạo thư mục productImages trong thư mục gốc của Web Content để lưu trữ ảnh tải lên.
Chạy: Deploy lên Tomcat 8.0 trở lên và truy cập qua đường dẫn http://localhost:8080/ProjectName/login.jsp.

📝 Lưu ý kỹ thuật
Dự án sử dụng @MultipartConfig tại các Controller để xử lý form dữ liệu chứa file. Mọi yêu cầu đều được định tuyến qua MainController để đảm bảo tính bảo mật và kiểm soát quyền truy cập tập trung.

Dự án được thực hiện bởi Tung (2026).
