package ua.com.novopacksv.production.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.userModel.TableModification;
import ua.com.novopacksv.production.model.userModel.TableType;
import ua.com.novopacksv.production.repository.userRepository.TableModificationRepository;
import ua.com.novopacksv.production.security.SecurityService;

@Service
@Transactional
@RequiredArgsConstructor
public class TableModificationServiceImpl implements TableModificationService {

    private final TableModificationRepository tableModificationRepository;

    private final SecurityService service;

    @Override
    @Transactional(readOnly = true)
    public TableModification findOne(TableType tableType) {
        return tableModificationRepository.findByTableType(tableType).orElseThrow(() -> {
            String message = String.format("Table type %s is not found!", tableType);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    public TableModification update(TableType tableType) {
        TableModification entity = findOne(tableType);
        entity.setUser(service.getLoggedInUser());
        return tableModificationRepository.save(entity);
    }

}