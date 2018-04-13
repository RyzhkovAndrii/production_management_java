package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollOperation;

import java.util.List;

@Service
@Transactional
public class RollOperationServiceImpl implements RollOperationService {

    @Override
    public RollOperation findById(Long id) {
        return null;
    }

    @Override
    public List<RollOperation> findAll() {
        return null;
    }

    @Override
    public RollOperation save(RollOperation rollOperation) {
        return null;
    }

    @Override
    public RollOperation update(RollOperation rollOperation) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<RollOperation> getAllManufacturedOperationsByRollManufactured(RollManufactured rollManufactured) {
        return null;
    }

    @Override
    public List<RollOperation> getAllUsedOperationsByRollManufactured(RollManufactured rollManufactured) {
        return null;
    }
}