package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.planModel.MachinePlan;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MachinePlanRepository extends JpaRepository<MachinePlan, Long> {

    List<MachinePlan> findAllByMachineNumberAndTimeStartContains(Integer machineNumber, LocalDate date);

    List<MachinePlan> findAllByTimeStartContainsAndMachineNumber(LocalDate date, Integer machineNumber, Sort sort);

    List<MachinePlan> findAllByProductType_IdAndTimeStartContains(Long productTypeId, LocalDate date);
}
