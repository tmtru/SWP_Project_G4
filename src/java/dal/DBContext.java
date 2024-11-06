package dal;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {
    protected Connection connection;

    public DBContext() {
        try {
            String user = "root";
            String pass = "12345";
            String url = "jdbc:mysql://localhost:3306/QuanLyNhaTroDB";
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void main(String[] args) {
        // Create an instance of DBContext to check the connection
        DBContext dbContext = new DBContext();
        
        // Check if the connection is not null
        if (dbContext.connection != null) {
            System.out.println("Database connected successfully.");
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
    
}

