package backend.authorities;
import backend.repo.UserRepository;
import backend.user.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthoritiesService authoritiesService;

    public CustomUserDetailsService(UserRepository userRepository, AuthoritiesService authoritiesService) {
        this.userRepository = userRepository;
        this.authoritiesService = authoritiesService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user from the repository
        Optional<User> optionalUser = userRepository.findByUserName(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Get the work class from the user and assign authority based on it
            int workClass = user.getWorkClass();
            switch (workClass) {
                case 1:
                    authoritiesService.assignAuthority(username, "WORK_CLASS_1");
                    break;
                case 2:
                    authoritiesService.assignAuthority(username, "WORK_CLASS_2");
                    break;
                case 3:
                    authoritiesService.assignAuthority(username, "WORK_CLASS_3");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid work class");
            }

            // Return UserDetails object with user details
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities("USER") // Assuming all users have a default authority
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
