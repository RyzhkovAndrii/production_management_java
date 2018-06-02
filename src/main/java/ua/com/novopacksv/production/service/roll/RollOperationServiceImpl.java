package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NegativeAmountException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.*;
import ua.com.novopacksv.production.repository.rollRepository.RollOperationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * This class implements {@link RollOperationService}
 * includes operations with rolls
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RollOperationServiceImpl implements RollOperationService {

    /**
     * Object of repository's level for work with DB with roll operations (DAO layer)
     */
    private final RollOperationRepository rollOperationRepository;

    /**
     * Object of service layer, it gives access to roll's leftover
     */
    @Autowired
    @Lazy
    private RollLeftOverServiceImpl rollLeftOverService; // todo circular dependencies remove

    /**
     * Object of service layer, it gives access  to manufactured rolls
     */
    @Autowired
    @Lazy
    private RollManufacturedServiceImpl rollManufacturedService; // todo circular dependencies remove

    /**
     * The method returns all operations by RollType for period - for tables of rollType`s information
     *
     * @param rollType - type of roll
     * @param fromDate - date of period's beginning
     * @param toDate   - date of period's end
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeAndManufacturedDateBetween(RollType rollType, LocalDate fromDate,
                                                                           LocalDate toDate) {
        log.debug("Method findAllByRollTypeAndManufacturedDateBetween(RollType rollType, LocalDate fromDate, " +
                "LocalDate toDate): Roll operations of roll type {} for period are finding", rollType);
        return rollOperationRepository
                .findAllByRollManufactured_RollTypeAndRollManufactured_ManufacturedDateBetween(rollType, fromDate,
                        toDate);
    }

    /**
     * The method finds all operations under rolls of one type that were manufactured in period
     *
     * @param rollTypeId - id of roll type
     * @param from       - date of period's beginning
     * @param to         - date of period's end
     * @return list of operations
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeIdAndManufacturedPeriod(Long rollTypeId, LocalDate from, LocalDate to) {
        List<RollManufactured> rollManufacturedList = rollManufacturedService.findAll(from, to, rollTypeId);
        log.debug("Method findAllByRollTypeIDAndManufacturedPeriod(Long rollTypeId, LocalDate from, LocalDate to): " +
                        "Operations with roll's type id = {} for manufactured period from {} to {} are finding",
                rollTypeId, from, to);
        return rollOperationRepository.findAllByRollManufacturedIsIn(rollManufacturedList);
    }

    /**
     * The method finds roll operations by rollTypeId for period
     *
     * @param id   - roll type id
     * @param from - date of period's beginning
     * @param to   - date of period's end
     * @return list of operations
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeIdAndOperationPeriod(Long id, LocalDate from, LocalDate to) {
        log.debug("Method findAllByRollTypeIdAndOperationPeriod(Long id, LocalDate from, LocalDate to): " +
                "Operations with roll type id = {} for period are finding", id);
        return rollOperationRepository.findAllByRollManufactured_RollType_IdAndOperationDateBetween(id, from, to);
    }

    /**
     * The method finds all operations of one roll type by id
     *
     * @param id - roll type id
     * @return operations
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeId(Long id) {
        List<RollManufactured> rollManufacturedList = rollManufacturedService.findAll(id);
        log.debug("Method findAllByRollTypeID(Long id): Roll operations with roll type id = {} are finding", id);
        return rollOperationRepository.findAllByRollManufacturedIsIn(rollManufacturedList);
    }

    /**
     * The method find one operation by it's id
     *
     * @param id - roll operation's id
     * @return roll operation with pointed id
     * @throws ResourceNotFoundException - if there is not operation with this id in DB
     */
    @Override
    @Transactional(readOnly = true)
    public RollOperation findById(Long id) throws ResourceNotFoundException {
        RollOperation rollOperation = rollOperationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Roll operation with id = %d is not found", id);
            log.error("Method findById(Long id): Roll operation with id {} was not found", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): Roll operation {} with id {} is finding", rollOperation, id);
        return rollOperation;
    }

    /**
     * The method finds all operations
     *
     * @return all operations that are exist
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAll() {
        log.debug("Method findAll(): all operations are finding");
        return rollOperationRepository.findAll();
    }

    /**
     * The method saves the new roll operation
     * Before save method recounts and rewrites rollLeftOver
     *
     * @param rollOperation - roll operation for save
     * @return this roll operation
     * @throws NegativeAmountException - if this operation changes roll's leftover to negative value
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RollOperation save(RollOperation rollOperation) throws NegativeAmountException {
        checkOperationSaveAllowed(rollOperation);
        RollManufactured rollManufactured = rollOperation.getRollManufactured();
        Integer changingAmount = getOperationAmountWithSign(rollOperation);
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollManufactured.getRollType());
        rollLeftOverService.changeRollLeftOverAmount(rollLeftOver, changingAmount);
        log.debug("Method save(RollOperation rollOperation): Roll operation {} is saving", rollOperation);
        return rollOperationRepository.save(rollOperation);
    }

    /**
     * The method updates roll operation if this operation is allowed
     * Not checked rollOperation if rollOperation.getRollAmount <0, not sure that it is necessary
     *
     * @param rollOperation - updated roll operation
     * @return saved roll operation
     * @throws NegativeAmountException - if roll operation changes roll's leftover to negative value
     */
    @Override
    public RollOperation update(RollOperation rollOperation) throws NegativeAmountException {
        RollOperation oldRollOperation = rollOperationRepository.getOne(rollOperation.getId());
        checkOperationUpdateAllowed(rollOperation);
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollOperation.getRollManufactured().getRollType());
        Integer differenceAmount = oldRollOperation.getRollAmount() - rollOperation.getRollAmount();
        rollLeftOverService.changeRollLeftOverAmount(rollLeftOver, differenceAmount);
        log.debug("Method update(RollOperation rollOperation): Roll operation {} is updating", rollOperation);
        return rollOperationRepository.save(rollOperation);
    }

    /**
     * The method deletes roll operation by operation id
     *
     * @param id - operation id
     * @throws ResourceNotFoundException - if there is not operation with this id
     * @throws NegativeAmountException   - if after trying to delete amount of roll's leftover is negative
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException, NegativeAmountException {
        RollOperation rollOperation = findById(id);
        checkOperationDeleteAllowed(rollOperation);
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollOperation.getRollManufactured().getRollType());
        rollLeftOverService.changeRollLeftOverAmount(rollLeftOver, -getOperationAmountWithSign(rollOperation));
        rollOperationRepository.deleteById(id);
        log.debug("Method delete(Long id): Operation {} with id {} was deleted", rollOperation, id);
    }

    /**
     * The method finds all operations with type MANUFACTURED and roll manufactured
     *
     * @param rollManufactured - roll manufactured (a batch of one roll's type that was produced one date)
     * @return list of operations
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllManufacturedOperationsByRollManufactured(RollManufactured rollManufactured) {
        log.debug("Method findAllManufacturedOperationsByRollManufactured(RollManufactured rollManufactured): " +
                "Roll operations with roll manufactured {} are finding", rollManufactured);
        return rollOperationRepository.findAllByOperationTypeAndRollManufactured(OperationType.MANUFACTURE,
                rollManufactured);
    }

    /**
     * The method finds all operations with type USE and roll manufactured
     *
     * @param rollManufactured - roll manufactured (a batch of one roll's type that was produced one date)
     * @return list of operations
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllUsedOperationsByRollManufactured(RollManufactured rollManufactured) {
        log.debug("Method findAllUsedOperationsByRollManufactured(RollManufactured rollManufactured): " +
                "Roll operations with roll manufactured {} are finding", rollManufactured);
        return rollOperationRepository.findAllByOperationTypeAndRollManufactured(OperationType.USE, rollManufactured);
    }

    /**
     * This method determines if operation has type MANUFACTURE
     *
     * @param rollOperation - roll operation
     * @return true if operation's type is MANUFACTURE or false if this one is USE
     */
    Boolean isItManufactureOperation(RollOperation rollOperation) {
        Boolean isManufacture = rollOperation.getOperationType().equals(OperationType.MANUFACTURE);
        log.debug("Method isItManufactureOperation(RollOperation rollOperation): Operation's type was determined as {}",
                isManufacture);
        return isManufacture;
    }

    /**
     * This method find the sign of operation
     *
     * @param rollOperation - roll operation
     * @return positive amount of operation if operation type is MANUFACTURE and negative if this one is USE
     */
    private Integer getOperationAmountWithSign(RollOperation rollOperation) {
        log.debug("Method getOperationAmountWithSign(RollOperation rollOperation): Sign of amount of " +
                "roll operation {} is finding", rollOperation);
        return isItManufactureOperation(rollOperation) ? rollOperation.getRollAmount() : -rollOperation.getRollAmount();
    }

    /**
     * The method determines if saving of operation is allowed
     *
     * @param rollOperation - roll operation
     * @throws NegativeAmountException - if operation change roll's leftover to negative value
     */
    private void checkOperationSaveAllowed(RollOperation rollOperation) throws NegativeAmountException {
        RollManufactured rollManufactured = rollOperation.getRollManufactured();
        if (!isItManufactureOperation(rollOperation)) {
            if (isRollNew(rollManufactured)) {
                log.error("Method checkOperationSaveAllowed(RollOperation rollOperation): Roll operation {} " +
                        "could not been saved if rolls were not manufactured", rollOperation);
                throw new NegativeAmountException("Roll's left is negative!");
            }
            Integer resultOfAmount =
                    rollManufacturedService.getLeftOverAmount(rollManufactured) - rollOperation.getRollAmount();
            if (resultOfAmount < 0) {
                log.error("Method checkOperationSaveAllowed(RollOperation rollOperation): Roll operation {} could " +
                        "not been saved if rollManufacturedAmount is negative", rollOperation);
                throw new NegativeAmountException("Roll's left is negative!");
            }
            log.debug("Method checkOperationSaveAllowed(RollOperation rollOperation): Roll operation {} is allowed", rollOperation);
        }
    }

    /**
     * The method determines if operation can be deleted
     *
     * @param rollOperation - roll operation
     * @throws NegativeAmountException - if manufacturedAmount is negative
     */
    private void checkOperationDeleteAllowed(RollOperation rollOperation) throws NegativeAmountException {
        if (isItManufactureOperation(rollOperation)) {
            RollManufactured rollManufactured = rollOperation.getRollManufactured();
            Integer resultOfAmount =
                    rollManufacturedService.getLeftOverAmount(rollManufactured) - rollOperation.getRollAmount();
            if (resultOfAmount < 0) {
                log.error("Method checkOperationDeleteAllowed(RollOperation rollOperation): Operation {} changes" +
                        " manufactureAmount to negative value", rollOperation);
                throw new NegativeAmountException("Roll's left is negative!");
            }
            log.debug("Method checkOperationDeleteAllowed(RollOperation rollOperation): Operation {} is allowed", rollOperation);
        }
    }

    /**
     * The method determines if operation can be updated
     *
     * @param rollOperation - new roll operation
     * @throws NegativeAmountException - if manufacturedAmount is negative
     */
    private void checkOperationUpdateAllowed(RollOperation rollOperation) {
        RollManufactured roll = rollOperation.getRollManufactured();
        List<RollOperation> operationList = rollOperationRepository.findAllByRollManufactured(roll);
        Integer resultAmount = 0;
        for (RollOperation operation : operationList) {
            if (Objects.equals(operation.getId(), rollOperation.getId())) {
                operation = rollOperation;
            }
            resultAmount += getOperationAmountWithSign(operation);
        }
        if (resultAmount < 0) {
            log.error("Method checkOperationUpdateAllowed(RollOperation rollOperation):" +
                    " Operation {} changes manufactureAmount to negative value", rollOperation);
            throw new NegativeAmountException("Roll's left is negative!");
        }
        log.debug("Method checkOperationUpdateAllowed(RollOperation rollOperation, RollOperation oldRollOperation):" +
                "Operation {} is allowed", rollOperation);
    }

    /**
     * The method determines if manufactured rolls are new
     *
     * @param rollManufactured - manufactured rolls
     * @return true if its are new and false if this manufactured rolls exist in DB
     */
    private Boolean isRollNew(RollManufactured rollManufactured) {
        log.debug("Method isRollNew(RollManufactured rollManufactured): Roll manufactured {} is checking if it's new",
                rollManufactured);
        return Objects.isNull(rollManufactured.getId());
    }

}