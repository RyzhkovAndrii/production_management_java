package ua.com.novopacksv.production.service.roll;

import org.springframework.stereotype.Service;
import ua.com.novopacksv.production.model.rollModel.RollOperation;
import ua.com.novopacksv.production.service.BaseEntityService;

@Service
public interface RollOperationService extends BaseEntityService<RollOperation> {

    // todo will check "ready to use" if create use operation
    // (if roll is not ready - you can't create use operation"

}