package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductPlanOperationRepository extends JpaRepository<ProductPlanOperation, Long> {

    List <ProductPlanOperation> findByProductType_IdAndDateBetween(Long productTypeId,LocalDate fromDate, LocalDate date);

}
