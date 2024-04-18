package backend;

import backend.authorities.AuthoritiesTableInitializer;
import backend.bulkdata.users.TableManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaRepositories
//@EnableSwagger2
//@EnableAutoConfiguration(exclude = {
//		springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration.class
//})
public class StoreBackendApplication {
  	public static void main(String[] args) {


//		System.setProperty("server.port", "9085");
//
		SpringApplication.run(StoreBackendApplication.class, args);


//		checkAndCreateUsersTable.createTable();


	}


//	@Bean
//	public CommandLineRunner initializeAuthoritiesTable(AuthoritiesTableInitializer tableInitializer) {
//		System.out.println("CUSTOM_TEST Auth table being created  ");
//
//		return args -> tableInitializer.createAuthoritiesTable();
//	}
//







//
//	@ControllerAdvice
//	public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//
//		@ExceptionHandler(Exception.class)
//		public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//			// Custom exception handling logic
//			// You need to replace "..." with appropriate response body or object
//			return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//
//	@Configuration
//	public class WebConfig extends WebMvcConfigurationSupport {
//
//		@Override
//		public void addResourceHandlers(ResourceHandlerRegistry registry) {
//			registry.addResourceHandler("/swagger-ui/**")
//					.addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
//		}
	//}


}
