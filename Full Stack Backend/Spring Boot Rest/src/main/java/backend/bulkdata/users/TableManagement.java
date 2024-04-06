package backend.bulkdata.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TableManagement {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void dropAndRecreateUsersTable() {
        // Drop the users table if it exists
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");

        // Recreate the users table
        String createTableSQL = "CREATE TABLE users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "pf VARCHAR(255) NOT NULL," +
                "full_name VARCHAR(255) NOT NULL," +
                "work_class INT NOT NULL," +
                "user_name VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL," +
                "user_role VARCHAR(255) NOT NULL," +
                "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";




        jdbcTemplate.execute(createTableSQL);
    }
}
