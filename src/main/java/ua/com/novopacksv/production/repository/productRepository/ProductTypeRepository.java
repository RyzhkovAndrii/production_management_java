package ua.com.novopacksv.production.repository.productRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.productModel.ProductType;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    @Query(value = "select distinct pt.* " +
            "from PRODUCT_TYPE pt " +
            "join NORM n " +
            "on pt.ID=n.ID " +
            "join NORM_ROLL_TYPE nrt " +
            "on nrt.NORM_ID=pt.ID and nrt.ROLL_TYPE_ID=?1", nativeQuery = true)
    List<ProductType> getByRollTypeIdInNorms(Long rollTypeId);

    ProductType findByNameAndWeightAndColorCode(String name, Double weight, String colorCode);

    List<ProductType> findAllByName(String name);
}
