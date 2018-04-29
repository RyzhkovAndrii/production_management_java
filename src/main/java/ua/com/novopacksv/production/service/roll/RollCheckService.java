package ua.com.novopacksv.production.service.roll;

import ua.com.novopacksv.production.model.rollModel.RollCheck;

import java.util.List;

public interface RollCheckService {

    RollCheck findOneByRollTypeId(Long id);

    List<RollCheck> findAll();

    RollCheck update(RollCheck check);

}