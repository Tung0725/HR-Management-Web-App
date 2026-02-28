package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Category;
import model.dao.CategoryDAO;

// Map chung vào một đường dẫn
public class CategoryController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8"); // Xử lý tiếng Việt

        // 1. Lấy tham số action để biết người dùng muốn làm gì
        String action = request.getParameter("action");
        if (action == null) {
            action = "listCategory"; // Mặc định là hiện danh sách
        }

        CategoryDAO dao = new CategoryDAO();

        try {
            switch (action) {
                case "listCategory":
                    listCategories(request, response, dao);
                    break;
                case "deleteCategory":
                    deleteCategory(request, response, dao);
                    break;
                case "addCategory":
                    insertCategory(request, response, dao);
                    break;
                case "editCategory":
                    showUpdateForm(request, response, dao);
                    break;
                case "updateCategory":
                    updateCategory(request, response, dao);
                    break;
                default:
                    listCategories(request, response, dao);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- CÁC HÀM CON XỬ LÝ TỪNG CHỨC NĂNG ---
    private void listCategories(HttpServletRequest request, HttpServletResponse response, CategoryDAO dao)
            throws ServletException, IOException {
        List<Category> list = dao.listAll();
        request.setAttribute("ds", list); // "ds" giống biến em dùng ở file JSP cũ
        request.getRequestDispatcher("categories.jsp").forward(request, response);
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response, CategoryDAO dao)
            throws IOException {
        String idStr = request.getParameter("id");
        Category c = new Category();
        c.setTypeId(Integer.parseInt(idStr));
        dao.deleteRec(c);
        response.sendRedirect("category?action=list"); // Load lại trang danh sách
    }

    private void insertCategory(HttpServletRequest request, HttpServletResponse response, CategoryDAO dao)
            throws IOException {
        String name = request.getParameter("typeName");
        String memo = request.getParameter("memo");

        // ID tự tăng nên constructor chỉ cần name và memo (tùy constructor em viết)
        Category c = new Category(0, name, memo);
        dao.insertRec(c);
        response.sendRedirect("main?action=listCategory");
    }

    private void showUpdateForm(HttpServletRequest request, HttpServletResponse response, CategoryDAO dao)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        Category c = dao.getObjectById(idStr);
        request.setAttribute("cate", c); // Gửi đối tượng sang JSP để hiện dữ liệu cũ
        request.getRequestDispatcher("update_category.jsp").forward(request, response);
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response, CategoryDAO dao)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("typeId"));
        String name = request.getParameter("typeName");
        String memo = request.getParameter("memo");

        Category c = new Category(id, name, memo);
        dao.updateRec(c);
        response.sendRedirect("category?action=list");
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
