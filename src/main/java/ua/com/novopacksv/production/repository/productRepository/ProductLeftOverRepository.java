package ua.com.novopacksv.production.repository.productRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;

import java.util.Optional;

@Repository
public interface ProductLeftOverRepository extends JpaRepository <ProductLeftOver, Long> {

    Optional<ProductLeftOver> findByProductType_Id(Long id);
}
