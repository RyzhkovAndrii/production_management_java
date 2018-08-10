package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NotUniqueFieldException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.repository.planRepository.MachinePlanItemRepository;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class MachinePlanItemServiceImpl implements MachinePlanItemService {

    private final MachinePlanItemRepository machinePlanItemRepository;

    @Override
    public MachinePlanItem findById(Long id) throws ResourceNotFoundException {
        MachinePlanItem machinePlanItem = machinePlanItemRepository.findById(id).orElseThrow(() -> {
            String message = String.format("MachinePlanItem with id = %d was not found", id);
            return new ResourceNotFoundException(message);
        });
        return machinePlanItem;
    }

    @Override
    public List<MachinePlanItem> findAll() {
        return machinePlanItemRepository.findAll();
    }

    @Override
    public MachinePlanItem save(MachinePlanItem item) {
        checkUniqueConstraint(item);
        return machinePlanItemRepository.save(item);
    }

    @Override
    public MachinePlanItem update(MachinePlanItem item) throws ResourceNotFoundException {
        return save(item);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        machinePlanItemRepository.delete(findById(id));
    }

    private void checkUniqueConstraint(MachinePlanItem item) {
        MachinePlanItem entityItem = machinePlanItemRepository
                .findByRollTypeAndMachinePlan(item.getRollType(), item.getMachinePlan())
                .orElse(null);
        if (entityItem != null && !hasSameId(item, entityItem)) {
            throw new NotUniqueFieldException("Roll type with machine plan in machine plan item must be unique!");
        }
    }

    private boolean hasSameId(MachinePlanItem item, MachinePlanItem entityItem) {
        Long id = item.getId();
        Long entityId = entityItem.getId();
        return id != null && entityId != null && Objects.equals(id, entityId);
    }

}
