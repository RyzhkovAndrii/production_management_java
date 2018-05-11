package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.repository.productRepository.ProductOperationRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductOperationServiceImpl implements ProductOperationService {

    private final ProductOperationRepository productOperationRepository;
    private final ProductLeftOverService productLeftOverService;

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

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductOperation save(ProductOperation productOperation) throws ResourceNotFoundException {
        changingLeftOver(productOperation, getChangingAmount(productOperation));
        return productOperationRepository.save(productOperation);
    }

    @Override
    public ProductOperation update(ProductOperation productOperation) throws ResourceNotFoundException {
        ProductOperation productOperationOld = findById(productOperation.getId());
        changingLeftOver(productOperation, productOperation.getAmount() - productOperationOld.getAmount());
        return productOperationRepository.save(productOperation);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        ProductOperation productOperation = findById(id);
        changingLeftOver(productOperation, -getChangingAmount(productOperation));
        productOperationRepository.delete(productOperation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductOperation> findAllOperationBetweenDatesByTypeId(Long productTypeId, LocalDate fromDate,
                                                                       LocalDate toDate) {
        return productOperationRepository.findAllByProductType_IdAndDateBetween(productTypeId, fromDate, toDate);
    }

    private Integer getChangingAmount(ProductOperation productOperation) {
        return productLeftOverService.isSoldOperation(productOperation) ?
                -productOperation.getAmount() : productOperation.getAmount();
    }

    private void changingLeftOver(ProductOperation productOperation, Integer changingAmount)
            throws ResourceNotFoundException {
        ProductLeftOver productLeftOver =
                productLeftOverService.findByProductTypeId(productOperation.getProductType().getId());
        Integer leftOverAmount = productLeftOver.getAmount();
        productLeftOver.setAmount(leftOverAmount + changingAmount);
        productLeftOverService.update(productLeftOver);
    }
}
