package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import model.Account;
import model.Category;
import model.Product;
import utilities.ConnectDB;

public class ProductDAO implements Accessible<Product> {

    private ServletContext sc;

    public ProductDAO() {
    }

    public ProductDAO(ServletContext sc) {
        this.sc = sc;
    }

    @Override
    public int insertRec(Product obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "INSERT INTO products(productId, productName, productImage, brief, postedDate, typeId, account, unit, price, discount) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, obj.getProductId());
            ps.setString(2, obj.getProductName());
            ps.setString(3, obj.getProductImage());
            ps.setString(4, obj.getBrief());
            ps.setDate(5, obj.getPostedDate());
            ps.setInt(6, obj.getType().getTypeId()); // Lấy ID từ object Category
            ps.setString(7, obj.getAccount().getAccount()); // Lấy ID từ object Account
            ps.setString(8, obj.getUnit());
            ps.setInt(9, obj.getPrice());
            ps.setInt(10, obj.getDiscount());
            
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int updateRec(Product obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "UPDATE products SET productName=?, productImage=?, brief=?, postedDate=?, typeId=?, account=?, unit=?, price=?, discount=? WHERE productId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, obj.getProductName());
            ps.setString(2, obj.getProductImage());
            ps.setString(3, obj.getBrief());
            ps.setDate(4, obj.getPostedDate());
            ps.setInt(5, obj.getType().getTypeId());
            ps.setString(6, obj.getAccount().getAccount());
            ps.setString(7, obj.getUnit());
            ps.setInt(8, obj.getPrice());
            ps.setInt(9, obj.getDiscount());
            ps.setString(10, obj.getProductId()); // WHERE productId = ...
            
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deleteRec(Product obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "DELETE FROM products WHERE productId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obj.getProductId());
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    // Hàm hỗ trợ map dữ liệu từ ResultSet vào Product Object
    // Vì Product chứa Category và Account object, nên ta cần tạo fake object hoặc query join
    // Ở đây thầy làm cách đơn giản là tạo Object con chỉ chứa ID để tránh query lồng nhau quá nhiều
    private Product mapProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getString("productId"));
        p.setProductName(rs.getString("productName"));
        p.setProductImage(rs.getString("productImage"));
        p.setBrief(rs.getString("brief"));
        p.setPostedDate(rs.getDate("postedDate"));
        p.setUnit(rs.getString("unit"));
        p.setPrice(rs.getInt("price"));
        p.setDiscount(rs.getInt("discount"));
        
        // Map Category (Chỉ set ID để tham chiếu)
        Category c = new Category();
        c.setTypeId(rs.getInt("typeId"));
        p.setType(c);
        
        // Map Account (Chỉ set ID để tham chiếu)
        Account a = new Account();
        a.setAccount(rs.getString("account"));
        p.setAccount(a);
        
        return p;
    }

    @Override
    public Product getObjectById(String id) {
        Product p = null;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "SELECT * FROM products WHERE productId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = mapProduct(rs);
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public List<Product> listAll() {
        List<Product> list = new ArrayList<>();
        try {
            Connection conn = new ConnectDB().getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    // Hàm listByCategory theo PDF trang 5 (Quan trọng cho trang Category)
    public List<Product> listByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "SELECT * FROM products WHERE typeId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(ProductDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}