import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/smartmes?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";
        
        try {
            // Load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully");
            
            // Establish connection
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully");
            
            // Close connection
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver loading failed: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }
}