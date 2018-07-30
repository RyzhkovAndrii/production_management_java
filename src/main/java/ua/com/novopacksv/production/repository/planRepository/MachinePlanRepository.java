package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.planModel.MachinePlan;

@Repository
public interface MachinePlanRepository extends JpaRepository<MachinePlan, Long> {
}
