package ua.com.novopacksv.production.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.model.userModel.Modification;

import java.util.List;

@Service
@Transactional
public class ModificationServiceImpl implements ModificationService {

    @Override
    public Modification findById(Long id) {
        return null;
    }

    @Override
    public List<Modification> findAll() {
        return null;
    }

    @Override
    public Modification save(Modification modification) {
        return null;
    }

    @Override
    public Modification update(Modification modification) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}