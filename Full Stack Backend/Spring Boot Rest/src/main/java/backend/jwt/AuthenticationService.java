package backend.jwt;


import backend.login.LoginUserDto;
import backend.user.UserDetailsDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import backend.repo.UserRepository;
 import backend.user.User;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(UserDetailsDto input) {
        // Check if pf, username, or email already exists



        boolean pfExists = userRepository.existsByPf(input.getPf());
        boolean userNameExists = userRepository.existsByUserName(input.getUserName());
        boolean emailExists = userRepository.existsByEmail(input.getEmail());

        // Create a message indicating which parameters already exist
        StringBuilder message = new StringBuilder("The following parameters already exist:");

        if (pfExists) {
            message.append(" pf");
        }
        if (userNameExists) {
            message.append(" username");
        }
        if (emailExists) {
            message.append(" email");
        }

        // If any parameter already exists, return null and send the message
        if (pfExists || userNameExists || emailExists) {
            System.out.println(message.toString());
            return null;
        }

        // Create a new User entity
        User user = new User(input.getPf(), input.getFullName(), input.getWorkClass(), input.getUserName(), input.getEmail(), input.getUserRole(), passwordEncoder.encode(input.getPassword()));

        // Save the user entity
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }



}

