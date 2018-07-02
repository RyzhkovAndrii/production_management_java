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
    public ProductLeftOver getOneWithoutPlan(Long productTypeId, LocalDate fromDate, LocalDate toDate) {
        ProductLeftOver tempLeftover = createTempLeftover(productTypeId, fromDate, toDate);
        return tempLeftover;
    }

    @Override
    public List<ProductLeftOver> getAllWithoutPlan(LocalDate fromDate, LocalDate toDate) {
        List<ProductType> productTypes = productTypeService.findAll();
        return productTypes.stream()
                .map(productType -> getOneWithoutPlan(productType.getId(), fromDate, toDate))
                .collect(Collectors.toList());
    }

    @Override
    public ProductLeftOver getOneTotal(Long productTypeId, LocalDate fromDate, LocalDate toDate) {
        ProductLeftOver tempLeftOver = createTempLeftover(productTypeId, fromDate, toDate);
        Integer amount = tempLeftOver.getAmount() + countAmountOfPlan(productTypeId, fromDate, toDate);
        tempLeftOver.setAmount(amount);
        return tempLeftOver;
    }

    @Override
    public List<ProductLeftOver> getAllTotal(LocalDate fromDate, LocalDate toDate) {
        List<ProductType> productTypes = productTypeService.findAll();
        return productTypes.stream()
                .map(productType -> getOneTotal(productType.getId(), fromDate, toDate))
                .collect(Collectors.toList());
    }

    private Integer countAmountWithoutPlan(Long productTypeId, LocalDate fromDate, LocalDate toDate) {
        List<OrderItem> orderItems;
        if (toDate.isAfter(LocalDate.now())) {
            orderItems = orderItemService.findAll(productTypeService.findById(productTypeId), fromDate, toDate);
        } else {
            orderItems = orderItemService.findAllNotDelivered(productTypeService.findById(productTypeId), toDate);
        }
        return orderItems.stream().mapToInt(OrderItem::getAmount).sum();
    }

    private Integer countAmountOfPlan(Long productTypeId, LocalDate fromDate, LocalDate toDate) {
        List<ProductPlanOperation> productPlanOperations =
                productPlanOperationService.getAll(productTypeId, fromDate, toDate);
        return productPlanOperations.stream().mapToInt(ProductPlanOperation::getProductAmount).sum();
    }

    private ProductLeftOver createTempLeftover(Long productTypeId, LocalDate fromDate, LocalDate toDate) {
        ProductLeftOver productLeftOver = productLeftOverService.findByProductTypeId(productTypeId);
        ProductLeftOver tempLeftover = new ProductLeftOver();
        tempLeftover.setProductType(productLeftOver.getProductType());
        tempLeftover.setLeftDate(toDate);
        tempLeftover.setAmount(productLeftOver.getAmount() - countAmountWithoutPlan(productTypeId, fromDate, toDate));
        return tempLeftover;
    }
}
