import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/HostelManagement";

    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = "******";  // Replace with your MySQL password

    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
