package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;
import ua.com.novopacksv.production.repository.planRepository.RollPlanOperationRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Class implements interface {@link RollPlanOperationService} and contains methods for work with repository's level
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RollPlanOperationServiceImpl implements RollPlanOperationService {

    /**
     * An object of repository layer for have access to methods for work with db
     */
    private final RollPlanOperationRepository rollPlanOperationRepository;

    /**
     * Method finds all operations for pointed roll type for a period
     *
     * @param rollTypeId - searching roll type's id
     * @param fromDate   - a beginning of a period
     * @param toDate     - an end of a period
     * @return list of roll plan operations
     */
    @Override
    public List<RollPlanOperation> findAll(Long rollTypeId, LocalDate fromDate, LocalDate toDate) {
        log.debug("Method findAll(Long rollTypeId, LocalDate fromDate, LocalDate toDate): List<RollPlanOperation> with " +
                "rollTypeId = {}, for period from {} to {} was found", rollTypeId, fromDate, toDate);
        return rollPlanOperationRepository.findAllByRollType_IdAndDateBetween(rollTypeId, fromDate, toDate);
    }

    /**
     * Method finds RollPlanOperation by it's id
     *
     * @param id - rollPlanOperation's id
     * @return RollPlanOperation from db
     * @throws ResourceNotFoundException if RollPlanOperation with pointed id does not exist in db
     */
    @Override
    public RollPlanOperation findById(Long id) throws ResourceNotFoundException {
        RollPlanOperation rollPlanOperation = rollPlanOperationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("The roll plan operation with id = %d was not found", id);
            log.error("Method findById(Long id): The roll plan operation with id ={} does not exist", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): The roll plan operation with id ={} was found: {}", id, rollPlanOperation);
        return rollPlanOperation;
    }

    /**
     * Method finds all RollPlanOperations from db
     *
     * @return list of RollPlanOperations
     */
    @Override
    public List<RollPlanOperation> findAll() {
        log.debug("Method findAll(): List<RollPlanOperation> was found");
        return rollPlanOperationRepository.findAll();
    }

    /**
     * Method saves new RollPlanOperation in db
     *
     * @param rollPlanOperation - new RollPlanOperation
     * @return saved RollPlanOperation
     */
    @Override
    public RollPlanOperation save(RollPlanOperation rollPlanOperation) {
        RollPlanOperation rollPlanOperationSaved = rollPlanOperationRepository.save(rollPlanOperation);
        log.debug("Method save(RollPlanOperation rollPlanOperation): RollPlanOperation {} was saved",
                rollPlanOperationSaved);
        return findById(rollPlanOperationSaved.getId());
    }

    /**
     * Method updates existed RollPlanOperation
     *
     * @param rollPlanOperation - RollPlanOperation for update
     * @return updated RollPlanOperation
     * @throws ResourceNotFoundException if RollPlanOperation with pointed id does not exist in db
     */
    @Override
    public RollPlanOperation update(RollPlanOperation rollPlanOperation) throws ResourceNotFoundException {
        findById(rollPlanOperation.getId());
        RollPlanOperation rollPlanOperationSaved = rollPlanOperationRepository.save(rollPlanOperation);
        log.debug("Method update(RollPlanOperation rollPlanOperation): RollPlanOperation {} was updated",
                rollPlanOperationSaved);
        return rollPlanOperationSaved;
    }

    /**
     * Method deletes RollPlanOperation by it's id
     *
     * @param id - id of RollPlanOperation for delete
     * @throws ResourceNotFoundException if RollPlanOperation with pointed id does not exist in db
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        rollPlanOperationRepository.delete(findById(id));
        log.debug("Method delete(Long id): RollPlanOperation with id ={} was deleted", id);
    }
}
