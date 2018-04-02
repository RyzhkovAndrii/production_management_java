package ua.com.novopacksv.production.repository.userRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.userModel.User;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
}
