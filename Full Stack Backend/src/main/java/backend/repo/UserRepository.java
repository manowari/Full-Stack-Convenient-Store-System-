package backend.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import backend.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username); // New method to find by username

}
