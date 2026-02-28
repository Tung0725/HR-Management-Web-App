<%-- 
    Document   : categories
    Created on : Feb 3, 2026, 9:02:40 PM
    Author     : Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Quản lý Loại sản phẩm</title>
        <%-- Sử dụng lại CSS chung của project cũ cho đồng bộ --%>
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

        <main class="container mb-5">
            <div class="table-container">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="text-dark fw-bold">List of categories</h2>
                    <a href="add_category.jsp" class="btn btn-success">
                        <i class="bi bi-plus-lg me-2"></i> Add New Category
                    </a>
                </div>

                <table class="table table-hover align-middle">
                    <thead class="table-light">
                        <tr>
                            <th scope="col">Type ID</th>
                            <th scope="col">Category Name</th>
                            <th scope="col">Memo</th> <th scope="col" class="text-center" style="width: 200px;">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.ds}" var="c" varStatus="s">
                            <tr>
                                <td class="fw-bold text-secondary">${s.count}</td>

                                <td>${c.categoryName}</td>

                                <td>${c.memo}</td> 

                                <td class="text-center">
                                    <a href="main?action=editCategory&id=${c.typeId}" class="btn btn-primary btn-sm px-3 shadow-sm">
                                        Update
                                    </a>

                                    <a href="main?action=deleteCategory&id=${c.typeId}" 
                                       class="btn btn-danger btn-sm px-3 ms-1 shadow-sm"
                                       onclick="return confirm('Bạn có chắc muốn xóa loại: ${c.categoryName}?');">
                                        Delete
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:if test="${empty requestScope.ds}">
                            <tr>
                                <td colspan="4" class="text-center text-muted py-4">
                                    <em>Chưa có dữ liệu loại sản phẩm nào hoặc chưa chạy qua Controller.</em>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </main>

        <%-- Footer chung --%>
        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
