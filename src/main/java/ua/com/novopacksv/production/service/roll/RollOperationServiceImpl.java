package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
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
    private final RollLeftOverService rollLeftOverService;
    private final RollManufacturedService rollManufacturedService;

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

    @Override
    public RollOperation save(RollOperation rollOperation) {
        return rollOperationSaveCorrect(rollOperation);
    }

    @Override
    public RollOperation update(RollOperation rollOperation) {
        return rollOperationSaveCorrect(rollOperation);
    }

    @Override
    public void delete(Long id) {
        rollOperationRepository.deleteById(id);
    }

    private RollOperation rollOperationSaveCorrect(RollOperation rollOperation) {
        RollManufactured rollManufactured = rollOperation.getRollManufactured();
        Integer rollManufacturedAmount = rollManufacturedService.getManufacturedRollAmount(rollManufactured);
        Integer rollUsedAmount = rollManufacturedService.getUsedRollAmount(rollManufactured);
        if (rollOperation.getOperationType().equals(OperationType.USE)) {
            if ((rollManufacturedAmount - rollUsedAmount - rollOperation.getRollAmount()) >= 0) {
                RollLeftOver rollLeftOver = rollLeftOverService
                        .findByRollTypeIdAndDate(rollManufactured.getRollType().getId(), LocalDate.now());
                Integer rollLeftOldAmount = rollLeftOver.getAmount();
                rollLeftOver.setAmount(rollLeftOldAmount - rollOperation.getRollAmount());
                return rollOperationRepository.save(rollOperation);
            } else throw new NegativeRollAmountException("Roll's left is negative!");
        } else
            return rollOperationRepository.save(rollOperation);
    }
}