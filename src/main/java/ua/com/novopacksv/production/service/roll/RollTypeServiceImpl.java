package ua.com.novopacksv.production.service.roll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.repository.rollRepository.RollTypeRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RollTypeServiceImpl implements RollTypeService {

    private final RollTypeRepository rollTypeRepository;

    @Override
    @Transactional(readOnly = true)
    public RollType findById(Long id) throws ResourceNotFoundException {
        return rollTypeRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Roll Type whit id = %d not found!", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollType> findAll() {
        return rollTypeRepository.findAll();
    }

    @Override
    public RollType save(RollType rollType) {
        return rollTypeRepository.save(rollType);
    }

    @Override
    public RollType update(RollType rollType) {
        return this.save(rollType);
    }

    @Override
    public void delete(Long id) {
        RollType rollType = findById(id);
        rollTypeRepository.delete(rollType);
    }

}