package backend.sales;

import backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import backend.user.User;
import backend.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("test/sales")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> postSale(@RequestBody Sale sale, @RequestParam Long employeeId) {
        // Add validation and error handling

        Sale postedSale = saleService.postSale(sale, sale.getItems(), employeeId);
        return ResponseEntity.ok(postedSale);
    }

    @PostMapping("/undo/{saleId}")
    public ResponseEntity<?> undoSale(@PathVariable Long saleId, @RequestParam Long employeeId) {
        // Add validation and error handling

        saleService.undoSale(saleId, employeeId);
        return ResponseEntity.ok("Sale undone successfully.");
    }
}

