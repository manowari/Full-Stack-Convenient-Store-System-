package backend.bulkdata.users;
import backend.user.User;
import backend.user.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CsvImportController {

    @Autowired
    private BulkUserService bulkUserService;

    @PostMapping("data-import/import/csv")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        try {
            // Read the CSV file from the uploaded file
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            List<UserDetailsDto> userDetailsList = parseCsv(reader);
            List<User> users = bulkUserService.bulkSignup(userDetailsList);

            return ResponseEntity.ok("CSV import successful. " + users.size() + " users created.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error importing CSV");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error parsing workclass to integer");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Unknown error occurred during CSV import");
        }
    }

    @PostMapping("data-import/import/csv/fromdir")
    public ResponseEntity<String> importCsvFromFile() {
        try {
            // Read the CSV file from the project directory
            InputStream inputStream = new ClassPathResource("users.csv").getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<UserDetailsDto> userDetailsList = parseCsv(reader);
            List<User> users = bulkUserService.bulkSignup(userDetailsList);

            return ResponseEntity.ok("CSV import successful. " + users.size() + " users created.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error importing CSV from file");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Unknown error occurred during CSV import");
        }
    }

    private List<UserDetailsDto> parseCsv(BufferedReader reader) throws IOException {
        List<UserDetailsDto> userDetailsList = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            // Split the CSV line and create a UserDetailsDto object
            String[] data = line.split(",");
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setPf(normalize(data[0]));
            userDetailsDto.setFullName(normalize(data[1]));
            userDetailsDto.setWorkClass(Integer.parseInt(data[2])); // Assuming workclass is already an integer
            userDetailsDto.setUserName(normalize(data[3]));
            userDetailsDto.setEmail(normalize(data[4]));
            userDetailsDto.setUserRole(normalize(data[5]));
            userDetailsDto.setPassword(normalize(data[6]));
            userDetailsList.add(userDetailsDto);
        }
        return userDetailsList;
    }



    @PostMapping("data-import/import/csv/frompath")
    public ResponseEntity<String> importCsvFromPath(@RequestParam("path") String filePath) {
        try {
            // Read the CSV file from the provided path
            InputStream inputStream = new FileInputStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<UserDetailsDto> userDetailsList = parseCsv(reader);
            List<User> users = bulkUserService.bulkSignup(userDetailsList);

            return ResponseEntity.ok("CSV import successful. " + users.size() + " users created.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error importing CSV from provided path");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Unknown error occurred during CSV import");
        }
    }



    // Normalize method to lowercase strings
    private String normalize(String input) {
        return input.toLowerCase().trim(); // Assuming you also want to trim leading and trailing whitespace
    }







//    @PostMapping("/import/csv")
//    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return ResponseEntity.badRequest().body("Please upload a CSV file");
//        }
//
//        try {
//            // Convert MultipartFile to byte array
//            byte[] fileBytes = file.getBytes();
//
//            // Convert byte array to Base64 encoded string
//            String fileBase64 = Base64.getEncoder().encodeToString(fileBytes);
//
//            // Pass the Base64 encoded string to the service
//            csvReaderService.importUsersFromCsv(fileBase64);
//
//            return ResponseEntity.ok("CSV import successful");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body("Error importing CSV");
//        }
//    }
//
//



}

