<%-- 
    Document   : update_product
    Created on : Feb 28, 2026, 11:27:27 AM
    Author     : Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cập Nhật Sản Phẩm</title>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <style>
            body {
                background-color: #f8f9fa;
            }
            .card {
                border: none;
                border-radius: 15px;
            }
            .form-label {
                font-weight: 600;
                color: #495057;
            }
        </style>
    </head>
    <body>
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-lg-8">
                    <div class="card shadow-sm p-4">
                        <div class="card-body">
                            <h3 class="card-title text-center mb-4 text-dark">Cập Nhật Sản Phẩm</h3>
                            <hr class="mb-4">
                            <form action="main" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="updateProduct">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Mã sản phẩm (Không thể đổi)</label>
                                        <input type="text" class="form-control bg-light" name="productId" value="${p.productId}" readonly>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Tên sản phẩm</label>
                                        <input type="text" class="form-control" name="productName" value="${p.productName}" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Loại sản phẩm</label>
                                        <select class="form-select" name="typeId" required>
                                            <c:forEach items="${listCate}" var="c">
                                                <option value="${c.typeId}" ${c.typeId == p.type.typeId ? 'selected' : ''}>${c.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Ngày đăng</label>
                                        <input type="date" class="form-control" name="postedDate" value="${p.postedDate}" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Hình ảnh sản phẩm (Bỏ trống nếu không muốn đổi ảnh)</label>

                                    <%-- Nơi chọn ảnh mới --%>
                                    <input type="file" class="form-control" name="productImageFile" accept="image/*">

                                    <%-- GIỮ LẠI ẢNH CŨ: Thẻ này bị ẩn đi, chứa tên file cũ --%>
                                    <input type="hidden" name="oldImage" value="${p.productImage}">

                                    <div class="mt-2 text-muted small">
                                        Ảnh hiện tại: <span class="fw-bold">${p.productImage}</span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Đơn vị</label>
                                        <input type="text" class="form-control" name="unit" value="${p.unit}" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Giá</label>
                                        <input type="number" class="form-control" name="price" value="${p.price}" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Giảm giá (%)</label>
                                        <input type="number" class="form-control" name="discount" value="${p.discount}">
                                    </div>
                                </div>
                                <div class="mb-4">
                                    <label class="form-label">Mô tả ngắn</label>
                                    <textarea class="form-control" name="brief" rows="3">${p.brief}</textarea>
                                </div>
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <a href="main?action=listProduct" class="btn btn-outline-secondary">Hủy</a>
                                    <button type="submit" class="btn btn-primary px-4">Lưu thay đổi</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
