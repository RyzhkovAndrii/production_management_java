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
     * @param rollTypeId - searching roll type's id
     * @param fromDate - a beginning of a period
     * @param toDate - an end of a period
     * @return list of roll plan operations
     */
    @Override
    public List<RollPlanOperation> findAll(Long rollTypeId, LocalDate fromDate, LocalDate toDate) {
        return rollPlanOperationRepository.findAllByRollType_IdAndDateBetween(rollTypeId, fromDate, toDate);
    }

    @Override
    public RollPlanOperation findById(Long id) throws ResourceNotFoundException {
        return rollPlanOperationRepository.findById(id).orElseThrow(() -> {
            String message = String.format("The roll plan operation with id = %d was not found", id);
            log.error("Method findById(Long id): The roll plan operation with id =() does not exist", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    public List<RollPlanOperation> findAll() {
        return rollPlanOperationRepository.findAll();
    }

    @Override
    public RollPlanOperation save(RollPlanOperation rollPlanOperation) {
        return rollPlanOperationRepository.save(rollPlanOperation);
    }

    @Override
    public RollPlanOperation update(RollPlanOperation rollPlanOperation) throws ResourceNotFoundException {
        findById(rollPlanOperation.getId());
        return rollPlanOperationRepository.save(rollPlanOperation);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        rollPlanOperationRepository.delete(findById(id));
    }
}
