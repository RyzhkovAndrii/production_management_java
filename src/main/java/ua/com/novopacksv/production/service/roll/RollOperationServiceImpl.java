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
    Research all operations by RollType for count RollLeftOver
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollType(RollType rollType) {
        return rollOperationRepository.findAllByRollManufactured_RollType(rollType);
    }

    /*
    Find all operations by RollType for period - for tables of rollType`s information
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollOperation> findAllByRollTypeAndDateBetween(RollType rollType, LocalDate fromDate, LocalDate toDate) {
        return rollOperationRepository
                .findAllByRollManufactured_RollTypeAndOperationDateBetween(rollType, fromDate, toDate);
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
        checkOperationSaveAllowed(rollOperation);
        RollOperation rollOperationOld = rollOperationRepository.getOne(rollOperation.getId());//update only for amount
        RollManufactured rollManufactured = rollOperation.getRollManufactured();// what kind of operation
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollManufactured.getRollType());
        rollLeftOverService.changeRollLeftOverAmount(rollLeftOver,
                rollOperation.getRollAmount() - rollOperationOld.getRollAmount());
        return rollOperationRepository.save(rollOperation);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException, NegativeRollAmountException {
        RollOperation rollOperation = findById(id);
        checkOperationDeleteAllowed(rollOperation);
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(findById(id).getRollManufactured().getRollType());
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

    private Integer getOperationAmountWithSign(RollOperation rollOperation) {
        return isItManufactureOperation(rollOperation) ? rollOperation.getRollAmount() : -rollOperation.getRollAmount();
    }

    private void checkOperationSaveAllowed(RollOperation rollOperation)
            throws NegativeRollAmountException {
        RollManufactured rollManufactured = rollOperation.getRollManufactured();
        Integer rollManufacturedAmount = 0;
        Integer rollUsedAmount = 0;
        Integer resultOfAmount;
        if (!isItManufactureOperation(rollOperation)) {
            rollManufacturedAmount
                    = isRollNew(rollManufactured) ? 0 : rollManufacturedService.getManufacturedRollAmount(rollManufactured);
            rollUsedAmount
                    = isRollNew(rollManufactured) ? 0 : rollManufacturedService.getUsedRollAmount(rollManufactured);
        }
        resultOfAmount = rollManufacturedAmount - rollUsedAmount - rollOperation.getRollAmount();
        if (resultOfAmount < 0) {
            throw new NegativeRollAmountException("Roll's left is negative!");
        }
    }

    private void checkOperationDeleteAllowed(RollOperation rollOperation) throws NegativeRollAmountException {
        if (isItManufactureOperation(rollOperation)) {
            RollManufactured rollManufactured = rollOperation.getRollManufactured();
            Integer rollManufacturedAmount = rollManufacturedService.getManufacturedRollAmount(rollManufactured);
            Integer rollUsedAmount = rollManufacturedService.getUsedRollAmount(rollManufactured);
            Integer resultOfAmount = rollManufacturedAmount - rollUsedAmount - rollOperation.getRollAmount();
            if (resultOfAmount < 0) {
                throw new NegativeRollAmountException("Roll's left is negative!");
            }
        }
    }

    private Boolean isItManufactureOperation(RollOperation rollOperation) {
        return rollOperation.getOperationType().equals(OperationType.MANUFACTURE);
    }

    private Boolean isRollNew(RollManufactured rollManufactured) {
        return Objects.isNull(rollManufactured.getId());
    }

}