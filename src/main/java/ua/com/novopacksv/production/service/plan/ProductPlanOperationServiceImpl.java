package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.model.planModel.ProductPlanOperation;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.planRepository.MachinePlanRepository;
import ua.com.novopacksv.production.repository.planRepository.ProductPlanOperationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Class implements interface {@link ProductPlanOperationService} and creates methods for work with
 * ProductPlanOperations
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductPlanOperationServiceImpl implements ProductPlanOperationService {

    /**
     * The value of LocalTime that determines a beginning of the first working shift as a beginning of day
     */
    private static final LocalTime DAY_START_TIME = LocalTime.of(8, 0, 0);

    /**
     * The value of LocalTime that determines an end of the last working shift as an end of day
     */
    private static final LocalTime DAY_END_TIME = LocalTime.of(7, 59, 59);

    /**
     * An object of repository's level for work with ProductPlanOperations from db
     */
    private final ProductPlanOperationRepository productPlanOperationRepository;

    /**
     * An object of repository's level for work with MachinePlans from db
     */
    private final MachinePlanRepository machinePlanRepository;

    /**
     * The method gets all ProductPlanOperations by the next parameters:
     *
     * @param productTypeId - ProductType's Id
     * @param fromDate      - a beginning of the interval
     * @param toDate        - an end of the interval
     * @return List of ProductPlanOperations
     */
    @Override
    public List<ProductPlanOperation> getAll(Long productTypeId, LocalDate fromDate, LocalDate toDate) {
        log.debug("Method getAll(Long productTypeId, LocalDate fromDate, LocalDate toDate): List<ProductPlanOperation> " +
                "for ProductType with id = {} from {} to {} is finding", productTypeId, fromDate, toDate);
        return productPlanOperationRepository.findByProductType_IdAndDateBetween(productTypeId, fromDate, toDate);
    }

    /**
     * Method gets all ProductPlanOperations for the interval
     *
     * @param fromDate - a beginning of the interval
     * @param toDate   - an end of the interval
     * @return List of ProductPlanOperations
     */
    @Override
    public List<ProductPlanOperation> getAll(LocalDate fromDate, LocalDate toDate) {
        log.debug("Method getAll(LocalDate fromDate, LocalDate toDate): List<ProductPlanOperation> for a period from {}" +
                "to {} is finding", fromDate, toDate);
        return productPlanOperationRepository.findAllByDateBetween(fromDate, toDate);
    }

    /**
     * Method gets all ProductPlanOperations by RollType's id for a period
     *
     * @param rollTypeId - RollType's Id
     * @param fromDate   - a beginning of a period
     * @param toDate     - an end of a period
     * @return List of ProductPlanOperations
     */
    @Override
    public List<ProductPlanOperation> getAllByRollTypeId(Long rollTypeId, LocalDate fromDate, LocalDate toDate) {
        log.debug("Method getAllByRollTypeId(Long rollTypeId, LocalDate fromDate, LocalDate toDate): " +
                        "List<ProductPlanOperation> for RollType's id = {} for a period from {} to {} is finding",
                rollTypeId, fromDate, toDate);
        return productPlanOperationRepository.findAllByRollType_IdAndDateBetween(rollTypeId, fromDate, toDate);
    }

    /**
     * Method finds one ProductPlanOperation by it's id
     *
     * @param id - ProductPlanOperation's id
     * @return ProductPlanOperation with pointed id from db
     * @throws ResourceNotFoundException if ProductPlanOperation with pointed id does not exist
     */
    @Override
    public ProductPlanOperation findById(Long id) throws ResourceNotFoundException {
        ProductPlanOperation productPlanOperation = productPlanOperationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("The product plan operation with id = %d was not found", id);
            log.error("Method findById(Long id): The product plan operation with id ={} does not exist", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): The product plan operation with id ={} was found: {}", id,
                productPlanOperation);
        return productPlanOperation;
    }

    /**
     * Method finds all existed ProductPlanOperations from db
     *
     * @return List of ProductPlanOperations
     */
    @Override
    public List<ProductPlanOperation> findAll() {
        log.debug("Method findAll(): List<ProductPlanOperation> is finding");
        return productPlanOperationRepository.findAll();
    }

    /**
     * Method saves ProductPlanOperation
     *
     * @param productPlanOperation - new ProductPlanOperation for save
     * @return saved ProductPlanOperation from db
     */
    @Override
    public ProductPlanOperation save(ProductPlanOperation productPlanOperation) {
        ProductPlanOperation productPlanOperationSaved = productPlanOperationRepository.save(productPlanOperation);
        log.debug("Method save(ProductPlanOperation productPlanOperation): ProductPlanOperation was saved: {}",
                productPlanOperationSaved);
        return productPlanOperationSaved;
    }

    /**
     * Method updates existed ProductPlanOperation in db
     *
     * @param productPlanOperation - updated ProductPlanOperation
     * @return saved ProductPlanOperation
     * @throws ResourceNotFoundException if ProductPlanOperation does not exist in db
     */
    @Override
    public ProductPlanOperation update(ProductPlanOperation productPlanOperation) throws ResourceNotFoundException {
        ProductPlanOperation productPlanOperationSaved =
                productPlanOperationRepository.save(findById(productPlanOperation.getId()));
        log.debug("Method update(ProductPlanOperation productPlanOperation): ProductPlanOperation {} was updated");
        return productPlanOperationSaved;
    }

    /**
     * Method deletes ProductPlanOperation by it's id
     *
     * @param id - ProductPlanOperation's id
     * @throws ResourceNotFoundException if ProductPlanOperation with pointed id does not exist
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        productPlanOperationRepository.delete(findById(id));
        log.debug("Method delete(Long id): ProductPlanOperation with id = {} was deleted", id);
    }

    /**
     * Method counts a sum of rollAmounts from MachinePlans for pointed parameters:
     *
     * @param productType - ProductType
     * @param rollType    - RollType
     * @param date        - date on which a sum is finding, here we consider that day is from 8 a.m. to 8 a.m. on the next day
     * @return integer, sum of rollAmounts
     */
    @Override
    public Integer getRollToMachinePlanAmount(ProductType productType, RollType rollType, LocalDate date) {
        LocalDateTime startDay = date.atTime(DAY_START_TIME);
        LocalDateTime endDay = date.plusDays(1).atTime(DAY_END_TIME);
        List<MachinePlan> machinePlans =
                machinePlanRepository.findAllByProductType_IdAndTimeStartBetween(productType.getId(), startDay, endDay);
        Integer sum = machinePlans
                .stream()
                .mapToInt(plan -> plan
                        .getMachinePlanItems()
                        .stream()
                        .filter(item -> item.getRollType().equals(rollType))
                        .mapToInt(MachinePlanItem::getRollAmount)
                        .sum())
                .sum();
        log.debug("Method getRollToMachinePlanAmount(ProductType productType, RollType rollType, LocalDate date):" +
                        "Sum from MachinePlans for ProductType = {}, for RollType = {} on date = {} is {}", productType,
                rollType, date, sum);
        return sum;
    }

}
