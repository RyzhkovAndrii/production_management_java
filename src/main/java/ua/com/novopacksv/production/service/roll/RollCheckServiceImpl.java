package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.CheckStatus;
import ua.com.novopacksv.production.model.rollModel.RollCheck;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollCheckRepository;

import java.util.List;

/**
 * Class implements {@link RollCheckService}, contains logic for working with checking if the roll's
 * information was verified
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RollCheckServiceImpl implements RollCheckService {

    /**
     * An object of repository layer for work with DB
     */
    private final RollCheckRepository rollCheckRepository;

    /**
     * An object of service layer for work with RollType
     */
    @Autowired
    @Lazy
    private RollTypeService rollTypeService;

    /**
     * Method finds a RollCheck of the roll's type by rollType's id
     *
     * @param rollTypeId - roll's type's id
     * @return RollCheck
     */
    @Override
    @Transactional(readOnly = true)
    public RollCheck findOneByRollTypeId(Long rollTypeId) {
        RollCheck rollCheck = rollCheckRepository.findByRollType_Id(rollTypeId).orElseThrow(() -> {
            log.error("Method findOneByRollTypeId(Long rollTypeId): RollCheck by rollType's id {} was not found",
                    rollTypeId);
            String message = String.format("Roll check with roll type id = %d is not found!", rollTypeId);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findOneByRollTypeId(Long rollTypeId): RollCheck {} by rollType's id {} was found",
                rollCheck, rollTypeId);
        return rollCheck;
    }

    /**
     * Method finds all rollChecks
     *
     * @return list of RollChecks
     */
    @Override
    @Transactional(readOnly = true)
    public List<RollCheck> findAll() {
        List<RollCheck> rollChecks = rollCheckRepository.findAll();
        log.debug("Method findAll(): List of RollChecks was found: {}", rollChecks);
        return rollChecks;
    }

    /**
     * Method saves changed rollCheck
     *
     * @param rollCheck - changed rollCheck
     * @return saved and changed rollCheck
     */
    @Override
    public RollCheck update(RollCheck rollCheck) {
        RollType rollType = rollTypeService.findById(rollCheck.getId());
        rollCheck.setRollType(rollType);
        log.debug("Method update(RollCheck rollCheck): RollCheck {} was saved", rollCheck);
        return rollCheckRepository.save(rollCheck);
    }

    /**
     * Method create new RollCheck and set its status {@code CheckStatus.NOT_CHECKED}
     *
     * @param rollType - roll type for check
     */
    @Override
    public void createNewRollCheckAndSave(RollType rollType) {
        RollCheck rollCheck = new RollCheck();
        rollCheck.setRollType(rollType);
        rollCheck.setRollLeftOverCheckStatus(CheckStatus.NOT_CHECKED);
        rollCheckRepository.save(rollCheck);
        log.debug("Method createNewRollCheckAndSave(RollType rollType): New rollCheck {} for rollType {} " +
                "was created and saved", rollCheck, rollType);
    }

    /**
     * Method sets status {@code CheckStatus.NOT_CHECKED} for all rollChecks
     */
    @Override
    public void setNotCheckedStatusForAll() {
        findAll().forEach(this::setNotCheckedStatus);
        List<RollCheck> rollChecks = findAll();
        log.debug("Method setNotCheckedStatusForAll(): For all rollChecks was set status NOT_CHECKED: {}", rollChecks);
    }

    /**
     * Method sets status {@code CheckStatus.NOT_CHECKED} for one rollCheck
     *
     * @param rollCheck - rollCheck for set status
     */
    private void setNotCheckedStatus(RollCheck rollCheck) {
        rollCheck.setRollLeftOverCheckStatus(CheckStatus.NOT_CHECKED);
        rollCheckRepository.save(rollCheck);
        log.debug("Method setNotCheckedStatus(RollCheck rollCheck): For rollCheck {} was set status NOT_CHECKED",
                rollCheck);
    }

}