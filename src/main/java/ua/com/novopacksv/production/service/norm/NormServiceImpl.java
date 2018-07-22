package ua.com.novopacksv.production.service.norm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.model.userModel.TableType;
import ua.com.novopacksv.production.repository.normRepository.NormRepository;
import ua.com.novopacksv.production.service.user.TableModificationService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NormServiceImpl implements NormService {

    private final static TableType TABLE_TYPE_FOR_UPDATE = TableType.NORMS;

    private final NormRepository normRepository;

    private final TableModificationService tableModificationService;

    @Override
    public Norm findById(Long id) throws ResourceNotFoundException {
        return normRepository.findById(id).orElseThrow(() -> {
            String message = String.format("Norm with id = %d was not found", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    public List<Norm> findAll() {
        return normRepository.findAll();
    }

    @Override
    public Norm save(Norm norm) {
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        return normRepository.save(norm);
    }

    @Override
    public Norm update(Norm norm) throws ResourceNotFoundException {
        findById(norm.getId());
        return save(norm);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        tableModificationService.update(TABLE_TYPE_FOR_UPDATE);
        normRepository.delete(findById(id));
    }

    @Override
    public List<Norm> findNorms(Long rollTypeId) {
        return normRepository.getByRollTypeId(rollTypeId);
    }

    @Override
    public void deleteNormsWithoutRolls() {
        normRepository.deleteNormsByRollTypesNull();
    }

}