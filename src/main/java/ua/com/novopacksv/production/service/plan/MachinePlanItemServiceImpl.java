package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.repository.planRepository.MachinePlanItemRepository;

import java.util.List;

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

}
