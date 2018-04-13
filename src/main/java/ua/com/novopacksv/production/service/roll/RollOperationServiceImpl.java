package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NegativeRollAmountException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.*;
import ua.com.novopacksv.production.repository.rollRepository.RollOperationRepository;

import java.time.LocalDate;
import java.util.List;

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
    public List<RollOperation> findAllByRollType(RollType rollType) {
        return rollOperationRepository.findAllByRollManufactured_RollType(rollType);
    }

    /*
    Find all operations by RollType for period - for tables of rollType`s information
     */
    @Override
    public List<RollOperation> findAllByRollTypeAndDateBetween(RollType rollType, LocalDate fromDate, LocalDate toDate) {
        return rollOperationRepository
                .findAllByRollManufactured_RollTypeAndOperationDateBetween(rollType, fromDate, toDate);
    }

    @Override
    public RollOperation findById(Long id) {
        return rollOperationRepository.findById(id).orElseThrow(() ->
        {
            String message = String.format("Roll operation with id = %d is not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    public List<RollOperation> findAll() {
        return rollOperationRepository.findAll();
    }

    /*
    Before save method recounts and rewrites rollLeftOver
     */
    @Override
    public RollOperation save(RollOperation rollOperation) throws NegativeRollAmountException {
        RollManufactured rollManufactured = rollOperation.getRollManufactured();
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollManufactured.getRollType());
        rollLeftOverService.getRollLeftOverAmount(rollLeftOver,
                checkOperationOfNegativeAmount(rollOperation));
        return rollOperationRepository.save(rollOperation);
    }

    /*
    Not checked rollOperation if rollOperation.getRollAmount <0, not sure that it is necessary
     */
    @Override
    public RollOperation update(RollOperation rollOperation) {
        RollOperation rollOperationOld = rollOperationRepository.getOne(rollOperation.getId());
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(rollOperation.getRollManufactured().getRollType());
        rollLeftOverService.getRollLeftOverAmount(rollLeftOver,
                rollOperation.getRollAmount() - rollOperationOld.getRollAmount());
        return rollOperationRepository.save(rollOperation);
    }

    @Override
    public void delete(Long id) {
        RollLeftOver rollLeftOver = rollLeftOverService
                .findLastRollLeftOverByRollType(findById(id).getRollManufactured().getRollType());
        rollLeftOverService.getRollLeftOverAmount(rollLeftOver, -findById(id).getRollAmount());
        rollOperationRepository.deleteById(id);
    }

    @Override
    public List<RollOperation> getAllManufacturedOperationsByRollManufactured(RollManufactured rollManufactured) {
        return rollOperationRepository.findAllByOperationTypeAndRollManufactured(OperationType.MANUFACTURE, rollManufactured);
    }

    @Override
    public List<RollOperation> getAllUsedOperationsByRollManufactured(RollManufactured rollManufactured) {
        return rollOperationRepository.findAllByOperationTypeAndRollManufactured(OperationType.USE, rollManufactured);
    }

    private Integer checkOperationOfNegativeAmount(RollOperation rollOperation)
            throws NegativeRollAmountException {
        Integer rollManufacturedAmount = rollManufacturedService.getManufacturedRollAmount(rollOperation.getRollManufactured());
        Integer rollUsedAmount = rollManufacturedService.getUsedRollAmount(rollOperation.getRollManufactured());

        if (rollOperation.getOperationType().equals(OperationType.USE)) {
            Integer resultOfAmount = rollManufacturedAmount - rollUsedAmount - rollOperation.getRollAmount();
            if (resultOfAmount >= 0) {
                return -rollOperation.getRollAmount();
            } else {
                throw new NegativeRollAmountException("Roll's left is negative!");
            }
        } else {
            return rollOperation.getRollAmount();
        }
    }

}