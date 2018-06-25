package ua.com.novopacksv.production.service.norm;

import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.service.BaseEntityService;

import java.util.List;

public interface NormService extends BaseEntityService<Norm> {
    List<Norm> findNorms(Long rollTypeId);
}
