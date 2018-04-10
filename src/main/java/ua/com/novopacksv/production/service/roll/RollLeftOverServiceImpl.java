package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RollLeftOverServiceImpl implements RollLeftOverService {

    @Override
    public List<RollLeftOver> findAllByDate(LocalDate date) {
        return null;
    }

    @Override
    public RollLeftOver findByRollTypeIdAndDate(Long rollTypeId, LocalDate date) {
        return null;
    }

    @Override
    public RollLeftOver findById(Long id) {
        return null;
    }

    @Override
    public List<RollLeftOver> findAll() {
        return null;
    }

    @Override
    public RollLeftOver save(RollLeftOver rollLeftOver) {
        return null;
    }

    @Override
    public RollLeftOver update(RollLeftOver rollLeftOver) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}