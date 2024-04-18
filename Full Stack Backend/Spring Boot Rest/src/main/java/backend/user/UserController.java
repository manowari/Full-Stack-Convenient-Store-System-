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

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

    }

    @GetMapping("/{identifier}")
    public ResponseEntity<User> getUserDetails(@PathVariable String identifier, Principal principal) {
        // Check if the principal (authenticated user) is authorized to access the endpoint
        if (!isAuthorized(principal, identifier)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<User> userOptional = userService.getUserByIdentifier(identifier);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        // Set password to null to exclude it from the response
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }



    private boolean isAuthorized(Principal principal, String identifier) {
        // Implement your authorization logic here
        // For example, check if the principal's username matches the requested user's username
        String principalUsername = principal.getName();
        Optional<User> requestedUserOptional = userService.getUserByIdentifier(identifier);
        if (requestedUserOptional.isEmpty()) {
            return false;
        }

        User requestedUser = requestedUserOptional.get();
        return requestedUser.getUsername().equals(principalUsername);
    }



}
