package backend.jwt;

import jakarta.servlet.http.HttpServletRequest;
import backend.login.LoginResponse;
import backend.login.LoginUserDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.bind.annotation.*;
import backend.user.RegisterUserDto;
import backend.user.User;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final CsrfTokenRepository csrfTokenRepository;


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
    public ResponseEntity<Object> register(@RequestBody RegisterUserDto registerUserDto, HttpServletRequest request) {
        System.out.println("POST request to /auth/signup");

        // Your registration logic here
        User registeredUser = authenticationService.signup(registerUserDto);

        // Generate a CSRF token
        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "abc123"); // Replace "abc123" with your actual CSRF token

        // Return the registered user and CSRF token in the response body
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("user", registeredUser);
        responseBody.put("csrfToken", csrfToken.getToken());

        return ResponseEntity.ok().body(responseBody);
    }




    @PostMapping("/backend/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());


        return ResponseEntity.ok(loginResponse);
    }
}
