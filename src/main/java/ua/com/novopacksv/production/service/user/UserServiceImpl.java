package ua.com.novopacksv.production.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.NotUniqueFieldException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.userModel.User;
import ua.com.novopacksv.production.repository.userRepository.UserRepository;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            String message = String.format("User whit id = %d is not found!", id);
            return new ResourceNotFoundException(message);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        checkUsernameUnique(user);
        user.setPassword(passwordEncoder.encode(user.getUsername()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        checkUsernameUnique(user);
        User entity = findById(user.getId());
        user.setPassword(entity.getPassword());
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(() -> {
            String message = String.format("User whit username = %s is not found!", username);
            return new ResourceNotFoundException(message);
        });
    }

    private void checkUsernameUnique(User user) {
        User entityUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (entityUser != null && !hasSameId(user, entityUser)) {
            throw new NotUniqueFieldException("Username must be unique!");
        }
    }

    private boolean hasSameId(User user, User entityUser) {
        Long id = user.getId();
        Long entityId = entityUser.getId();
        return id != null && entityId != null && Objects.equals(id, entityId);
    }

}