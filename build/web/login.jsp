<%-- 
    Document   : login
    Created on : Feb 25, 2026, 9:47:51 AM
    Author     : Tung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@include file="/WEB-INF/jspf/css.jspf" %>
    </head>
    <body>
    <c:if test="${sessionScope.sls}">
        
    </c:if>
        <form action="login" method="post">

            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-5">
                        <div class="card shadow">
                            <div class="card-header bg-primary text-white text-center">
                                <h4>Login</h4>
                            </div>
                            <div class="card-body">
                                <form action="login" method="post">

                                    <div class="mb-3">
                                        <label for="uname" class="form-label fw-bold">Username</label>
                                        <input type="text" class="form-control" placeholder="Enter Username" name="uname" required>
                                    </div>

                                    <div class="mb-3">
                                        <label for="psw" class="form-label fw-bold">Password</label>
                                        <input type="password" class="form-control" placeholder="Enter Password" name="psw" required>
                                    </div>

                                    <div class="mb-3 form-check">
                                        <input type="checkbox" class="form-check-input" id="remember" name="remember">
                                        <label class="form-check-label" for="remember">Remember me</label>
                                    </div>

                                    <div class="d-grid gap-2">
                                        <button class="btn btn-primary" type="submit">Login</button>
                                    </div>

                                </form>
                            </div>

                            <div class="card-footer d-flex justify-content-between align-items-center bg-light">
                                <a href="index.jsp" class="btn btn-secondary btn-sm">Cancel</a>
                                <span class="psw small">Forgot <a href="#" class="text-decoration-none">password?</a></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
