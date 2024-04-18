package backend.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import backend.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String username);
    Optional<User> findByPf(String pf);




    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.pf = ?1 AND u.pf IS NOT NULL")
    boolean existsByPf(String pf);

    boolean existsByUserName(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u.password FROM User u WHERE u.email = ?1")
    String findEncodedPasswordByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = ?1 AND u.password = ?2")
    boolean verifyPasswordByEmail(String email, String password);
}


