package ua.com.novopacksv.production.service.norm;

import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.service.BaseEntityService;

public interface NormService extends BaseEntityService<Norm> {

    Norm findOne(Long productTypeId);
}
