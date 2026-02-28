package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Account;
import model.Category;
import model.Product;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "ProductController", urlPatterns = {"/product"})
public class ProductController extends HttpServlet {

    private String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        // Hàm hỗ trợ lưu file ảnh
        Part part = request.getPart("productImageFile"); // Lấy file từ form
        if (part != null && part.getSize() > 0) {
            // Lấy thư mục thật của project trên server
            String realPath = request.getServletContext().getRealPath("/productImages");
            File uploadDir = new File(realPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Tạo folder nếu chưa có
            }
            String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            part.write(realPath + File.separator + fileName); // Lưu file vào folder
            return fileName; // Trả về tên file để lưu vào Database
        }
        return null;
    }
    // Thêm hàm này vào ProductController để đọc các ô Input Text khi dùng Multipart

    private String getValue(HttpServletRequest request, String name) throws IOException, ServletException {
        Part part = request.getPart(name);
        if (part != null) {
            java.util.Scanner s = new java.util.Scanner(part.getInputStream(), "UTF-8");
            if (s.hasNext()) {
                return s.nextLine();
            }
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "listProduct";
        }

        ProductDAO dao = new ProductDAO();
        CategoryDAO catDao = new CategoryDAO();

        try {
            switch (action) {
                // 1. Dành cho Admin/Manager/Staff quản lý
                case "listProduct":
                    request.setAttribute("dsProduct", dao.listAll());
                    request.getRequestDispatcher("products.jsp").forward(request, response);
                    break;

                // 2. Dành cho User xem (Giao diện thẻ)
                case "view_product":
                    request.setAttribute("dsProduct", dao.listAll());
                    request.getRequestDispatcher("view_product.jsp").forward(request, response);
                    break;

                // 3. Xóa sản phẩm
                case "deleteProduct":
                    String idDelete = request.getParameter("id");
                    Product pDel = new Product();
                    pDel.setProductId(idDelete);
                    dao.deleteRec(pDel);
                    response.sendRedirect("main?action=listProduct");
                    break;

                // 4. Thêm sản phẩm mới (Mở form và Lưu)
                case "addProduct":
                    String newId = request.getParameter("productId");
                    if (newId == null) {
                        // Nếu chưa có ID tức là người dùng mới bấm nút "Add New", ta mở Form
                        request.setAttribute("listCate", catDao.listAll());
                        request.getRequestDispatcher("add_product.jsp").forward(request, response);
                    } else {
                        // Nếu đã có ID tức là đang Submit form
                        String name = request.getParameter("productName");
                        String img = request.getParameter("productImage");
                        String brief = request.getParameter("brief");
                        Date date = Date.valueOf(request.getParameter("postedDate"));
                        String unit = request.getParameter("unit");
                        int price = Integer.parseInt(request.getParameter("price"));
                        int discount = Integer.parseInt(request.getParameter("discount"));
                        int typeId = Integer.parseInt(request.getParameter("typeId"));

                        // Gọi hàm upload để lưu file và lấy tên file
                        String imgName = uploadFile(request);
                        if (imgName == null) {
                            imgName = "default.jpg"; // Đề phòng lỗi
                        }
                        // Lấy người đang đăng nhập làm người tạo sản phẩm
                        HttpSession session = request.getSession();
                        Account currentUser = (Account) session.getAttribute("ttdn");
                        String accName = (currentUser != null) ? currentUser.getAccount() : "admin";

                        Category c = new Category();
                        c.setTypeId(typeId);
                        Account a = new Account();
                        a.setAccount(accName);

                        Product pNew = new Product(newId, name, img, brief, date, c, a, unit, price, discount);
                        dao.insertRec(pNew);
                        response.sendRedirect("main?action=listProduct");
                    }
                    break;

                // 5. Mở form cập nhật
                case "editProduct":
                    String idEdit = request.getParameter("id");
                    Product pEdit = dao.getObjectById(idEdit);
                    request.setAttribute("p", pEdit);
                    request.setAttribute("listCate", catDao.listAll());
                    request.getRequestDispatcher("update_product.jsp").forward(request, response);
                    break;

                // 6. Lưu dữ liệu cập nhật
                case "updateProduct":
                    String uId = request.getParameter("productId");
                    String uName = request.getParameter("productName");
                    String uImg = request.getParameter("productImage");
                    String uBrief = request.getParameter("brief");
                    Date uDate = Date.valueOf(request.getParameter("postedDate"));
                    String uUnit = request.getParameter("unit");
                    int uPrice = Integer.parseInt(request.getParameter("price"));
                    int uDiscount = Integer.parseInt(request.getParameter("discount"));
                    int uTypeId = Integer.parseInt(request.getParameter("typeId"));

                    // Xử lý ảnh khi update
                    String uImg = uploadFile(request);
                    if (uImg == null) {
                        // Nếu user không chọn ảnh mới, lấy lại tên ảnh cũ từ thẻ input hidden
                        uImg = request.getParameter("oldImage");
                    }

                    // Người sửa
                    HttpSession session2 = request.getSession();
                    Account currentU = (Account) session2.getAttribute("ttdn");
                    String uAcc = (currentU != null) ? currentU.getAccount() : "admin";

                    Category cU = new Category();
                    cU.setTypeId(uTypeId);
                    Account aU = new Account();
                    aU.setAccount(uAcc);

                    Product pUpdate = new Product(uId, uName, uImg, uBrief, uDate, cU, aU, uUnit, uPrice, uDiscount);
                    dao.updateRec(pUpdate);
                    response.sendRedirect("main?action=listProduct");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
