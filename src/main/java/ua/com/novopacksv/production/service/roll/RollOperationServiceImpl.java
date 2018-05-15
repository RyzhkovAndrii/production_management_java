package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NegativeRollAmountException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.*;
import ua.com.novopacksv.production.repository.rollRepository.RollOperationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class RollOperationServiceImpl implements RollOperationService {

    private final RollOperationRepository rollOperationRepository;

    @Autowired
    @Lazy
    private RollLeftOverServiceImpl rollLeftOverService; // todo circular dependencies remove

    @Autowired
    @Lazy
    private RollManufacturedServiceImpl rollManufacturedService; // todo circular dependencies remove

    /*
    Find all operations by RollType for period - for tables of rollType`s information
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeAndManufacturedDateBetween(RollType rollType, LocalDate fromDate, LocalDate toDate) {
        return rollOperationRepository
                .findAllByRollManufactured_RollTypeAndRollManufactured_ManufacturedDateBetween(rollType, fromDate, toDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeIdAndManufacturedPeriod(Long id, LocalDate from, LocalDate to) {
        List<RollManufactured> rollManufacturedList = rollManufacturedService.findAll(from, to, id);
        return rollOperationRepository.findAllByRollManufacturedIsIn(rollManufacturedList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeIdAndOperationPeriod(Long id, LocalDate from, LocalDate to) {
        return rollOperationRepository.findAllByRollManufactured_RollType_IdAndOperationDateBetween(id, from, to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeId(Long id) {
        List<RollManufactured> rollManufacturedList = rollManufacturedService.findAll(id);
        return rollOperationRepository.findAllByRollManufacturedIsIn(rollManufacturedList);
    }

    @Override
    @Transactional(readOnly = true)
    public RollOperation findById(Long id) {
        return rollOperationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Roll operation with id = %d is not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAll() {
        return rollOperationRepository.findAll();
    }

    /*
    Before save method recounts and rewrites rollLeftOver
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public RollOperation save(RollOperation rollOperation) throws NegativeRollAmountException {
        checkOperationSaveAllowed(rollOperation);
        RollManufactured rollManufactured = rollOperation.getRollManufactured();
        Integer changingAmount = getOperationAmountWithSign(rollOperation);
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollManufactured.getRollType());
        rollLeftOverService.changeRollLeftOverAmount(rollLeftOver, changingAmount);
        return rollOperationRepository.save(rollOperation);
    }

    /*
    Not checked rollOperation if rollOperation.getRollAmount <0, not sure that it is necessary
     */
    @Override
    public RollOperation update(RollOperation rollOperation) throws NegativeRollAmountException {
        RollOperation oldRollOperation = rollOperationRepository.getOne(rollOperation.getId());
        checkOperationUpdateAllowed(rollOperation, oldRollOperation);
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollOperation.getRollManufactured().getRollType());
        Integer differenceAmount = rollOperation.getRollAmount() - oldRollOperation.getRollAmount();
        rollLeftOverService.changeRollLeftOverAmount(rollLeftOver, differenceAmount);
        return rollOperationRepository.save(rollOperation);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException, NegativeRollAmountException {
        RollOperation rollOperation = findById(id);
        checkOperationDeleteAllowed(rollOperation);
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollOperation.getRollManufactured().getRollType());
        rollLeftOverService.changeRollLeftOverAmount(rollLeftOver, -getOperationAmountWithSign(rollOperation));
        rollOperationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> getAllManufacturedOperationsByRollManufactured(RollManufactured rollManufactured) {
        return rollOperationRepository.findAllByOperationTypeAndRollManufactured(OperationType.MANUFACTURE, rollManufactured);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> getAllUsedOperationsByRollManufactured(RollManufactured rollManufactured) {
        return rollOperationRepository.findAllByOperationTypeAndRollManufactured(OperationType.USE, rollManufactured);
    }

    public Boolean isItManufactureOperation(RollOperation rollOperation) {
        return rollOperation.getOperationType().equals(OperationType.MANUFACTURE);
    }

    private Integer getOperationAmountWithSign(RollOperation rollOperation) {
        return isItManufactureOperation(rollOperation) ? rollOperation.getRollAmount() : -rollOperation.getRollAmount();
    }

    private void checkOperationSaveAllowed(RollOperation rollOperation) throws NegativeRollAmountException {
        RollManufactured rollManufactured = rollOperation.getRollManufactured();
        if (!isItManufactureOperation(rollOperation)) {
            if (isRollNew(rollManufactured)) {
                throw new NegativeRollAmountException("Roll's left is negative!");
            }
            Integer resultOfAmount =
                    rollManufacturedService.getLeftOverAmount(rollManufactured) - rollOperation.getRollAmount();
            if (resultOfAmount < 0) {
                throw new NegativeRollAmountException("Roll's left is negative!");
            }
        }
    }

    private void checkOperationDeleteAllowed(RollOperation rollOperation) throws NegativeRollAmountException {
        if (isItManufactureOperation(rollOperation)) {
            RollManufactured rollManufactured = rollOperation.getRollManufactured();
            Integer resultOfAmount =
                    rollManufacturedService.getLeftOverAmount(rollManufactured) - rollOperation.getRollAmount();
            if (resultOfAmount < 0) {
                throw new NegativeRollAmountException("Roll's left is negative!");
            }
        }
    }

    private void checkOperationUpdateAllowed(RollOperation rollOperation, RollOperation oldRollOperation) {
        Integer differenceAmount = rollOperation.getRollAmount() - oldRollOperation.getRollAmount();
        RollManufactured rollManufactured = rollOperation.getRollManufactured();
        Integer resultOfAmount = isItManufactureOperation(rollOperation)
                ? rollManufacturedService.getLeftOverAmount(rollManufactured) + differenceAmount
                : rollManufacturedService.getLeftOverAmount(rollManufactured) - differenceAmount;
        if (resultOfAmount < 0) {
            throw new NegativeRollAmountException("Roll's left is negative!");
        }
    }

    private Boolean isRollNew(RollManufactured rollManufactured) {
        return Objects.isNull(rollManufactured.getId());
    }

}