<%-- 
    Document   : view_product
    Created on : Feb 26, 2026, 11:22:48 AM
    Author     : Tung
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Khám phá Sản phẩm</title>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <style>
            body { background-color: #f4f6f9; }
            .product-card {
                transition: all 0.25s ease-in-out;
                cursor: pointer;
                border: 1px solid #e9ecef;
                border-radius: 12px;
                background-color: #fff;
                overflow: hidden;
            }
            .product-card:hover {
                transform: translateY(-5px);
                box-shadow: 0 12px 24px rgba(0,0,0,0.08);
                border-color: #cdd4da;
            }
            .img-container {
                height: 220px;
                background-color: #ffffff;
                padding: 15px;
                display: flex;
                align-items: center;
                justify-content: center;
                border-bottom: 1px solid #f8f9fa;
            }
            .card-img-top {
                max-height: 100%;
                max-width: 100%;
                object-fit: contain; /* Giúp ảnh không bị cắt góc */
            }
            .discount-badge {
                position: absolute;
                top: 12px;
                right: 12px;
                background: #e84118;
                color: white;
                padding: 4px 12px;
                border-radius: 8px;
                font-weight: 600;
                font-size: 0.85rem;
                box-shadow: 0 2px 4px rgba(232, 65, 24, 0.3);
                z-index: 2;
            }
            .price-text { font-size: 1.25rem; }
        </style>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jspf" %>

        <main class="container py-5">
            <div class="d-flex flex-column">
            <div class="d-flex align-items-center justify-content-between mb-5 pb-4 border-bottom flex-nowrap gap-4">
                <!-- Tiêu đề + mô tả bên trái -->
                <div class="flex-grow-1">
                    <h1 class="display-5 fw-bold text-primary mb-1">Khám phá sản phẩm</h1>
                    <p class="lead text-muted mb-0">Tìm kiếm và lựa chọn những sản phẩm phù hợp nhất với bạn</p>
                </div>

                <!-- Nút Bộ lọc bên phải, không cho xuống dòng -->
                <button class="btn btn-primary btn-lg px-5 py-3 rounded-pill shadow-lg flex-shrink-0"
                        data-bs-toggle="offcanvas" data-bs-target="#filterSidebar">
                    <i class="bi bi-filter-right me-2"></i> Bộ lọc
                </button>
            </div>

            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4">
                <c:forEach items="${requestScope.dsProduct}" var="p">
                    <div class="col">
                        <div class="card h-100 product-card" data-bs-toggle="modal" data-bs-target="#modal-${p.productId}">
                            <c:if test="${p.discount > 0}">
                                <div class="discount-badge">-${p.discount}%</div>
                            </c:if>
                            
                            <div class="img-container position-relative">
                                <img src="productImages/${p.productImage}" class="card-img-top" alt="${p.productName}">
                            </div>
                            
                            <div class="card-body d-flex flex-column">
                                <span class="badge bg-light text-secondary border mb-2 align-self-start">Loại: ${p.type.typeId}</span>
                                <h6 class="card-title text-dark fw-bold text-truncate mb-2" title="${p.productName}">${p.productName}</h6>
                                
                                <div class="mt-auto pt-2">
                                    <span class="price-text fw-bold text-primary">
                                        <fmt:formatNumber value="${p.price * (1 - p.discount/100)}" pattern="#,###"/> đ
                                    </span>
                                    <c:if test="${p.discount > 0}">
                                        <small class="text-decoration-line-through text-muted ms-2">
                                            <fmt:formatNumber value="${p.price}" pattern="#,###"/>
                                        </small>
                                    </c:if>
                                </div>
                            </div>
                        </div>

                        <div class="modal fade" id="modal-${p.productId}" tabindex="-1" aria-hidden="true">
                            <div class="modal-dialog modal-lg modal-dialog-centered">
                                <div class="modal-content border-0 shadow-lg" style="border-radius: 16px; overflow: hidden;">
                                    <div class="modal-body p-0">
                                        <div class="row g-0">
                                            <div class="col-md-6 p-4 d-flex align-items-center justify-content-center bg-light">
                                                <img src="productImages/${p.productImage}" class="img-fluid" style="max-height: 400px; object-fit: contain;">
                                            </div>
                                            <div class="col-md-6 p-4 p-md-5 d-flex flex-column">
                                                <button type="button" class="btn-close align-self-end mb-2" data-bs-dismiss="modal"></button>
                                                
                                                <span class="badge bg-secondary text-white align-self-start mb-2">Loại: ${p.type.typeId}</span>
                                                <h3 class="fw-bold text-dark mb-3">${p.productName}</h3>
                                                
                                                <p class="text-secondary mb-4" style="line-height: 1.6;">${p.brief}</p>
                                                
                                                <div class="bg-light p-3 rounded-3 mb-4 border">
                                                    <h4 class="text-primary fw-bold mb-1">
                                                        <fmt:formatNumber value="${p.price * (1 - p.discount/100)}" pattern="#,###"/> đ / ${p.unit}
                                                    </h4>
                                                    <small class="text-muted">Giá gốc: <fmt:formatNumber value="${p.price}" pattern="#,###"/> đ</small><br>
                                                    <small class="text-muted">Ngày đăng: <fmt:formatDate value="${p.postedDate}" pattern="dd/MM/yyyy"/></small>
                                                </div>
                                                
                                                <div class="mt-auto d-grid">
                                                    <button class="btn btn-outline-secondary py-2" data-bs-dismiss="modal">Đóng</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
                </div>
        </main>

        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>