package backend.sales;

import backend.products.Product;
import backend.products.ProductService;
import backend.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductService productService;

    public SaleService(SaleRepository saleRepository, ProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }

    public Sale postSale(Sale sale, List<SaleItem> saleItems, Long employeeId) {
        // Check if employee is authorized to post sale (work class 1 or 2)
        // Your authentication logic here

        List<String> insufficientProducts = new ArrayList<>();

        // Check availability of products
        for (SaleItem item : saleItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            Product existingProduct = productService.getProductByName(product.getName());
            if (existingProduct != null && existingProduct.getQuantity() < quantity) {
                insufficientProducts.add(product.getName());
            }
        }

        // If any product is insufficient, notify the user and return null
        if (!insufficientProducts.isEmpty()) {
            throw new RuntimeException("Insufficient quantity for products: " + String.join(", ", insufficientProducts));
        }

        // Deduct items from product table
        for (SaleItem item : saleItems) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            Product existingProduct = productService.getProductByName(product.getName());
            existingProduct.setQuantity(existingProduct.getQuantity() - quantity);
            productService.updateProduct(existingProduct.getId(), existingProduct);
        }

        // Save the sale
        sale.setDate(new Date());
        sale.setEmployeeId(employeeId);
        return saleRepository.save(sale);
    }

    // Other methods remain unchanged


    public void undoSale(Long saleId, Long employeeId) {
        // Check if employee is authorized to undo sale (work class 3 or manager)
        // Your authentication logic here

        Optional<Sale> optionalSale = saleRepository.findById(saleId);
        if (optionalSale.isPresent()) {
            Sale sale = optionalSale.get();
            // Add logic to undo the sale (e.g., add back items to product table)

            // Update modifiedBy field
            sale.setModifiedBy(employeeId);

            saleRepository.save(sale);
        } else {
            // Handle sale not found
        }
    }
}
