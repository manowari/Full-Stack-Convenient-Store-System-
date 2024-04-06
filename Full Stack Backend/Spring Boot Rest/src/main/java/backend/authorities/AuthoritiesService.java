package backend.authorities;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesService {

    private final JdbcTemplate jdbcTemplate;

    public AuthoritiesService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void assignAuthority(String user_name, String authority) {
        jdbcTemplate.update("INSERT INTO authorities (username, authority) VALUES (?, ?)", user_name, authority);
    }
}
