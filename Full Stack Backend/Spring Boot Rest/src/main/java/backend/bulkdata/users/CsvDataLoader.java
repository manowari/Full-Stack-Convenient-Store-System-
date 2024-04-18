package backend.bulkdata.users;

import backend.products.ProductDto;
import backend.routine.CheckAndCreateUsersTable;
import backend.user.UserDetailsDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
//
//@Component
//public class CsvImportRunner implements CommandLineRunner {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        // Provide the path to the CSV file
//        String csvFilePath = "C:\\Users\\grub\\Documents\\ALEKI\\Torch\\conv store\\Full-Stack-Convenient-Store-System-\\Full Stack Backend\\src\\main\\java\\backend\\bulkdata\\users\\users.csv";
//
//        // Make a POST request to the import/csv/frompath endpoint with the CSV file path
//        String encodedPath = URLEncoder.encode(csvFilePath, StandardCharsets.UTF_8);
//        String url = "http://localhost:8080/data-import/import/csv/frompath?path=" + encodedPath;
//        restTemplate.postForObject(url, null, String.class);
//    }
//}

@Component
public class CsvDataLoader implements CommandLineRunner {

    @Autowired
    private TableManagement tableManagement;
    @Autowired
    private BulkUserService bulkUserService;



    @Override
    public void run(String... args) throws Exception {


        String usersFilePath = "C:\\Users\\grub\\Documents\\ALEKI\\Torch\\conv store\\Full-Stack-Convenient-Store-System-\\Full Stack Backend\\Spring Boot Rest\\src\\main\\java\\backend\\bulkdata\\users\\users.csv";
        String productsFilePath = "C:\\Users\\grub\\Documents\\ALEKI\\Torch\\conv store\\Full-Stack-Convenient-Store-System-\\Full Stack Backend\\Spring Boot Rest\\src\\main\\java\\backend\\bulkdata\\users\\products.csv";


        try (BufferedReader productsReader = new BufferedReader(new FileReader(productsFilePath));
             BufferedReader usersReader = new BufferedReader(new FileReader(usersFilePath))) {

            // Parse products CSV
            List<ProductDto> productsList = parseProductsCsv(productsReader);
            bulkUserService.bulkProductsAdd(productsList);
            System.out.println("Products CSV import successful.");

            // Parse users CSV
            List<UserDetailsDto> userDetailsList = parseCsv(usersReader);
            bulkUserService.bulkSignup(userDetailsList);
            System.out.println("User CSV import successful.");

        } catch (IOException e) {
            System.err.println("Error importing CSV: " + e.getMessage());
        }
    }


        private List<UserDetailsDto> parseCsv(Reader reader) throws IOException, CsvException {
        List<UserDetailsDto> userDetailsList = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(reader)) {
            // Skip the header row if it exists
            csvReader.skip(1); // Skip the first row (header)

            String[] csvRow;
            while ((csvRow = csvReader.readNext()) != null) {
                UserDetailsDto userDetailsDto = new UserDetailsDto();

                // Assuming the CSV columns are in the following order: pf, fullName, workClass, userName, email, userRole, password
                userDetailsDto.setPf(csvRow[0]);
                userDetailsDto.setFullName(csvRow[1]);
                userDetailsDto.setWorkClass(Integer.parseInt(csvRow[2])); // Assuming workClass is already an integer
                userDetailsDto.setUserName(csvRow[3]);
                userDetailsDto.setEmail(csvRow[4]);
                userDetailsDto.setPassword(csvRow[5]);
                userDetailsDto.setUserRole(csvRow[6]); // Ensure that userRole is set correctly
              // Ensure that password is set correctly

                userDetailsList.add(userDetailsDto);
            }
        }

        return userDetailsList;
    }


    private List<ProductDto> parseProductsCsv(BufferedReader reader) throws IOException, CsvException {
        List<ProductDto> productList = new ArrayList<>();
        String line;
        int lineNumber = 0; // Track the line number for error reporting
        while ((line = reader.readLine()) != null) {
            lineNumber++;
            // Split the CSV line and create a ProductDto object
            String[] data = line.split(",");

            // Ensure the data array has the expected number of elements
            if (data.length != 3) {
                System.err.println("Error parsing CSV at line " + lineNumber + ": Unexpected number of columns.");
                continue; // Skip this line and proceed to the next one
            }

            ProductDto productDto = new ProductDto();
            try {
                productDto.setName(normalize(data[0]));
                productDto.setPrice(Double.parseDouble(data[1]));
                productDto.setQuantity(Integer.parseInt(data[2]));
                productList.add(productDto);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing CSV at line " + lineNumber + ": Invalid numeric value.");
                continue; // Skip this line and proceed to the next one
            }
        }
        return productList;
    }





    // Normalize method to lowercase strings
    private String normalize(String input) {
        return input.trim().toLowerCase(); // Assuming you also want to trim leading and trailing whitespace
    }
}

