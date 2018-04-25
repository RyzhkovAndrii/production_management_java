package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.rollModel.RollType;
import ua.com.novopacksv.production.service.BaseEntityService;

public interface RollTypeService extends BaseEntityService<RollType> {

    RollType findOne(String name) throws ResourceNotFoundException;

}