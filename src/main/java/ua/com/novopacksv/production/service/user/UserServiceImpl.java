package ua.com.novopacksv.production.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Override
    public ua.com.novopacksv.production.model.userModel.User findById(Long id) {
        return null;
    }

    @Override
    public List<ua.com.novopacksv.production.model.userModel.User> findAll() {
        return null;
    }

    @Override
    public ua.com.novopacksv.production.model.userModel.User save(ua.com.novopacksv.production.model.userModel.User user) {
        return null;
    }

    @Override
    public ua.com.novopacksv.production.model.userModel.User update(ua.com.novopacksv.production.model.userModel.User user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}