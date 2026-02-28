<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
    <head>
        <title>Quản lý Sản phẩm</title>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
        <style>
    /* CSS ép Full màn hình và làm ảnh to hơn */
    .table-container {
        background: white;
        padding: 20px;
        width: 100% !important; 
        min-height: 85vh;
    }
    
    .container-fluid {
        width: 100% !important;
        max-width: none !important;
    }

    /* Tăng kích thước ảnh tại đây */
    .product-img {
        width: 150px;         /* Tăng từ 50px lên 100px hoặc 120px tùy bạn */
        height: 150px;        /* Đảm bảo tỉ lệ 1:1 */
        object-fit: cover;    /* Giúp ảnh không bị méo khi bị ép kích thước */
        border-radius: 8px;   /* Bo góc tròn nhìn sẽ hiện đại hơn */
        box-shadow: 0 2px 5px rgba(0,0,0,0.2); /* Thêm chút đổ bóng cho nổi bật */
        transition: transform 0.3s; /* Hiệu ứng khi di chuột vào */
    }

    .product-img:hover {
        transform: scale(1.2); /* Khi di chuột vào ảnh sẽ phóng to nhẹ lên */
        cursor: zoom-in;
    }

    .text-truncate-custom {
        max-width: 200px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>
    </head>
    <body>
        <c:if test="${sessionScope.ttdn == null}">
            <c:redirect url="login.jsp" /> 
        </c:if>

        <%@include file="/WEB-INF/jspf/header.jspf" %>

        <main class="container-fluid mb-5">
            <div class="table-container">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2 class="text-dark fw-bold">Product Management</h2>
                    <a href="main?action=addProduct" class="btn btn-success">
                        <i class="bi bi-plus-circle me-2"></i> Add New Product
                    </a>
                </div>

                <div class="table-responsive">
                    <table class="table table-hover align-middle w-100">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Image</th>
                                <th>Product Name</th>
                                <th>Brief</th>
                                <th>Type</th>
                                <th>Account</th> <th>Unit</th>
                                <th>Price</th>
                                <th>Discount</th>
                                <th>Date</th>
                                <th class="text-center">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.dsProduct}" var="p">
                                <tr>
                                    <td class="small">${p.productId}</td>
                                    <td>
                                        <img  src="productImages/${p.productImage}" alt="img" class="product-img border">
                                    </td>
                                    <td class="fw-bold text-primary">${p.productName}</td>
                                    <td class="text-muted small text-truncate-custom">${p.brief}</td>
                                    
                                    <td><span class="badge bg-info text-dark">${p.type.typeId}</span></td>
                                    
                                    <td><small>${p.account.account}</small></td>
                                    
                                    <td>${p.unit}</td>
                                    <td class="fw-bold text-success">
                                        <fmt:formatNumber value="${p.price}" pattern="#,###"/>
                                    </td>
                                    <td class="text-danger">-${p.discount}%</td>
                                    <td><fmt:formatDate value="${p.postedDate}" pattern="dd/MM/yy"/></td>
                                    
                                    <td class="text-center">
                                        <div class="btn-group">
                                            <a href="main?action=editProduct&id=${p.productId}" class="btn btn-sm btn-outline-primary">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="main?action=deleteProduct&id=${p.productId}"
                                               class="btn btn-sm btn-outline-danger"
                                               onclick="return confirm('Xóa sản phẩm ${p.productId}?');">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>

                            <c:if test="${empty requestScope.dsProduct}">
                                <tr>
                                    <td colspan="11" class="text-center py-5 text-muted">
                                        <em>Danh sách trống hoặc chưa tải được dữ liệu.</em>
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>

        <%@include file="/WEB-INF/jspf/footer.jspf" %>
    </body>
</html>