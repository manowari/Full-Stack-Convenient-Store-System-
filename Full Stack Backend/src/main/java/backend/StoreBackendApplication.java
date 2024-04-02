package backend;

import backend.routine.CheckAndCreateUsersTable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
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
//@EnableSwagger2
//@EnableAutoConfiguration(exclude = {
//		springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration.class
//})
public class StoreBackendApplication {
	static CheckAndCreateUsersTable checkAndCreateUsersTable = new CheckAndCreateUsersTable();

	public static void main(String[] args) {

		SpringApplication.run(StoreBackendApplication.class, args);
		checkAndCreateUsersTable.createTable();


	}


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
