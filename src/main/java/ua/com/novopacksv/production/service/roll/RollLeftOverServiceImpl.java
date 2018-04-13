package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RollLeftOverServiceImpl implements RollLeftOverService {

    private final RollLeftOverRepository rollLeftOverRepository;
    private final RollOperationServiceImpl rollOperationService;

    @Override
    public List<RollLeftOver> findAllByDate(LocalDate date) {

            return findAll().stream().map((rollLeftOver) -> checkLeftOverOnDate(rollLeftOver, date))
                    .collect(Collectors.toList());
        
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
    public RollLeftOver findByRollTypeIdAndDate(Long rollTypeId, LocalDate date) throws ResourceNotFoundException {
        RollLeftOver rollLeftOver = rollLeftOverRepository.findByRollType_Id(rollTypeId);
        if (rollLeftOver != null) {
            return checkLeftOverOnDate(rollLeftOver, date);
        } else {
            String message = String.format("RollLeftOver with id = %d is not found", rollLeftOver.getId());
            throw new ResourceNotFoundException(message);
        }
    }

    @Override
    public RollLeftOver findLastRollLeftOverByRollType(RollType rollType) {
        Optional<RollLeftOver> rollLeftOver = Optional.ofNullable(rollLeftOverRepository
                .findByRollType_Id(rollType.getId()));

        return rollLeftOver.orElseThrow(() ->
        {
            String message = String.format("RollType with name = %s is not found", rollType.getName());
            return new ResourceNotFoundException(message);
        });
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
        return rollLeftOverRepository.findAll();
    }

    @Override
    public RollLeftOver save(RollLeftOver rollLeftOver) {
        return rollLeftOverRepository.save(rollLeftOver);
    }

    @Override
    public RollLeftOver update(RollLeftOver rollLeftOver) {
        return rollLeftOverRepository.save(rollLeftOver);
    }

    @Override
    public void delete(Long id) {
        rollLeftOverRepository.deleteById(id);
        log.error("RollLeftOver with id = %d is not deleted", id);
    }

    public void getRollLeftOverAmount(RollLeftOver rollLeftOver, Integer positiveOrNegativeChanges) {
        Integer oldAmount = rollLeftOver.getAmount();
        rollLeftOver.setAmount(oldAmount + positiveOrNegativeChanges);
        update(rollLeftOver);
    }
}