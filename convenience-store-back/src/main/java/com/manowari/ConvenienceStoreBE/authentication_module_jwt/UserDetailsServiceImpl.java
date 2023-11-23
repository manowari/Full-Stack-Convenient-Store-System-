import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /***
     * UserDetailsServiceImpl.java:
     * 
     * This file is an implementation of Spring Security's UserDetailsService
     * interface.
     * It provides an implementation for the loadUserByUsername method, which Spring
     * Security uses to load user details based on the provided username.
     * The actual logic to load user details from your data source should be
     * implemented within this class.
     * 
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */

    // Inject your user service or repository here
    // private final UserService userService;

    // ...

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement the logic to load user details by username
        // Example: User user = userService.findByUsername(username);

        // You need to return a UserDetails object with username, password, and
        // authorities
        return User.builder()
                .username(username)
                .password("encodedPassword") // Replace with the encoded password
                .roles("USER") // Add roles as needed
                .build();
    }
}