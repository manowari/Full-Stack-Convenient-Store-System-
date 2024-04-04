package backend.user;

// UserService.java

import backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.user.User;
 import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUserName(username);
        return optionalUser.orElse(null);
    }

    public User findByEmail(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        return optionalUser.orElse(null);
    }




}


