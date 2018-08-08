package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.repository.planRepository.ProductPlanOperationRepository;
import ua.com.novopacksv.production.service.norm.NormService;
import ua.com.novopacksv.production.service.product.ProductTypeService;
import ua.com.novopacksv.production.service.roll.RollTypeService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductPlanOperationServiceImpl implements ProductPlanOperationService {

    private final ProductPlanOperationRepository productPlanOperationRepository;
    
    @Override
    public List<ProductPlanOperation> getAll(Long productTypeId, LocalDate fromDate, LocalDate toDate) {
        return productPlanOperationRepository.findByProductType_IdAndDateBetween(productTypeId, fromDate, toDate);
    }

    @Override
    public List<ProductPlanOperation> getAll(LocalDate fromDate, LocalDate toDate) {
        return productPlanOperationRepository.findAllByDateBetween(fromDate, toDate);
    }

    @Override
    public List<ProductPlanOperation> getAllByRollTypeId(Long rollTypeId, LocalDate fromDate, LocalDate toDate) {
        return productPlanOperationRepository.findAllByRollType_IdAndDateBetween(rollTypeId, fromDate, toDate);
    }

    @Override
    public ProductPlanOperation findById(Long id) throws ResourceNotFoundException {
        ProductPlanOperation productPlanOperation = productPlanOperationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("The product plan operation with id = %d was not found", id);
            log.error("Method findById(Long id): The product plan operation with id =() does not exist", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): The product plan operation with id =() was found: ()", id,
                productPlanOperation);
        return productPlanOperation;
    }

    @Override
    public List<ProductPlanOperation> findAll() {
        return productPlanOperationRepository.findAll();
    }

    @Override
    public ProductPlanOperation save(ProductPlanOperation productPlanOperation) {
        return productPlanOperationRepository.save(productPlanOperation);
    }

    @Override
    public ProductPlanOperation update(ProductPlanOperation productPlanOperation) throws ResourceNotFoundException {
        return productPlanOperationRepository.save(productPlanOperation);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        productPlanOperationRepository.delete(findById(id));
    }

}
