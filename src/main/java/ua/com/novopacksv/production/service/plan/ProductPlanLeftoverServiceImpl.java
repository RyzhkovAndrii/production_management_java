package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.orderModel.OrderItem;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.productModel.ProductLeftOver;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.service.order.OrderItemService;
import ua.com.novopacksv.production.service.product.ProductLeftOverService;
import ua.com.novopacksv.production.service.product.ProductTypeService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductPlanLeftoverServiceImpl implements ProductPlanLeftoverService {

    @Autowired
    @Lazy
    private ProductLeftOverService productLeftOverService;

    private final ProductPlanOperationService productPlanOperationService;

    @Autowired
    @Lazy
    private OrderItemService orderItemService;

    @Autowired
    @Lazy
    private ProductTypeService productTypeService;

    @Override
    public ProductLeftOver getOneWithoutPlan(Long productTypeId, LocalDate date) {
        ProductLeftOver tempLeftover = createTempLeftover(productTypeId, date);
        return tempLeftover;
    }

    @Override
    public List<ProductLeftOver> getAllWithoutPlan(LocalDate date) {
        List<ProductType> productTypes = productTypeService.findAll();
        return productTypes.stream()
                .map(productType -> getOneWithoutPlan(productType.getId(), date))
                .collect(Collectors.toList());
    }

    @Override
    public ProductLeftOver getOneTotal(Long productTypeId, LocalDate date) {
        ProductLeftOver tempLeftOver = createTempLeftover(productTypeId, date);
        Integer amount = tempLeftOver.getAmount() + countAmountOfPlan(productTypeId, date);
        tempLeftOver.setAmount(amount);
        return tempLeftOver;
    }

    @Override
    public List<ProductLeftOver> getAllTotal(LocalDate date) {
        List<ProductType> productTypes = productTypeService.findAll();
        return productTypes.stream()
                .map(productType -> getOneTotal(productType.getId(), date))
                .collect(Collectors.toList());
    }

    private Integer countAmountWithoutPlan(Long productTypeId, LocalDate date) {
        List<OrderItem> orderItems;
            orderItems = orderItemService.findAllNotDelivered(productTypeService.findById(productTypeId), date);
        return orderItems.stream().mapToInt(OrderItem::getAmount).sum();
    }

    private Integer countAmountOfPlan(Long productTypeId, LocalDate toDate) {
        List<ProductPlanOperation> productPlanOperations =
                productPlanOperationService.getAll(productTypeId, LocalDate.now(), toDate);
        return productPlanOperations.stream().mapToInt(ProductPlanOperation::getProductAmount).sum();
    }

    private ProductLeftOver createTempLeftover(Long productTypeId, LocalDate date) {
        ProductLeftOver productLeftOver = productLeftOverService.findByProductTypeId(productTypeId);
        ProductLeftOver tempLeftover = new ProductLeftOver();
        tempLeftover.setProductType(productLeftOver.getProductType());
        tempLeftover.setLeftDate(date);
        tempLeftover.setAmount(productLeftOver.getAmount() - countAmountWithoutPlan(productTypeId, date));
        return tempLeftover;
    }
}
