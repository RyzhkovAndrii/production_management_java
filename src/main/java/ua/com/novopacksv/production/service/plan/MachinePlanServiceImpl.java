package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.repository.planRepository.MachinePlanRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MachinePlanServiceImpl implements MachinePlanService {

    private final MachinePlanRepository machinePlanRepository;

    @Override
    public MachinePlan findById(Long id) throws ResourceNotFoundException {
        MachinePlan machinePlan = machinePlanRepository.findById(id).orElseThrow(() -> {
            String message = String.format("MachinePlan with id = %d was not found", id);
            log.error("Method findById(Long id): MachinePlan with id = {} was not found", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): MachinePlan with id = {} was found: {}", id, machinePlan);
        return machinePlan;
    }

    @Override
    public List<MachinePlan> findAll() {
        log.debug("Method findAll(): List<MachinePlan> is finding");
        return machinePlanRepository.findAll();
    }

    @Override
    public MachinePlan save(MachinePlan machinePlan) {
        log.debug("Method save(MachinePlan machinePlan): MachinePlan {} was saved", machinePlan);
        return machinePlanRepository.save(machinePlan);
    }

    @Override
    public MachinePlan update(MachinePlan machinePlan) throws ResourceNotFoundException {
        findById(machinePlan.getId());
        machinePlanRepository.save(machinePlan);
        MachinePlan machinePlanUpdated = findById(machinePlan.getId());
        log.debug("Method update(MachinePlan machinePlan): MachinePlan {} was updated", machinePlanUpdated);
        return machinePlanUpdated;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        machinePlanRepository.delete(findById(id));
        log.debug("Method delete(Long id): MachinePlane with id = {} was deleted", id);
    }
}
