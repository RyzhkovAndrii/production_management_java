package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.util.Optional;

@Repository
public interface MachinePlanItemRepository extends JpaRepository<MachinePlanItem, Long> {

    Optional<MachinePlanItem> findByRollTypeAndMachinePlan(RollType rollType, MachinePlan machinePlan);

}
