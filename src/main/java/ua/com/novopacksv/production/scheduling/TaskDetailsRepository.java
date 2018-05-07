package ua.com.novopacksv.production.scheduling;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetails, Long> {

    List<TaskDetails> findAllByNextExecutionTimeIsBefore(LocalDateTime time);

    Optional<TaskDetails> findByTaskClassName(String taskClassName);

}