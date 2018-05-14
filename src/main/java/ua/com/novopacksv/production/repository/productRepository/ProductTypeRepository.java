package ua.com.novopacksv.production.repository.productRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.productModel.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    ProductType findByNameAndWeightAndColorCode(String name, Double weight, String colorCode);
}
