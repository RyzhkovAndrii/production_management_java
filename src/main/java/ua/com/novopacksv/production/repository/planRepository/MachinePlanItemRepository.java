package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;

@Repository
public interface MachinePlanItemRepository extends JpaRepository<MachinePlanItem, Long> {
}
