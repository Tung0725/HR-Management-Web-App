<%-- 
    Document   : account
    Created on : Feb 25, 2026, 12:58:18 PM
    Author     : Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Quản lý Tài khoản</title>
        <%-- Sử dụng lại CSS chung --%>
        <%@include file="/WEB-INF/jspf/css.jspf" %>

        <style>
            .table-container {
                background: white;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0,0,0,0.1);
                padding: 20px;
                margin-top: 30px;
            }

        </style>
    </head>
    <body>
        <c:if test="${sessionScope.ttdn==null}">
            <%-- Nếu session ttdn bị null hoặc trống, đẩy về trang home --%>
            <c:redirect url="login.jsp" /> 
        </c:if>
        <%-- Header chung --%>
        <%@include file="/WEB-INF/jspf/header.jspf" %>

        <main class="container-fluid px-4 mb-5">
            <div class="table-container">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="text-dark fw-bold">User Accounts Management</h2>
                    <a href="add_account.jsp" class="btn btn-success">
                        <i class="bi bi-person-plus-fill me-2"></i> Add New Account
                    </a>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead class="table-light">
                            <tr>
                                <th scope="col">Account</th>
                                <th scope="col">Full Name</th>
                                <th scope="col">Birthday</th>
                                <th scope="col">Gender</th>
                                <th scope="col">Phone</th>
                                <th scope="col">Role</th>
                                <th scope="col">Status</th>
                                <th scope="col" class="text-center">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%-- Giả sử Servlet gửi danh sách qua biến 'dsAccount' --%>
                            <c:forEach items="${requestScope.dsAcc}" var="a">
                                <tr>
                                    <td class="fw-bold text-primary">${a.account}</td>
                                    <td>${a.lastName} ${a.firstName}</td>
                                    <td>${a.birthday}</td>
                                    <%-- Gender: boolean (isGender) --%>
                                    <td>${a.gender ? 'Nam' : 'Nữ'}</td>

                                    <%-- Phone: getPhone() --%>
                                    <td>${a.phone}</td>

                                    <%-- Role: getRoleInSystem() -> Hiện số hoặc phân loại --%>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.roleInSystem == 1}">Admin</c:when>
                                            <c:when test="${a.roleInSystem == 2}">Manager</c:when>
                                            <c:when test="${a.roleInSystem == 3}">Staff</c:when>
                                            <c:when test="${a.roleInSystem == 4}">User</c:when>
                                        </c:choose>
                                    </td>


                                    <td>
                                        <c:choose>
                                            <%-- Phải là isUse để nó gọi đúng hàm isIsUse() trong Java --%>
                                            <c:when test="${a.use}">
                                                <span class="badge bg-success">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger">Locked</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>


                                    <td class="text-center">
                                        <%-- Đã sửa href trỏ về main --%>
                                        <a href="main?action=editAccount&id=${a.account}" class="btn btn-primary btn-sm">Update</a>
                                        <a href="main?action=deleteAccount&id=${a.account}" 
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Xóa account ${a.account}?');">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty requestScope.dsAcc}">
                                <tr>
                                    <td colspan="8" class="text-center text-muted py-4">
                                        <em>Chưa có dữ liệu tài khoản nào được tìm thấy.</em>
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>

        <%-- Footer chung --%>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
