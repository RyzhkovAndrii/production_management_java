package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.repository.productRepository.ProductOperationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOperationServiceImpl implements ProductOperationService {

    private final ProductOperationRepository productOperationRepository;

    @Override
    @Transactional(readOnly = true)
    public ProductOperation findById(Long id) throws ResourceNotFoundException {
        return productOperationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Product operation with id = %d was not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductOperation> findAll() {
        return productOperationRepository.findAll();
    }

    @Override //TODO add changing to left over
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductOperation save(ProductOperation productOperation) {
        return productOperationRepository.save(productOperation);
    }

    @Override //TODO add changing to left over
    public ProductOperation update(ProductOperation productOperation) {
        return productOperationRepository.save(productOperation);
    }

    @Override //TODO add changing to left over
    public void delete(Long id) throws ResourceNotFoundException {
        productOperationRepository.delete(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductOperation> findAllOperationBetweenDatesByTypeId(Long productTypeId, LocalDate fromDate,
                                                                       LocalDate toDate){
        return productOperationRepository.findAllByProductType_IdAndDateBetween(productTypeId, fromDate, toDate);
    }
}