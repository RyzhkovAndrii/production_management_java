package ua.com.novopacksv.production.service.user;

import ua.com.novopacksv.production.model.userModel.TableModification;
import ua.com.novopacksv.production.model.userModel.TableType;

public interface TableModificationService {

    TableModification findOne(TableType tableType);

    TableModification update(TableType tableType);

}