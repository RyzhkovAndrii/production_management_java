package ua.com.novopacksv.production.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.userModel.TableModification;
import ua.com.novopacksv.production.model.userModel.TableType;

@Service
@Transactional
public class TableModificationServiceImpl implements TableModificationService {

    @Override
    @Transactional(readOnly = true)
    public TableModification findOne(TableType tableType) {
        return null;
    }

    @Override
    public TableModification update(TableType tableType) {
        return null;
    }

}