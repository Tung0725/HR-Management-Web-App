<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chỉnh sửa tài khoản | Management System</title>
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
                            <h3 class="card-title text-center mb-4 text-dark">Cập Nhật Tài Khoản</h3>
                            <hr class="mb-4">

                            <form action="main" method="post"> <%-- Đổi thành 'account' cho khớp Controller --%>
                                <%-- 1. Action để Controller biết là thực hiện update --%>
                                <input type="hidden" name="action" value="updateAccount">

                                <div class="mb-3">
                                    <label class="form-label">Tài khoản (Email)</label>
                                    <input type="text" class="form-control bg-light" name="account" 
                                           value="${acc.account}" readonly>
                                    <div class="form-text">Tên đăng nhập không thể thay đổi.</div>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Họ (Last name)</label>
                                        <input type="text" class="form-control" name="lastname" 
                                               value="${acc.lastName}" required>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Tên (First name)</label>
                                        <input type="text" class="form-control" name="firstname" 
                                               value="${acc.firstName}" required>
                                    </div>
                                </div>

                                <div class="mb-3">
                                    <label class="form-label">Mật khẩu mới</label>
                                    <input type="password" class="form-control" name="password" 
                                           value="${acc.pass}" required>
                                </div>

                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Số điện thoại</label>
                                        <input type="tel" class="form-control" name="phone" value="${acc.phone}">
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Ngày sinh</label>
                                        <input type="date" class="form-control" name="dob" 
                                               value="${acc.birthday}" required>
                                    </div>
                                </div>

                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <label class="form-label d-block">Giới tính</label>
                                        <div class="form-check form-check-inline mt-1">
                                            <input class="form-check-input" type="radio" name="gender" id="m" value="true" ${acc.gender ? 'checked' : ''}>
                                            <label class="form-check-label" for="m">Nam</label>
                                        </div>
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" type="radio" name="gender" id="f" value="false" ${!acc.gender ? 'checked' : ''}>
                                            <label class="form-check-label" for="f">Nữ</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <label class="form-label">Vai trò</label>
                                        <select class="form-select" name="role">
                                            <option value="1" ${acc.roleInSystem == 1 ? 'selected' : ''}>Admin</option>
                                            <option value="2" ${acc.roleInSystem == 2 ? 'selected' : ''}>Manager</option>
                                            <option value="3" ${acc.roleInSystem == 3 ? 'selected' : ''}>Staff</option>
                                            <option value="4" ${acc.roleInSystem == 4 ? 'selected' : ''}>User</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <div class="form-check form-switch">
                                        <input class="form-check-input" type="checkbox" id="act" name="active" ${acc.use ? 'checked' : ''}>
                                        <label class="form-check-label text-dark" for="act">Kích hoạt tài khoản</label>
                                    </div>
                                </div>

                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                    <a href="main?action=listAccount" class="btn btn-outline-secondary">Hủy bỏ</a>
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