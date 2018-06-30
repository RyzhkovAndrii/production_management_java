package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.novopacksv.production.model.planModel.RollPlanOperation;

public interface RollPlanOperationRepository extends JpaRepository<RollPlanOperation, Long> {
}
