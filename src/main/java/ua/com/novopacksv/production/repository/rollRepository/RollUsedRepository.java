package ua.com.novopacksv.production.repository.rollRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.rollModel.RollUsed;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RollUsedRepository extends JpaRepository <RollUsed, Long> {

    List<RollUsed> findAllByUsedDateBetween(LocalDate fromDate, LocalDate toDate);
}
