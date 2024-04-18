package backend.bulkdata.users;

import backend.jwt.AuthenticationService;
import backend.products.Product;
import backend.products.ProductDto;
import backend.products.ProductRepository;
import backend.repo.UserRepository;
import backend.user.User;
import backend.user.UserDetailsDto;
import backend.user.UserService;
import com.opencsv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class BulkUserService {

    private final PasswordEncoder passwordEncoder;

    private final ProductRepository productRepository;



    private final UserRepository userRepository;

    private final AuthenticationService authenticationService;



    public BulkUserService(ProductRepository productRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;

        this.passwordEncoder = passwordEncoder;

        this.authenticationService = authenticationService;

    }

    public List<User> bulkSignup(List<UserDetailsDto> userDetailsList) throws Exception {
        List<User> userList = new ArrayList<>();
        for (UserDetailsDto userDetails : userDetailsList) {
            User user = authenticationService.signup(userDetails);
            if (user != null) {
                userList.add(user);
            }
        }
        return userList;
    }



    public List<Product> bulkProductsAdd(List<ProductDto> productDtoList) {
        // Convert ProductDto to Product entities
        List<Product> productList = productDtoList.stream()
                .map(this::convertToProductEntity)
                .collect(Collectors.toList());

        // Save the list of products
        return productRepository.saveAll(productList);
    }

    private Product convertToProductEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        // Set other properties if needed
        return product;
    }


    public void importUsersFromCsv(String csvData) {
        try (CSVReader reader = new CSVReader(new StringReader(csvData))) {
            String[] headers = reader.readNext(); // Read the headers (first row)

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                try {
                    // Parse user data from CSV row
                    String pf = nextLine[0]; // Assuming pf is the first column
                    String fullName = nextLine[1]; // Assuming Full Name is the second column
                    int workClass = Integer.parseInt(nextLine[2]); // Assuming Work Class is the third column
                    String userName = nextLine[3]; // Assuming Username is the fourth column
                    String email = nextLine[4]; // Assuming Email is the fifth column
                    String password = nextLine[5]; // Assuming Password is the sixth column
                    String userRole = nextLine[6]; // Assuming User Role is the seventh column

                    // Encode the password
                    String encodedPassword = passwordEncoder.encode(password);

                    // Create a new User entity
                    User user = new User(pf, fullName, workClass, userName, email, userRole, encodedPassword);

                    // Save the user entity
                    userRepository.save(user);
                } catch (Exception e) {
                    // Handle exceptions for individual rows (e.g., log error, skip row)
                    Logger.getLogger(UserService.class.getName()).warning("Error processing CSV row: " + e.getMessage());
                }
            }
        } catch (CsvValidationException e) {
            // Handle CSV validation exception (e.g., log error, throw exception)
            Logger.getLogger(UserService.class.getName()).severe("CSV validation error: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions (e.g., IO exception)
            Logger.getLogger(UserService.class.getName()).severe("Error importing CSV: " + e.getMessage());
        }
    }





}

