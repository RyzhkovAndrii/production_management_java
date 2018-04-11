package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RollManufacturedService {

    RollManufactured findByManufacturedDateAndRollTypeOrCreateNew(LocalDate manufacturedDate, RollType rollType);

    List<RollManufactured> findAllByManufacturedDate(LocalDate date);

    List<RollManufactured> findAllByManufacturedPeriod(LocalDate fromDate, LocalDate toDate);

    List<RollManufactured> findAllByRollTypeIdAndManufacturedPeriod(Long rollTypeId, LocalDate fromDate, LocalDate toDate);

    Integer getManufacturedRollAmount(RollManufactured rollManufactured);

    Integer getUsedRollAmount(RollManufactured rollManufactured);

}