package ua.com.novopacksv.production.repository.productRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.productModel.ProductOperation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductOperationRepository extends JpaRepository<ProductOperation, Long> {

    List<ProductOperation> findAllByProductType_IdAndDateBetween(Long productTypeId, LocalDate fromDate, LocalDate toDate);
}
