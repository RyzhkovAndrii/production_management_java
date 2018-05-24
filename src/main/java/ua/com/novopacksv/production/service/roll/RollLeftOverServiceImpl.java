package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollLeftOverRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class implements {@link RollLeftOverService}, contains the logic for working with roll's leftover - objects of
 * {@link RollLeftOver}
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RollLeftOverServiceImpl implements RollLeftOverService {

    /**
     * An object of repository layer for have access to methods of working with DB
     */
    private final RollLeftOverRepository rollLeftOverRepository;
    /**
     * An object of service layer for have access to methods of working with roll operations
     */
    private final RollOperationServiceImpl rollOperationService;
    /**
     * An object that give methods for convert from one format to another (here it is date)
     */
    private final ConversionService conversionService;

    /**
     * The method returns roll's leftovers on pointed date
     *
     * @param date - pointed date for searching
     * @return list of roll's leftovers - objects of {@link RollLeftOver}
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollLeftOver> findAllByDate(LocalDate date) {
        List<RollLeftOver> rollLeftOvers = findAll().stream()
                .map((rollLeftOver) -> getLeftOverOnDate(rollLeftOver, date))
                .collect(Collectors.toList());
        log.debug("Method findAllByDate(LocalDate date): List of RollLeftOvers {} are finding by date {}",
                rollLeftOvers, date);
        return rollLeftOvers;
    }

    /**
     * The method returns one roll's leftover on pointed date and rollTypeId
     *
     * @param rollTypeId - rollTypeId for searching
     * @param date       - pointed date for searching
     * @return roll's leftover - object of {@link RollLeftOver}
     * @throws ResourceNotFoundException - if there is not rollType with pointed Id
     */
    @Override
    @Transactional(readOnly = true)
    public RollLeftOver findByRollTypeIdAndDate(Long rollTypeId, LocalDate date) throws ResourceNotFoundException {
        RollLeftOver rollLeftOver = rollLeftOverRepository.findByRollType_Id(rollTypeId).orElseThrow(() -> {
            log.error("Method findByRollTypeIdAndDate(Long rollTypeId, LocalDate date): " +
                    "RollLeftOver was not found with rollTypeId {}", rollTypeId);
            String formatDate = conversionService.convert(date, String.class);
            String message =
                    String.format("RollLeftOver with typeId = %d on date = %s is not found", rollTypeId, formatDate);
            return new ResourceNotFoundException(message);
        });
        RollLeftOver leftOverOnDate = getLeftOverOnDate(rollLeftOver, date);
        log.debug("Method findByRollTypeIdAndDate(Long rollTypeId, LocalDate date): RollLeftOver {} was found " +
                        "with rollTypeId {} and on date {}",
                leftOverOnDate, rollTypeId, date);
        return leftOverOnDate;
    }

    /**
     * The method creates new leftover for (new*) roll type
     *
     * @param rollType - (new*) roll type
     */
    @Override
    public void createNewLeftOverAndSave(RollType rollType) {
        RollLeftOver leftOver = new RollLeftOver();
        leftOver.setDate(LocalDate.now());
        leftOver.setRollType(rollType);
        leftOver.setAmount(0);
        rollLeftOverRepository.save(leftOver);
        log.debug("Method createNewLeftOverAndSave(RollType rollType): New leftover {} for rollType {} was created",
                leftOver, rollType);
    }

    /**
     * The method finds roll's leftover by id on current date
     *
     * @param id - roll's leftover's id for searching
     * @return roll's leftover
     */
    @Override
    public RollLeftOver getTotalLeftOver(LocalDate date) {
        Integer sumAmount = findAllByDate(date).stream().mapToInt(RollLeftOver::getAmount).sum();
        RollLeftOver rollLeftOver = new RollLeftOver();
        rollLeftOver.setDate(date);
        rollLeftOver.setAmount(sumAmount);
        return rollLeftOver;
    }

    @Override
    @Transactional(readOnly = true)
    public RollLeftOver findById(Long id) {
        RollLeftOver rollLeftOver = rollLeftOverRepository.findById(id).orElseThrow(() -> {
            log.error("Method findById(*): rollLeftOver was not found by id {}", id);
            String message = String.format("Roll left over with id = %d is not found", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): rollLeftOver {} is finding by id {}", rollLeftOver, id);
        return rollLeftOver;
    }

    /**
     * Method finds all roll's leftovers on current date
     *
     * @return list of exist roll's leftovers
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollLeftOver> findAll() {
        List<RollLeftOver> rollLeftOvers = rollLeftOverRepository.findAll();
        log.debug("Method findAll(): List of RollLeftOver was found: {}", rollLeftOvers);
        return rollLeftOvers;
    }

    /**
     * Method saves rollLeftOver
     *
     * @param rollLeftOver - roll's leftover for save
     * @return saved roll's leftover
     */
    @Override
    public RollLeftOver save(RollLeftOver rollLeftOver) {
        log.debug("Method save(RollLeftOver rollLeftOver): RollLeftOver {} is saving", rollLeftOver);
        return rollLeftOverRepository.save(rollLeftOver);
    }

    /**
     * Method saves changed roll's leftover
     *
     * @param rollLeftOver - changed roll's leftover
     * @return changed roll's leftover
     */
    @Override
    public RollLeftOver update(RollLeftOver rollLeftOver) {
        log.debug("Method update(RollLeftOver rollLeftOver): RollLeftover {} was updated", rollLeftOver);
        return rollLeftOverRepository.save(rollLeftOver);
    }

    /**
     * Method removes roll's leftover by it's id
     *
     * @param id - roll's leftover's id for remove
     */
    @Override
    public void delete(Long id) {
        RollLeftOver rollLeftOver = findById(id);
        rollLeftOverRepository.deleteById(id);
        log.debug("Method delete(Long id): RollLeftOver {} with id {} was deleted", rollLeftOver, id);
    }

    /**
     * Method finds roll's leftover by roll's type on current date
     *
     * @param rollType - roll's type for search
     * @return roll's leftover - object of {@link RollLeftOver}
     */
    @Override
    @Transactional(readOnly = true)
    public RollLeftOver findLastRollLeftOverByRollType(RollType rollType) {
        RollLeftOver rollLeftOver = rollLeftOverRepository.findByRollType_Id(rollType.getId()).orElseThrow(() -> {
            log.error("Method findLastRollLeftOverByRollType(RollType rollType): RollLeftOver was not found " +
                    "by roll's type {}", rollType);
            String message = String.format("RollLeftOver with typeId = %d is not found", rollType.getId());
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findLastRollLeftOverByRollType(RollType rollType): RollLeftOver {} " +
                "was found by roll's type {}", rollLeftOver, rollType);
        return rollLeftOver;
    }

    /**
     * Method counts roll's leftover on pointed date
     *
     * @param rollLeftOver - roll's leftover on current date
     * @param date         - date for search
     * @return roll's leftover on pointed date
     */
    private RollLeftOver getLeftOverOnDate(RollLeftOver rollLeftOver, LocalDate date) {
        Integer lastAmount = rollLeftOver.getAmount();
        List<RollOperation> rollOperations = rollOperationService.findAllByRollTypeAndManufacturedDateBetween(
                rollLeftOver.getRollType(), date.plusDays(1), rollLeftOver.getDate());
        for (RollOperation rollOperation : rollOperations) {
            lastAmount += rollOperationService.isItManufactureOperation(rollOperation)
                    ? -rollOperation.getRollAmount()
                    : rollOperation.getRollAmount();
        }
        RollLeftOver rollLeftOverTemp = new RollLeftOver();
        rollLeftOverTemp.setDate(date);
        rollLeftOverTemp.setRollType(rollLeftOver.getRollType());
        rollLeftOverTemp.setAmount(lastAmount);
        log.debug("Method getLeftOverOnDate(RollLeftOver rollLeftOver, LocalDate date): RollLeftOver on date {} " +
                "was found: {}", date, rollLeftOverTemp);
        return rollLeftOverTemp;
    }

    /**
     * Method changes roll's leftover's amount
     *
     * @param rollLeftOver              - roll's leftover for change
     * @param positiveOrNegativeChanges - integer value of changing roll's leftover's amount
     */
    public void changeRollLeftOverAmount(RollLeftOver rollLeftOver, Integer positiveOrNegativeChanges) {
        Integer oldAmount = rollLeftOver.getAmount();
        rollLeftOver.setAmount(oldAmount + positiveOrNegativeChanges);
        update(rollLeftOver);
        log.debug("Method changeRollLeftOverAmount(RollLeftOver rollLeftOver, Integer positiveOrNegativeChanges): " +
                        "RollLeftOver {} was changed by value {}",
                rollLeftOver, positiveOrNegativeChanges);
    }
}