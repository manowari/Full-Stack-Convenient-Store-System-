package backend.user;

import backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import backend.user.User;
import backend.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        if (username != null && !username.isEmpty()) {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            return optionalUser.map(user -> ResponseEntity.ok().body(user))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            // Handle the case when username is not provided or empty
            return ResponseEntity.badRequest().body("Username parameter is required");
        }
    }



}
