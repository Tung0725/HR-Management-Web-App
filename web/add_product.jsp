<%-- 
    Document   : add_product
    Created on : Feb 28, 2026, 11:27:16 AM
    Author     : Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm Sản Phẩm Mới</title>
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
                            <h3 class="card-title text-center mb-4 text-dark">Thêm Sản Phẩm Mới</h3>
                            <hr class="mb-4">
                            <form action="main" method="post" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="addProduct">
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Mã sản phẩm (ID)</label>
                                        <input type="text" class="form-control" name="productId" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Tên sản phẩm</label>
                                        <input type="text" class="form-control" name="productName" required>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Loại sản phẩm</label>
                                        <select class="form-select" name="typeId" required>
                                            <c:forEach items="${listCate}" var="c">
                                                <option value="${c.typeId}">${c.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Ngày đăng</label>
                                        <input type="date" class="form-control" name="postedDate" required>
                                    </div>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Hình ảnh sản phẩm</label>
                                    <%-- Đổi type="file", đổi name thành "productImageFile" và thêm accept để chỉ nhận ảnh --%>
                                    <input type="file" class="form-control" name="productImageFile" accept="image/*" required>
                                </div>
                                <div class="row">
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Đơn vị (Unit)</label>
                                        <input type="text" class="form-control" name="unit" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Giá (VND)</label>
                                        <input type="number" class="form-control" name="price" required>
                                    </div>
                                    <div class="col-md-4 mb-3">
                                        <label class="form-label">Giảm giá (%)</label>
                                        <input type="number" class="form-control" name="discount" value="0">
                                    </div>
                                </div>
                                <div class="mb-4">
                                    <label class="form-label">Mô tả ngắn (Brief)</label>
                                    <textarea class="form-control" name="brief" rows="3"></textarea>
                                </div>
                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <a href="main?action=listProduct" class="btn btn-outline-secondary">Hủy</a>
                                    <button type="submit" class="btn btn-success px-4">Lưu sản phẩm</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
