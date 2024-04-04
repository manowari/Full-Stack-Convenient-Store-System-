package backend.routine;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class CheckAndCreateUsersTable {
    public  void createTable( ) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("C:\\Users\\grub\\Documents\\ALEKI\\Torch\\conv store\\Full-Stack-Convenient-Store-System-\\Full Stack Backend\\src\\main\\resources\\application.properties"));
        } catch (FileNotFoundException e) {
            System.err.println("Application properties file not found: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Failed to load application properties file: " + e.getMessage());
            return;
        }

        String url = properties.getProperty("spring.datasource.url");
        String username = properties.getProperty("spring.datasource.username");
        String password = properties.getProperty("spring.datasource.password");

        String checkTableExistsQuery = "SHOW TABLES LIKE 'users'";
        String createTableQuery = "CREATE TABLE users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pf VARCHAR(50) UNIQUE NOT NULL," +
                "full_name VARCHAR(255) NOT NULL," +
                "email VARCHAR(100) UNIQUE NOT NULL," +
                "username VARCHAR(100) UNIQUE NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "work_class INT NOT NULL," +
                "user_role VARCHAR(50) NOT NULL DEFAULT 'officer'," +
                "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";


        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            // Check if the 'users' table already exists
            ResultSet resultSet = stmt.executeQuery(checkTableExistsQuery);
            if (!resultSet.next()) {
                // Table does not exist, create it
                stmt.executeUpdate(createTableQuery);
                System.out.println("Table 'users' created successfully!");
            } else {
                System.out.println("Table 'users' already exists!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

