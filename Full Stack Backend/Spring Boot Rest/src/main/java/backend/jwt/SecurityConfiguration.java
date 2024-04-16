package backend.jwt;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CsrfTokenRepository csrfTokenRepository;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider,
            CsrfTokenRepository csrfTokenRepository,
            CorsConfigurationSource corsConfigurationSource
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.csrfTokenRepository = csrfTokenRepository;
        this.corsConfigurationSource = corsConfigurationSource;

        System.out.println("SecurityConfiguration constructor executed.");
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        System.out.println("Configuring security filter chain...");
//
//        http
//                .cors().configurationSource(corsConfigurationSource())
//                .and()
//                .csrf().ignoringRequestMatchers("/data-import/**", "/test/**", "/auth/**")
//                .and()
//                .authorizeRequests()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .requestMatchers("/auth/**", "/test/**").permitAll()
//
//                .requestMatchers("/products/add", "/products/remove").hasAuthority("WORK_CLASS_3")
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        System.out.println("Finished...");
//
//        return http.build();
//    }
//

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("Configuring security filter chain...");

        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().ignoringRequestMatchers("/data-import/**", "/test/**", "/auth/**")
                .and()
                .authorizeRequests()
                // Permit access to the login endpoint without authentication
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                // Permit access to other public endpoints if needed
                .requestMatchers("/test/**").permitAll()
                // Require authentication for all other requests
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        System.out.println("Finished configuring security filter chain...");

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:4200", "http://localhost:10000", "http://localhost:9085"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "no-auth", "x-xsrf-token", "Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(Arrays.asList("Content-Type", "x-xsrf-token", "Authorization", "Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Message"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(4000000L);
        configuration.addAllowedMethod("OPTIONS");
        configuration.addAllowedMethod("HEAD");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PATCH");
        System.out.println("-----------------------------------");
        System.out.println(configuration.getAllowedOrigins());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
