package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.planRepository.MachinePlanRepository;
import ua.com.novopacksv.production.repository.planRepository.ProductPlanOperationRepository;
import ua.com.novopacksv.production.service.norm.NormService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductPlanOperationServiceImpl implements ProductPlanOperationService {

    private static final LocalTime DAY_START_TIME = LocalTime.of(8, 0, 0);
    private static final LocalTime DAY_END_TIME = LocalTime.of(7, 59, 59);

    private final ProductPlanOperationRepository productPlanOperationRepository;

    private final NormService normService;

    private final MachinePlanRepository machinePlanRepository;

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
        return productPlanOperationRepository.save(setAmountByNorm(productPlanOperation));
    }

    @Override
    public ProductPlanOperation update(ProductPlanOperation productPlanOperation) throws ResourceNotFoundException {
        ProductPlanOperation planOperationOld = findById(productPlanOperation.getId());
        if (!planOperationOld.getProductAmount().equals(productPlanOperation.getProductAmount())) {
            productPlanOperation = setAmountByNorm(productPlanOperation);
        }
        return productPlanOperationRepository.save(productPlanOperation);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        productPlanOperationRepository.delete(findById(id));
    }

    @Override
    public Integer getRollToMachinePlanAmount(ProductType productType, RollType rollType, LocalDate date) {
        LocalDateTime startDay = date.atTime(DAY_START_TIME);
        LocalDateTime endDay = date.plusDays(1).atTime(DAY_END_TIME);
        List<MachinePlan> machinePlans =
                machinePlanRepository.findAllByProductType_IdAndTimeStartBetween(productType.getId(), startDay, endDay);
        return machinePlans
                .stream()
                .mapToInt(plan -> plan
                        .getMachinePlanItems()
                        .stream()
                        .filter(item -> item.getRollType().equals(rollType))
                        .mapToInt(MachinePlanItem::getRollAmount)
                        .sum())
                .sum();
    }

    private Integer getRollQuantity(Long productTypeId, Integer amount) { // todo rename amount
        Norm norm = normService.findOne(productTypeId);
        return (int) Math.floor(amount / norm.getNorm());
    }

    private Integer getProductQuantity(Long productTypeId, Integer rollQuantity) { // todo rename amount
        Norm norm = normService.findOne(productTypeId);
        return norm.getNorm() * rollQuantity;
    }

    private ProductPlanOperation setAmountByNorm(ProductPlanOperation productPlanOperation) {
        Integer rollQuantity = getRollQuantity(productPlanOperation.getProductType().getId(),
                productPlanOperation.getProductAmount());
        productPlanOperation.setRollAmount(rollQuantity);
        productPlanOperation.setProductAmount(getProductQuantity(productPlanOperation.getProductType().getId(),
                rollQuantity));
        return productPlanOperation;
    }
}
