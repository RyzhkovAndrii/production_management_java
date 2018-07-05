package ua.com.novopacksv.production.service.norm;

import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.util.List;

public interface NormService extends BaseEntityService<Norm> {

    List<Norm> findNorms(Long rollTypeId);

    void deleteNormsWithoutRolls();

    Norm findOne(Long productTypeId);

    Boolean findFirstByProductTypeId(Long productTypeId);

    Boolean findFirstByRollTypeId(Long rollTypeId);
}
