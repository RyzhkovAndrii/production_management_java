package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.OperationType;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollLeftOverRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RollLeftOverServiceImpl implements RollLeftOverService {

    private final RollLeftOverRepository rollLeftOverRepository;
    private final RollOperationServiceImpl rollOperationService;

    @Override
    public List<RollLeftOver> findAllByDate(LocalDate date) {
        if (!findAll().isEmpty()) {
            return findAll().stream().map((rollLeftOver) -> checkLeftOverOnDate(rollLeftOver, date))
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("There are not rolls on this date!");
        }
    }

    private RollLeftOver checkLeftOverOnDate(RollLeftOver rollLeftOver, LocalDate date) {
        Integer lastAmount = rollLeftOver.getAmount();
        List<RollOperation> rollOperations =
                rollOperationService.findAllByRollTypeAndDateBetween(rollLeftOver.getRollType(),
                        date, rollLeftOver.getDate());
        if (!rollOperations.isEmpty()) {
            for (RollOperation rollOperation : rollOperations) {
                if (rollOperation.getOperationType().equals(OperationType.USE)) {
                    rollLeftOver.setAmount(lastAmount + rollOperation.getRollAmount());
                } else {
                    rollLeftOver.setAmount(lastAmount - rollOperation.getRollAmount());
                }
            }
        }
        return rollLeftOver;
    }

    @Override
    public RollLeftOver findByRollTypeIdAndDate(Long rollTypeId, LocalDate date) {
        return null;
    }

    @Override
    public RollLeftOver findLastRollLeftOverByRollType(RollType rollType) {
        return null;
    }

    @Override
    public RollLeftOver findById(Long id) {
        return rollLeftOverRepository.findById(id).orElseThrow(() ->
        {
            String message = String.format("Roll left over with id = %d is not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    public List<RollLeftOver> findAll() {
        return null;
    }

    @Override
    public RollLeftOver save(RollLeftOver rollLeftOver) {
        return null;
    }

    @Override
    public RollLeftOver update(RollLeftOver rollLeftOver) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    public void changeRollLeftOverAmount(RollLeftOver rollLeftOver, Integer positiveOrNegativeChanges) {
        Integer oldAmount = rollLeftOver.getAmount();
        rollLeftOver.setAmount(oldAmount + positiveOrNegativeChanges);
        update(rollLeftOver);
    }
}