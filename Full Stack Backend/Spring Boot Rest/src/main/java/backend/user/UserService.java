package backend.user;

// UserService.java

import backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User findByPf(String pf) {
        Optional<User> optionalUser = userRepository.findByPf(pf);
        return optionalUser.orElse(null);
    }


     Optional<User> getUserByIdentifier(String identifier) {
        if (isValidEmail(identifier)) {
            return userRepository.findByEmail(identifier);
        } else if (isValidPf(identifier)) {
            // Assuming pf is a numeric string (integer or long)
            return userRepository.findByPf(identifier);
        } else {
            // Assuming username is an alphanumeric string
            return userRepository.findByUserName(identifier);
        }
    }

     boolean isValidEmail(String email) {
        // Implement email validation logic
        // For simplicity, you can use a basic regex pattern
        // Make sure to adjust it according to your requirements
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

     boolean isValidPf(String pf) {
        // Assuming pf is a numeric string (integer or long)
        // Validate if it can be parsed as a Long
        try {
            return pf.startsWith("pf");

        } catch (NumberFormatException e) {
            return false;
        }
    }




}


