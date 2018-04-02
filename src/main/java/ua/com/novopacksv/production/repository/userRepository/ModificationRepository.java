package ua.com.novopacksv.production.repository.userRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.userModel.Modification;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ModificationRepository extends JpaRepository <Modification, Long> {
    List<Modification> findAllByModificationDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
