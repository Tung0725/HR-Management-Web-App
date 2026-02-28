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
import model.Account;
import utilities.ConnectDB;

public class AccountDAO implements Accessible<Account> {

    // Biến để lưu context nếu cần đọc tham số từ web.xml (theo PDF trang 3)
    private ServletContext sc;

    public AccountDAO() {
    }

    public AccountDAO(ServletContext sc) {
        this.sc = sc;
    }

    @Override
    public int insertRec(Account obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "INSERT INTO accounts(account, pass, lastName, firstName, birthday, gender, phone, isUse, roleInSystem) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, obj.getAccount());
            ps.setString(2, obj.getPass());
            ps.setString(3, obj.getLastName());
            ps.setString(4, obj.getFirstName());
            ps.setDate(5, obj.getBirthday());
            ps.setBoolean(6, obj.isGender());
            ps.setString(7, obj.getPhone());
            ps.setBoolean(8, obj.isUse());
            ps.setInt(9, obj.getRoleInSystem());
            
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int updateRec(Account obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "UPDATE accounts SET pass=?, lastName=?, firstName=?, birthday=?, gender=?, phone=?, isUse=?, roleInSystem=? WHERE account=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, obj.getPass());
            ps.setString(2, obj.getLastName());
            ps.setString(3, obj.getFirstName());
            ps.setDate(4, obj.getBirthday());
            ps.setBoolean(5, obj.isGender());
            ps.setString(6, obj.getPhone());
            ps.setBoolean(7, obj.isUse());
            ps.setInt(8, obj.getRoleInSystem());
            ps.setString(9, obj.getAccount()); // Điều kiện WHERE
            
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    // Hàm updateIsUsed theo PDF trang 3 (Dùng để khóa/mở khóa tài khoản)
    public int updateIsUsed(String acc, boolean isUsed) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "UPDATE accounts SET isUse=? WHERE account=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, isUsed);
            ps.setString(2, acc);
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deleteRec(Account obj) {
        int result = 0;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "DELETE FROM accounts WHERE account=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obj.getAccount());
            result = ps.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Account getObjectById(String id) {
        Account acc = null;
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "SELECT * FROM accounts WHERE account=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                acc = new Account(
                    rs.getString("account"), rs.getString("pass"), 
                    rs.getString("lastName"), rs.getString("firstName"), 
                    rs.getDate("birthday"), rs.getBoolean("gender"), 
                    rs.getString("phone"), rs.getBoolean("isUse"), 
                    rs.getInt("roleInSystem")
                );
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return acc;
    }
    
    // Hàm loginSuccess theo PDF trang 3
    public Account loginSuccess(String acc, String pass) {
        Account result = null;
        try {
            Connection conn = new ConnectDB().getConnection();
            // Chỉ login được nếu đúng user, pass VÀ tài khoản đang active (isUse=1)
            String sql = "SELECT * FROM accounts WHERE account=? AND pass=? AND isUse=1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, acc);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = new Account(
                    rs.getString("account"), rs.getString("pass"), 
                    rs.getString("lastName"), rs.getString("firstName"), 
                    rs.getDate("birthday"), rs.getBoolean("gender"), 
                    rs.getString("phone"), rs.getBoolean("isUse"), 
                    rs.getInt("roleInSystem")
                );
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Account> listAll() {
        List<Account> list = new ArrayList<>();
        try {
            Connection conn = new ConnectDB().getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM accounts");
            while (rs.next()) {
                Account acc = new Account(
                    rs.getString("account"), rs.getString("pass"), 
                    rs.getString("lastName"), rs.getString("firstName"), 
                    rs.getDate("birthday"), rs.getBoolean("gender"), 
                    rs.getString("phone"), rs.getBoolean("isUse"), 
                    rs.getInt("roleInSystem")
                );
                list.add(acc);
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    // Hàm listByRole theo PDF trang 3
    public List<Account> listByRole(int role) {
        List<Account> list = new ArrayList<>();
        try {
            Connection conn = new ConnectDB().getConnection();
            String sql = "SELECT * FROM accounts WHERE roleInSystem=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = new Account(
                    rs.getString("account"), rs.getString("pass"), 
                    rs.getString("lastName"), rs.getString("firstName"), 
                    rs.getDate("birthday"), rs.getBoolean("gender"), 
                    rs.getString("phone"), rs.getBoolean("isUse"), 
                    rs.getInt("roleInSystem")
                );
                list.add(acc);
            }
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}