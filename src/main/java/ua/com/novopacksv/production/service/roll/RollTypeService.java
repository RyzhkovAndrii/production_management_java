package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.util.List;

public interface RollTypeService extends BaseEntityService<RollType> {

    List<RollType> findAll(Double thickness);

    List<RollType> findAll(String colorCode);
}