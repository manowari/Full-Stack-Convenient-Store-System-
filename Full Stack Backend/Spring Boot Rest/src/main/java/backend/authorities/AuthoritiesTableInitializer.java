package backend.authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthoritiesTableInitializer {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthoritiesTableInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createAuthoritiesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS authorities ("
                + "    username VARCHAR(50) NOT NULL,"
                + "    authority VARCHAR(50) NOT NULL,"
                + "    PRIMARY KEY (username, authority)"
                + ")";
        jdbcTemplate.execute(sql);
    }

    private void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "    id BIGINT AUTO_INCREMENT PRIMARY KEY,"
                + "    pf VARCHAR(255),"
                + "    full_name VARCHAR(255),"
                + "    work_class INT,"
                + "    user_name VARCHAR(255),"
                + "    email VARCHAR(255),"
                + "    user_role VARCHAR(255),"
                + "    password VARCHAR(255)"
                + ")";
        jdbcTemplate.execute(sql);
    }
}
