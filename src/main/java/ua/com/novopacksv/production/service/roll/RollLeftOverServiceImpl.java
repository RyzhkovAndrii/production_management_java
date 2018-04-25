package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    private final ConversionService conversionService;

    @Override
    @Transactional(readOnly = true)
    public List<RollLeftOver> findAllByDate(LocalDate date) {
        return findAll().stream()
                .map((rollLeftOver) -> checkLeftOverOnDate(rollLeftOver, date))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RollLeftOver findByRollTypeIdAndDate(Long rollTypeId, LocalDate date) throws ResourceNotFoundException {
        RollLeftOver rollLeftOver = rollLeftOverRepository.findByRollType_Id(rollTypeId).orElseThrow(() -> {
            String formatDate = conversionService.convert(date, String.class);
            String message =
                    String.format("RollLeftOver with typeId = %d on date = %s is not found", rollTypeId, formatDate);
            return new ResourceNotFoundException(message);
        });
        return checkLeftOverOnDate(rollLeftOver, date);
    }


    @Override
    public void createNewLeftOverAndSave(RollType rollType) {
        RollLeftOver leftOver = new RollLeftOver();
        leftOver.setDate(LocalDate.now());
        leftOver.setRollType(rollType);
        leftOver.setAmount(0);
        rollLeftOverRepository.save(leftOver);
    }

    @Override
    @Transactional(readOnly = true)
    public RollLeftOver findById(Long id) {
        return rollLeftOverRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Roll left over with id = %d is not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollLeftOver> findAll() {
        return rollLeftOverRepository.findAll();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
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
    }

    @Override
    @Transactional(readOnly = true)
    public RollLeftOver findLastRollLeftOverByRollType(RollType rollType) {
        return rollLeftOverRepository.findByRollType_Id(rollType.getId()).orElseThrow(() -> {
            String message = String.format("RollLeftOver with typeId = %d is not found", rollType.getId());
            return new ResourceNotFoundException(message);
        });
    }

    private RollLeftOver checkLeftOverOnDate(RollLeftOver rollLeftOver, LocalDate date) {
        Integer lastAmount = rollLeftOver.getAmount();
        RollLeftOver rollLeftOverTemp = new RollLeftOver();
        rollLeftOverTemp.setDate(date);
        rollLeftOverTemp.setRollType(rollLeftOver.getRollType());
        List<RollOperation> rollOperations =
                rollOperationService.findAllByRollTypeAndDateBetween(rollLeftOver.getRollType(),
                        date, rollLeftOver.getDate());
        for (RollOperation rollOperation : rollOperations) {
            lastAmount = isItUseOperation(rollOperation)
                    ? lastAmount + rollOperation.getRollAmount()
                    : lastAmount - rollOperation.getRollAmount();
        }
        rollLeftOverTemp.setAmount(lastAmount);
        return rollLeftOverTemp;
    }

    private Boolean isItUseOperation(RollOperation rollOperation) {
        return rollOperation.getOperationType().equals(OperationType.USE);
    }

    public void changeRollLeftOverAmount(RollLeftOver rollLeftOver, Integer positiveOrNegativeChanges) {
        Integer oldAmount = rollLeftOver.getAmount();
        rollLeftOver.setAmount(oldAmount + positiveOrNegativeChanges);
        update(rollLeftOver);
    }
}