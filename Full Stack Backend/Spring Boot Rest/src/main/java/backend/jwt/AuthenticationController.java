package backend.jwt;

import backend.user.UserDetailsDto;
import jakarta.servlet.http.HttpServletRequest;
import backend.login.LoginResponse;
import backend.login.LoginUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> register(@RequestBody UserDetailsDto registerUserDto, HttpServletRequest request) throws Exception {
        System.out.println("POST request to /auth/signup");

        // Your registration logic here
        User registeredUser = authenticationService.signup(registerUserDto);

        // Return only the registered user in the response body
        return ResponseEntity.ok().body(registeredUser);
    }





    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) throws Exception {

        logger.debug("Received login request with body: {}", loginUserDto);

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        // Check if authenticatedUser is null before proceeding
        if (authenticatedUser == null) {
            // Handle case where user is not found or authentication fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}
