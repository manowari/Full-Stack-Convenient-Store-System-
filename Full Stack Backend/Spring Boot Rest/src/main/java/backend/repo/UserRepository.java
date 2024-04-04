package backend.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import backend.user.User;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import backend.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String username);

    boolean existsByPf(String pf);
    boolean existsByUserName(String username); // Define existsByUserName method
    boolean existsByEmail(String email); // Define existsByEmail method
}

