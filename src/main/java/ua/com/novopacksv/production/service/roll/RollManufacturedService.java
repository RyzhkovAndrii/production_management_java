package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.time.LocalDate;

@Service
public interface RollManufacturedService extends BaseEntityService<RollManufactured> {

    RollManufactured findByManufacturedDateAndRollTypeOrCreateNew(LocalDate manufacturedDate, RollType rollType);

}
