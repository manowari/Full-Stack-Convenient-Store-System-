package backend.jwt;

import backend.user.UserDetailsDto;
import jakarta.servlet.http.HttpServletRequest;
import backend.login.LoginResponse;
import backend.login.LoginUserDto;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.bind.annotation.*;
 import backend.user.User;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final CsrfTokenRepository csrfTokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, CsrfTokenRepository csrfTokenRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.csrfTokenRepository = csrfTokenRepository;

    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        System.out.println("GET request to /auth/csrf-token");

        return csrfTokenRepository.generateToken(request);
    }







    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody UserDetailsDto registerUserDto, HttpServletRequest request) {
        try {
            System.out.println("POST request to /auth/signup");

            // Your registration logic here
            User registeredUser = authenticationService.signup(registerUserDto);

            // Return only the registered user in the response body
            return ResponseEntity.ok().body(registeredUser);
        } catch (Exception ex) {
            // If an exception occurs during registration (e.g., username or email already exists), return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            User authenticatedUser = authenticationService.authenticate(loginUserDto);

            if (authenticatedUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            String jwtToken = jwtService.generateToken(authenticatedUser);
            long expiresIn = jwtService.getExpirationTime();

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("expiresIn", expiresIn);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

}
