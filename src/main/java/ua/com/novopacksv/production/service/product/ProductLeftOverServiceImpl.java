package ua.com.novopacksv.production.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NotUniqueFieldException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.model.productModel.ProductOperation;
import ua.com.novopacksv.production.model.productModel.ProductOperationType;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.repository.productRepository.ProductLeftOverRepository;
import ua.com.novopacksv.production.service.order.OrderItemService;
import ua.com.novopacksv.production.service.order.OrderService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductLeftOverServiceImpl implements ProductLeftOverService {

    private final ProductLeftOverRepository productLeftOverRepository;

    @Autowired
    @Lazy
    private ProductOperationService productOperationService;

    @Autowired
    @Lazy
    private OrderItemService orderItemService;

    @Autowired
    @Lazy
    private OrderService orderService;

    @Override
    public List<ProductLeftOver> findOnDate(LocalDate date) {
        List<ProductLeftOver> productLeftOvers = productLeftOverRepository.findAll();
        return productLeftOvers.stream()
                .map((productLeftOver) -> getLeftOverOnDate(date, productLeftOver)).collect(Collectors.toList());
    }

    @Override
    public List<ProductLeftOver> findLatest() {
        LocalDate latestDeliveryDate = orderService.findMaxDeliveryDate();
        return findOnDate(latestDeliveryDate);
    }

    @Override
    public ProductLeftOver findByProductType_IdOnDate(Long productTypeId, LocalDate date)
            throws ResourceNotFoundException {
        ProductLeftOver productLeftOver =
                productLeftOverRepository.findByProductType_Id(productTypeId).orElseThrow(() -> {
                    String message = String.format("Product type with Id = %d was not found", productTypeId);
                    return new ResourceNotFoundException(message);
                });
        return getLeftOverOnDate(date, productLeftOver);
    }

    @Override
    public ProductLeftOver findById(Long id) throws ResourceNotFoundException {
        return productLeftOverRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Product leftover with id = %d was not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    public ProductLeftOver findByProductTypeId(Long productTypeId) throws ResourceNotFoundException {
        return productLeftOverRepository.findByProductType_Id(productTypeId).orElseThrow(() -> {
            String message = String.format("Product type with id = %d was not found", productTypeId);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    public List<ProductLeftOver> findAll() {
        return productLeftOverRepository.findAll();
    }

    @Override
    public ProductLeftOver save(ProductLeftOver productLeftOver) {
        return productLeftOverRepository.save(productLeftOver);
    }

    @Override
    public ProductLeftOver update(ProductLeftOver productLeftOver) {
        return productLeftOverRepository.save(productLeftOver);
    }

    @Override
    public void delete(Long id) {
        productLeftOverRepository.deleteById(id);
    }

    private ProductLeftOver getLeftOverOnDate(LocalDate date, ProductLeftOver productLeftOver) {
        ProductLeftOver productLeftOverTemp = new ProductLeftOver();
        productLeftOverTemp.setLeftDate(date);
        productLeftOverTemp.setProductType(productLeftOver.getProductType());
        productLeftOverTemp.setAmount(amountOfLeftOverOnDate(date, productLeftOver));
        return productLeftOverTemp;
    }

    @Override
    public ProductLeftOver saveByProductType(ProductType productType) {
            return createNewLeftOver(productType);
    }

    public Boolean isSoldOperation(ProductOperation productOperation) {
        return productOperation.getProductOperationType().equals(ProductOperationType.SOLD);
    }

    private Integer amountOfLeftOverOnDate(LocalDate date, ProductLeftOver productLeftOver) {
        if (date.isBefore(LocalDate.now()) || date.isEqual(LocalDate.now())) {
            return countAmountOfLeftOverForPast(date, productLeftOver);
        } else {
            return countAmountOfLeftOverForFuture(date, productLeftOver);
        }
    }

    private Integer countAmountOfLeftOverForPast(LocalDate date, ProductLeftOver productLeftOver) {
        List<ProductOperation> operationsBetweenDates =
                productOperationService.findAllOperationBetweenDatesByTypeId(productLeftOver.getProductType().getId(),
                        date.plusDays(1), LocalDate.now());
        Integer amount = productLeftOver.getAmount();
        for (ProductOperation productOperation : operationsBetweenDates) {
            amount += isSoldOperation(productOperation) ?
                    productOperation.getAmount() : - productOperation.getAmount();
        }
        return amount;
    }

    private Integer countAmountOfLeftOverForFuture(LocalDate date, ProductLeftOver productLeftOver) {
        ProductType productType = productLeftOver.getProductType();
        List<OrderItem> orderItems = findOrderNotDelivered(productType, LocalDate.now(), date);
        Integer amount = productLeftOver.getAmount();
        for (OrderItem orderItem : orderItems) {
            amount -= orderItem.getAmount();
        }
        return amount;
    }

    //TODO review
    private List<OrderItem> findOrderNotDelivered(ProductType productType, LocalDate fromDate, LocalDate toDate) {
        List<OrderItem> orderItems = orderItemService.findAll(productType, fromDate, toDate);
        return orderItems.stream()
                .filter((orderItem) -> orderItem.getOrder().getIsDelivered().equals(false)).collect(Collectors.toList());
    }

    private Boolean ifLeftOverExist(ProductType productType) {
        return productLeftOverRepository.findByProductType_Id(productType.getId()) != null;
    }

    private ProductLeftOver createNewLeftOver(ProductType productType) {
        ProductLeftOver productLeftOver = new ProductLeftOver();
        productLeftOver.setProductType(productType);
        productLeftOver.setAmount(0);
        productLeftOver.setLeftDate(LocalDate.now());
        return productLeftOverRepository.save(productLeftOver);
    }
}
