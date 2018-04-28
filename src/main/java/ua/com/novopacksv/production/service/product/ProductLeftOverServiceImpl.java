package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.repository.productRepository.ProductLeftOverRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductLeftOverServiceImpl implements ProductLeftOverService {

    private final ProductLeftOverRepository productLeftOverRepository;

    private final ProductOperationService productOperationService;

    @Override
    public List<ProductLeftOver> findOnDate(LocalDate date) {
        List<ProductLeftOver> productLeftOvers = productLeftOverRepository.findAll();
        return productLeftOvers.stream()
                .map((productLeftOver) -> getLeftOverOnDate(date, productLeftOver)).collect(Collectors.toList());
    }

    @Override
    public ProductLeftOver findByProductType_IdOnDate(Long productTypeId, LocalDate date)
            throws ResourceNotFoundException {
        ProductLeftOver productLeftOver =
                productLeftOverRepository.findByProductType_Id(productTypeId).orElseThrow(() -> {
                    String message = String.format("Produt type with Id = %d was not found", productTypeId);
                    return new ResourceNotFoundException(message);
                });
        return getLeftOverOnDate(date, productLeftOver);
    }

    @Override
    public ProductLeftOver findById(Long id) {
        return null;
    }

    @Override
    public List<ProductLeftOver> findAll() {
        return null;
    }

    @Override
    public ProductLeftOver save(ProductLeftOver productLeftOver) {
        return null;
    }

    @Override
    public ProductLeftOver update(ProductLeftOver productLeftOver) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    private ProductLeftOver getLeftOverOnDate(LocalDate date, ProductLeftOver productLeftOver) {
        ProductLeftOver productLeftOverTemp = new ProductLeftOver();
        productLeftOverTemp.setLeftDate(date);
        productLeftOverTemp.setProductType(productLeftOver.getProductType());
        productLeftOverTemp.setAmount(amountOfLeftOverOnDate(date, productLeftOver));
        return productLeftOverTemp;
    }

    private Boolean isSoldOperation(ProductOperation productOperation) {
        return productOperation.getProductOperationType().equals(ProductOperationType.SOLD);
    }

    private Integer amountOfLeftOverOnDate(LocalDate date, ProductLeftOver productLeftOver) {
        List<ProductOperation> operationsBetweenDates =
                productOperationService.findAllOperationBetweenDatesByTypeId(productLeftOver.getProductType().getId(),
                        date, productLeftOver.getLeftDate());
        Integer amount = productLeftOver.getAmount();
        for (ProductOperation productOperation : operationsBetweenDates) {
            amount = isSoldOperation(productOperation) ?
                    amount - productOperation.getAmount() : amount + productOperation.getAmount();
        }
        return amount;
    }
}
