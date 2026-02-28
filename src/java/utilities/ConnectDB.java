/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Tung
 */
public class ConnectDB {

    private String hostName;
    private String port;
    private String dbName;
    private String user;
    private String pass;

    public ConnectDB() {
        this.hostName = "localhost";
        this.port = "1433";
        this.dbName = "ProductIntro";
        this.user = "sa";
        this.pass = "12345";
    }

    public ConnectDB(ServletContext sc) {
        this.hostName = sc.getInitParameter("hostAddress");
        this.dbName = sc.getInitParameter("dbName");
        this.port = sc.getInitParameter("port");
        this.user = sc.getInitParameter("userName");
        this.pass = sc.getInitParameter("userPass");
    }

    public String getURLString() {
        // SỬA: Sắp xếp đúng thứ tự Host -> Port -> DbName
        // THÊM: encrypt=true;trustServerCertificate=true để tránh lỗi bảo mật trên Driver mới
        String fmt = "jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;";
        return String.format(fmt, this.hostName, this.port, this.dbName, this.user, this.pass);
    }

    public Connection getConnection() {
        Connection kn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            kn = DriverManager.getConnection(getURLString());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kn;
    }

}
