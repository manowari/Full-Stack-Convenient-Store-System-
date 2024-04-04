package backend.sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM Sale s JOIN FETCH s.items WHERE s.id = :saleId")
    Optional<Sale> findByIdWithItems(@Param("saleId") Long saleId);
}

