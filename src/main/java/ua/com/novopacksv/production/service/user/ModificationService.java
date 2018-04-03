package ua.com.novopacksv.production.service.user;

import ua.com.novopacksv.production.model.userModel.Modification;
import ua.com.novopacksv.production.service.BaseEntityService;

public interface ModificationService extends BaseEntityService<Modification> {

    // todo remove extends from base entity and don't write create and public update method

    // todo get by table

    // todo method like getLastByTableType()

    // todo create() method which must be called when some data is modified

}
