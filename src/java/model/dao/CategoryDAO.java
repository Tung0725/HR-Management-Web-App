package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import model.Category;
import utilities.ConnectDB;

public class CategoryDAO implements Accessible<Category> {
    
    private ServletContext sc;

    public CategoryDAO() {
    }

    public CategoryDAO(ServletContext sc) {
        this.sc = sc;
    }

    @Override
    public int insertRec(Category obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            // typeId identity nên không insert
            String sqlCommand = "INSERT INTO categories(categoryName, memo) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sqlCommand);
            ps.setString(1, obj.getCategoryName());
            ps.setString(2, obj.getMemo());
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int updateRec(Category obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sqlCommand = "UPDATE categories SET categoryName = ?, memo = ? WHERE typeId = ?";
            PreparedStatement ps = conn.prepareStatement(sqlCommand);
            ps.setString(1, obj.getCategoryName());
            ps.setString(2, obj.getMemo());
            ps.setInt(3, obj.getTypeId());
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deleteRec(Category obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sqlCommand = "DELETE FROM categories WHERE typeId = ?";
            PreparedStatement ps = conn.prepareStatement(sqlCommand);
            ps.setInt(1, obj.getTypeId());
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Category getObjectById(String id) {
        Category result = null;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sqlCommand = "SELECT * FROM categories WHERE typeId = ?";
            PreparedStatement ps = conn.prepareStatement(sqlCommand);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = new Category(rs.getInt("typeId"), rs.getString("categoryName"), rs.getString("memo"));
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Category> listAll() {
        List<Category> list = new ArrayList<>();
        try {
            Connection conn = new ConnectDB().getConnection();
            Statement cmd = conn.createStatement();
            String sqlCommand = "SELECT * FROM categories";
            ResultSet rs = cmd.executeQuery(sqlCommand);
            while (rs.next()) {
                Category c = new Category(rs.getInt("typeId"), rs.getString("categoryName"), rs.getString("memo"));
                list.add(c);
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}