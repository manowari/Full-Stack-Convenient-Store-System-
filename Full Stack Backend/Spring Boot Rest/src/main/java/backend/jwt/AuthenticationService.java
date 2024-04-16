package backend.jwt;


import backend.AESUtil;
import backend.login.LoginUserDto;
import backend.user.UserDetailsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import backend.repo.UserRepository;
 import backend.user.User;

import java.security.GeneralSecurityException;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

     private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private final BCryptPasswordEncoder passwordEncoder;



    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(UserDetailsDto input) throws Exception {
        // Check if pf, username, or email already exists



        boolean pfExists = userRepository.existsByPf(input.getPf());
        boolean userNameExists = userRepository.existsByUserName(input.getUserName());
        boolean emailExists = userRepository.existsByEmail(input.getEmail());

        // Create a message indicating which parameters already exist
        StringBuilder message = new StringBuilder("The following parameters already exist:");
//
//        if (pfExists) {
//            message.append(" pf");
//        }
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
        String hashedPassword = passwordEncoder.encode(input.getPassword());

        User user = new User(input.getPf(), input.getFullName(), input.getWorkClass(), input.getUserName(), input.getEmail(),hashedPassword, input.getUserRole());

        // Save the user entity
        return userRepository.save(user);
    }



    public User authenticate(LoginUserDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            // Handle authentication failure (invalid credentials, locked user, etc.)
            throw new BadCredentialsException("Invalid email or password");
        }

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }



//    public User authenticate(LoginUserDto input) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            input.getEmail(),
//                            input.getPassword()
//                    )
//            );
//
//            // Fetch encoded password from database
//            String encodedPasswordFromDB = userRepository.findEncodedPasswordByEmail(input.getEmail());
//
//            // Encode password from JSON body
//            String rawPassword = input.getPassword();
//            String encodedPasswordFromJSON = passwordEncoder.encode(rawPassword);
//
//
////            String encodedPasswordFromJSON = AESUtil.encrypt(input.getPassword());
//            // Log encoded passwords
//            logger.debug("Encoded password from DB: {}", encodedPasswordFromDB);
//            logger.debug("Encoded password from JSON body: {}", encodedPasswordFromJSON);
//
//            // Check if passwords match
//            if (encodedPasswordFromDB != null && passwordEncoder.matches(rawPassword, encodedPasswordFromDB)) {
//                // Passwords match, retrieve user
//                return userRepository.findByEmail(input.getEmail())
//                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + input.getEmail()));
//            } else {
//                // Passwords don't match, throw BadCredentialsException
//                throw new BadCredentialsException("Invalid email or password");
//            }
//        } catch (BadCredentialsException ex) {
//            throw new BadCredentialsException("Invalid email or password", ex);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


//    public User authenticate(LoginUserDto input) {
//        String email = input.getEmail();
//        String password = input.getPassword();
//
//        // Check if the user exists
//        if (!userRepository.existsByEmail(email)) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//
//        // Retrieve the encoded password from the database
//        String encodedPasswordFromDB = userRepository.findEncodedPasswordByEmail(email);
//
//        // Verify if the provided password matches the encoded password from the database
//        if (!passwordEncoder.matches(password, encodedPasswordFromDB)) {
//            throw new BadCredentialsException("Invalid password");
//        }
//
//        // Authentication successful, retrieve the user details
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
//    }




//
//
//    public User authenticate(LoginUserDto input) throws Exception {
//
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            input.getEmail(),
//                            input.getPassword()
//                    )
//            );
//
//
//            // Retrieve the user by email
//            User user = userRepository.findByEmail(input.getEmail())
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + input.getEmail()));
//
//            // Retrieve the encrypted password from the database
//            String encryptedPasswordFromDB = userRepository.findEncodedPasswordByEmail(input.getEmail());
//
//
//            String hashedPassword = AESUtil.encrypt(input.getPassword());
//
//
//            // Decrypt the encrypted password from the database
//
//            String decryptedPasswordFromDB = AESUtil.decrypt(encryptedPasswordFromDB);
//
//            // Compare the decrypted password from the database with the input password
//            if (encryptedPasswordFromDB.equals(hashedPassword)) {
//                return user;
//            } else {
//
//                logger.debug(" encryptedPasswordFromDB - " + decryptedPasswordFromDB + " dep " + encryptedPasswordFromDB + " vs " + hashedPassword);
//                throw new BadCredentialsException("Invalid email or password");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
}

