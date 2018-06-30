package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RollPlanOperationRepository extends JpaRepository<RollPlanOperation, Long> {

    List<RollPlanOperation> findAllByRollType_IdAndDateBetween(Long rollTypeId, LocalDate fromDate, LocalDate toDate);
}
