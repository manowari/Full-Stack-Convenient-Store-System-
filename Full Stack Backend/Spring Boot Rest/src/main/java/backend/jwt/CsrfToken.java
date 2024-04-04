package backend.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;


@Configuration

public class CsrfToken {


        @Bean
        public CsrfTokenRepository csrfTokenRepository() {
            CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
            return repository;        }

}
