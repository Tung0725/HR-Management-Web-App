/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.dao.AccountDAO;

/**
 *
 * @author Tung
 */
public class LoginController extends HttpServlet {

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
        // --- B1: Lấy dữ liệu từ tham số (parameter) của form gửi lên
        String acc = request.getParameter("uname");
        String pass = request.getParameter("psw");

        // --- B2: Gọi lớp DaoHuman để kiểm tra đăng nhập trong Database
        // Nếu tìm thấy, nó trả về một đối tượng DtoHuman (chứa thông tin người dùng)
        Account x = new AccountDAO().loginSuccess(acc, pass);

        // --- B3: Xử lý kết quả trả về
        if (x != null) {
            // Nếu đăng nhập thành công (x khác null), chuyển hướng về trang chủ index.jsp
            javax.servlet.http.HttpSession session = request.getSession();
            session.setAttribute("ttdn", x);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
          
            // Nếu thất bại, tạo một thông báo lỗi "err" và quay lại trang login.jsp
            request.setAttribute("err", "User name or Passord is incorrect !. Try again");
            request.getRequestDispatcher("login.jsp").forward(request, response);
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
