/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;

/**
 *
 * @author Tung
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class MainController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); // Sửa lỗi font tiếng Việt

        // --------------------------------------------------------
        // BƯỚC 1: AUTHENTICATION (Xác thực đăng nhập)
        // --------------------------------------------------------
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("ttdn");

        if (currentUser == null) {
            // Chưa đăng nhập -> Trục xuất về trang đăng nhập ngay lập tức
            response.sendRedirect("login.jsp");
            return; // Lệnh return này cực kỳ quan trọng để cắt đứt luồng chạy phía dưới!
        }

        // --------------------------------------------------------
        // BƯỚC 2: TIẾP NHẬN YÊU CẦU (Lấy action)
        // --------------------------------------------------------
        String action = request.getParameter("action");
        if (action == null) {
            action = "dashboard"; // Nếu không truyền action, mặc định cho về trang chủ
        }

        String url = "error.jsp"; // Biến lưu trữ trang đích đến

        // --------------------------------------------------------
// BƯỚC 3: AUTHORIZATION (Phân quyền & Xử lý logic)
// --------------------------------------------------------
        int role = currentUser.getRoleInSystem();

        try {
            switch (action) {
                // ==============================================
                // NHÓM 1: CHỨC NĂNG CHUNG (Tất cả Role đều vào được)
                // ==============================================
                case "dashboard":
                    url = "index.jsp";
                    break;

                case "view_product":
                    // Cho phép tất cả các role (1, 2, 3, 4) xem sản phẩm
                    url = "product"; // Gọi sang ProductController
                    break;

                // ==============================================
                // NHÓM 2: QUẢN LÝ TÀI KHOẢN (Chỉ Role 1 và 2)
                // ==============================================
                case "listAccount":
                case "addAccount":
                case "editAccount":
                case "updateAccount":
                case "deleteAccount":
                    if (role == 1 || role == 2) {
                        // Cho phép đi tiếp sang AccountController.
                        // LƯU Ý: Việc Role 2 không được xóa/sửa Admin cần được 
                        // xử lý tiếp ở AccountController hoặc DAO.
                        url = "account";
                    } else {
                        // Role 3 và 4 bị chặn
                        request.setAttribute("errorMsg", "Access Denied! Bạn không có quyền truy cập quản lý tài khoản.");
                        url = "accessDenied.jsp";
                    }
                    break;

                // ==============================================
                // NHÓM 3: XEM & THÊM/SỬA SẢN PHẨM, DANH MỤC (Role 1, 2, 3)
                // ==============================================
                case "listProduct":
                case "addProduct":
                case "editProduct":
                case "updateProduct":
                case "listCategory":
                case "addCategory":
                case "editCategory":
                case "updateCategory":
                    if (role == 1 || role == 2 || role == 3) {
                        // Dựa vào action có chứa chữ "Product" hay "Category" để chuyển hướng
                        url = action.contains("Product") ? "product" : "category";
                    } else {
                        // Role 4 bị chặn (chỉ được dùng view_product ở Nhóm 1)
                        request.setAttribute("errorMsg", "Access Denied! Bạn chỉ có quyền xem sản phẩm.");
                        url = "accessDenied.jsp";
                    }
                    break;

                // ==============================================
                // NHÓM 4: XÓA SẢN PHẨM, DANH MỤC (Chỉ Role 1, 2)
                // ==============================================
                case "deleteProduct":
                case "deleteCategory":
                    if (role == 1 || role == 2) {
                        url = action.contains("Product") ? "product" : "category";
                    } else {
                        // Role 3, 4 bị chặn không được xóa
                        request.setAttribute("errorMsg", "Access Denied! Bạn không có quyền xóa dữ liệu này.");
                        url = "accessDenied.jsp";
                    }
                    break;

                // ==============================================
                // NHÓM 5: LOGOUT VÀ CÁC TRƯỜNG HỢP KHÁC
                // ==============================================
                case "logout":
                    url="logout";
                    break;

                default:
                    request.setAttribute("errorMsg", "Chức năng không tồn tại!"); //
                    url = "error.jsp"; //
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString()); //
        } finally {
            // --------------------------------------------------------
            // BƯỚC 4: ĐIỀU HƯỚNG TẬP TRUNG (Dispatcher)
            // --------------------------------------------------------
            request.getRequestDispatcher(url).forward(request, response); //
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
