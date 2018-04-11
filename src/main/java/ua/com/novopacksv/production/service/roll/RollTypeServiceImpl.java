package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.util.List;

@Service
@Transactional
public class RollTypeServiceImpl implements RollTypeService {

    @Override
    public RollType findById(Long id) {
        return null;
    }

    @Override
    public List<RollType> findAll() {
        return null;
    }

    @Override
    public RollType save(RollType rollType) {
        return null;
    }

    @Override
    public RollType update(RollType rollType) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}