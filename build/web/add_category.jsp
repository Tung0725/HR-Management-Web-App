<%-- 
    Document   : add_category
    Created on : Feb 28, 2026, 10:58:58 AM
    Author     : Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thêm mới danh mục | Management System</title>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
            .btn-success {
                border-radius: 8px;
                padding: 10px 25px;
            }
            .form-control:focus {
                box-shadow: none;
                border-color: #198754;
            }
        </style>
    </head>
    <body>
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card shadow-sm p-4">
                        <div class="card-body">
                            <h3 class="card-title text-center mb-4 text-dark">Thêm Danh Mục Mới</h3>
                            <hr class="mb-4">

                            <form action="main" method="post">
                                <%-- Action để MainController biết là đang thêm mới Category --%>
                                <input type="hidden" name="action" value="addCategory">

                                <div class="mb-3">
                                    <label class="form-label">Tên danh mục (Category Name)</label>
                                    <input type="text" class="form-control" name="typeName" placeholder="Nhập tên danh mục" required>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label">Mô tả chi tiết (Memo)</label>
                                    <textarea class="form-control" name="memo" rows="4" placeholder="Nhập mô tả cho danh mục này"></textarea>
                                </div>

                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <a href="main?action=listCategory" class="btn btn-outline-secondary">Hủy bỏ</a>
                                    <button type="submit" class="btn btn-success shadow-sm">Thêm mới</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
