<%-- 
    Document   : index
    Created on : Feb 2, 2026, 6:24:30 PM
    Author     : Tung
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Manager Home</title>
        <%@include file="/WEB-INF/jspf/css.jspf" %>


    </head>
    <body>

        <%@include file="/WEB-INF/jspf/header.jspf" %>

        <main class="container text-center py-5">
            <div class="mb-5">
                <h1 class="hero-title">WELCOME TO MANAGER SYSTEM</h1>
                <p class="hero-desc mt-3">

                    <strong><c:if test="${not empty sessionScope.ttdn}">
                            Xin chào 
                            <span class="text-danger">
                                ${sessionScope.ttdn.lastName} ${sessionScope.ttdn.firstName}
                            </span>
                            ! Hệ thống đã sẵn sàng.
                        </c:if>
                    </strong>
                </p>
                <div class="mt-4">
                    <c:choose>
                        <%-- Chưa login: hiển thị nút Đăng nhập --%>
                        <c:when test="${empty sessionScope.ttdn}">
                            <a href="login.jsp"
                               class="btn btn-light fly-up-anim text-primary fw-bold px-5 py-3 shadow-lg rounded-pill btn-lg">  <!-- Tăng size cho nổi bật -->
                                <i class="bi bi-box-arrow-in-right me-2"></i> Đăng nhập ngay
                            </a>
                        </c:when>

                        <%-- Đã login: hiển thị nút Xem sản phẩm --%>
                        <c:otherwise>
                            <a href="product?action=view_product"  
                               class="btn btn-primary fly-up-anim fw-bold px-5 py-3 shadow-lg rounded-pill btn-lg">  
                                <i class="bi bi-shop me-2"></i> Xem toàn bộ sản phẩm
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>

            </div>


            <div class="container py-5">
                <div class="text-center mb-5">
                    <h1 class="display-4 fw-bold text-dark">DUY TÙNG STORE</h1>
                    <p class="lead text-muted">Nơi mang đến sản phẩm chất lượng cao – Uy tín – Đáng tin cậy mỗi ngày!</p>
                </div>

                <div class="row g-4 w-100 justify-content-center">
                    <!-- Ô 1: Chất lượng sản phẩm -->
                    <div class="col-md-4">  <!-- Tăng lên col-md-4 để 3 ô rộng hơn, đẹp hơn -->
                        <div class="card glass-card h-100 p-4 text-center fly-up-anim">
                            <div class="display-4 text-primary mb-3">
                                <i class="bi bi-shield-check-fill"></i>
                            </div>
                            <h4 class="fw-bold text-dark mb-2">Sản phẩm chính hãng 100%</h4>
                            <p class="text-muted">Mọi mặt hàng đều được kiểm tra kỹ lưỡng, nguồn gốc rõ ràng, bảo hành đầy đủ từ nhà sản xuất.</p>
                        </div>
                    </div>

                    <!-- Ô 2: Dịch vụ chăm sóc -->
                    <div class="col-md-4">
                        <div class="card glass-card h-100 p-4 text-center fly-up-anim">
                            <div class="display-4 text-success mb-3">
                                <i class="bi bi-headset"></i>
                            </div>
                            <h4 class="fw-bold text-dark mb-2">Hỗ trợ tận tâm 24/7</h4>
                            <p class="text-muted">Đội ngũ tư vấn luôn sẵn sàng giải đáp mọi thắc mắc, hỗ trợ nhanh chóng qua chat, hotline và Zalo.</p>
                        </div>
                    </div>

                    <!-- Ô 3: Cam kết giao hàng & hoàn tiền -->
                    <div class="col-md-4">
                        <div class="card glass-card h-100 p-4 text-center fly-up-anim">
                            <div class="display-4 text-warning mb-3">
                                <i class="bi bi-truck"></i>
                            </div>
                            <h4 class="fw-bold text-dark mb-2">Giao hàng nhanh – Hoàn tiền dễ dàng</h4>
                            <p class="text-muted">Ship COD toàn quốc, freeship đơn từ Xk, đổi trả thoải mái trong 7–30 ngày nếu không ưng ý.</p>
                        </div>
                    </div>
                </div>
            </div>
            
        </main>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
