<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cập nhật danh mục | Management System</title>
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
            .btn-primary {
                border-radius: 8px;
                padding: 10px 25px;
            }
            .form-control:focus {
                box-shadow: none;
                border-color: #0d6efd;
            }
        </style>
    </head>
    <body>
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="card shadow-sm p-4">
                        <div class="card-body">
                            <h3 class="card-title text-center mb-4 text-dark">Cập Nhật Danh Mục</h3>
                            <hr class="mb-4">

                            <form action="main" method="post">
                                <input type="hidden" name="action" value="updateCategory">
                                <%-- Phải giữ lại ID để update đúng dòng --%>
                                <input type="hidden" name="typeId" value="${cate.typeId}">

                                <div class="mb-3">
                                    <label class="form-label">Tên danh mục (Category Name)</label>
                                    <input type="text" class="form-control" name="typeName" value="${cate.categoryName}" required>
                                </div>

                                <div class="mb-4">
                                    <label class="form-label">Mô tả chi tiết (Memo)</label>
                                    <textarea class="form-control" name="memo" rows="4">${cate.memo}</textarea>
                                </div>

                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <a href="main?action=listCategory" class="btn btn-outline-secondary">Hủy bỏ</a>
                                    <button type="submit" class="btn btn-primary shadow-sm">Lưu thay đổi</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>