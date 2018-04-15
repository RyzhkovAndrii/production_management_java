package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.time.LocalDate;
import java.util.List;

@Service
public interface RollManufacturedService {

    RollManufactured findOneOrCreate(LocalDate manufacturedDate, RollType rollType);

    RollManufactured findOne(LocalDate manufacturedDate, RollType rollType)
            throws ResourceNotFoundException;

    List<RollManufactured> findAll(LocalDate date);

    List<RollManufactured> findAll(LocalDate fromDate, LocalDate toDate);

    List<RollManufactured> findAll(
            LocalDate fromDate, LocalDate toDate, RollType rollType);

    Integer getManufacturedRollAmount(RollManufactured rollManufactured);

    Integer getUsedRollAmount(RollManufactured rollManufactured);

}