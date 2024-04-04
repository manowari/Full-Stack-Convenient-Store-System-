package backend.products;
import backend.authorities.AuthoritiesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final AuthoritiesService authoritiesService;

    public ProductService(ProductRepository productRepository, AuthoritiesService authoritiesService) {
        this.productRepository = productRepository;
        this.authoritiesService = authoritiesService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @PreAuthorize("hasAuthority('WORK_CLASS_3')")
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @PreAuthorize("hasAuthority('WORK_CLASS_3')")
    public Product updateProduct(Long productId, Product product) {
        product.setId(productId);
        return productRepository.save(product);
    }

    @PreAuthorize("hasAuthority('WORK_CLASS_3')")
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
