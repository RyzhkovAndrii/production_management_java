package ua.com.novopacksv.production.service.norm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.normModel.Norm;
import ua.com.novopacksv.production.repository.normRepository.NormRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NormServiceImpl implements NormService {

    private final NormRepository normRepository;

    @Override
    public Norm findById(Long id) throws ResourceNotFoundException {
        return normRepository.findById(id).orElseThrow(() ->
        {
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
        return normRepository.save(norm);
    }

    @Override
    public Norm update(Norm norm) throws ResourceNotFoundException {
        findById(norm.getId());
        return normRepository.save(norm);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        normRepository.delete(findById(id));
    }

    @Override
    public List<Norm> findNorms(Long rollTypeId) {
        return normRepository.getByRollTypeId(rollTypeId);
    }
}
