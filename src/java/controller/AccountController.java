package controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import model.dao.AccountDAO;

public class AccountController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Lấy action từ request (Do MainController truyền sang)
        String action = request.getParameter("action");
        if (action == null) {
            action = "listAccount";
        }

        // Lấy thông tin user đang đăng nhập để phân quyền sâu
        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("ttdn");
        int role = (currentUser != null) ? currentUser.getRoleInSystem() : 4;

        AccountDAO dao = new AccountDAO();

        try {
            switch (action) {
                // 1. Hiển thị danh sách
                case "listAccount":
                    List<Account> list = dao.listAll();
                    request.setAttribute("dsAcc", list);
                    request.getRequestDispatcher("accounts.jsp").forward(request, response);
                    break;

                // 2. Xóa tài khoản
                case "deleteAccount":
                    String accId = request.getParameter("id");
                    Account targetDelete = dao.getObjectById(accId); // Tìm tk muốn xóa

                    // Logic: Manager (2) không được xóa Admin (1)
                    if (role == 2 && targetDelete.getRoleInSystem() == 1) {
                        request.setAttribute("errorMsg", "Access Denied! Manager không có quyền xóa tài khoản Admin.");
                        request.getRequestDispatcher("accessDenied.jsp").forward(request, response);
                        return;
                    }

                    Account aDel = new Account();
                    aDel.setAccount(accId);
                    dao.deleteRec(aDel);
                    response.sendRedirect("main?action=listAccount"); // Xóa xong gọi lại main
                    break;

                // 3. Thêm tài khoản mới (Insert)
                case "addAccount":
                    // Do lúc insert, form gửi mail bằng name="email"
                    String user = request.getParameter("account");
                    String pass = request.getParameter("password");
                    String last = request.getParameter("lastname");
                    String first = request.getParameter("firstname");
                    Date dob = Date.valueOf(request.getParameter("dob"));
                    boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
                    String phone = request.getParameter("phone");
                    int roleAdd = Integer.parseInt(request.getParameter("role"));
                    boolean active = request.getParameter("active") != null;

                    Account aNew = new Account(user, pass, last, first, dob, gender, phone, active, roleAdd);
                    dao.insertRec(aNew);
                    response.sendRedirect("main?action=listAccount");
                    break;

                // 4. Lấy dữ liệu cũ để hiển thị lên form sửa
                case "editAccount":
                    String idToEdit = request.getParameter("id");
                    Account acc = dao.getObjectById(idToEdit);

                    // Logic: Manager (2) không được sửa Admin (1)
                    if (role == 2 && acc.getRoleInSystem() == 1) {
                        request.setAttribute("errorMsg", "Access Denied! Manager không có quyền chỉnh sửa tài khoản Admin.");
                        request.getRequestDispatcher("accessDenied.jsp").forward(request, response);
                        return;
                    }

                    if (acc != null) {
                        request.setAttribute("acc", acc);
                        request.getRequestDispatcher("update_account.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("main?action=listAccount");
                    }
                    break;

                // 5. Cập nhật dữ liệu vào DB (Update)
                case "updateAccount":
                    String uUser = request.getParameter("account");
                    String uPass = request.getParameter("password");
                    String uLast = request.getParameter("lastname");
                    String uFirst = request.getParameter("firstname");
                    Date uDob = Date.valueOf(request.getParameter("dob"));
                    boolean uGender = Boolean.parseBoolean(request.getParameter("gender"));
                    String uPhone = request.getParameter("phone");
                    int uRole = Integer.parseInt(request.getParameter("role"));
                    boolean uActive = request.getParameter("active") != null;

                    Account aUpdate = new Account(uUser, uPass, uLast, uFirst, uDob, uGender, uPhone, uActive, uRole);
                    dao.updateRec(aUpdate);

                    response.sendRedirect("main?action=listAccount");
                    break;

                default:
                    response.sendRedirect("main?action=listAccount");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
