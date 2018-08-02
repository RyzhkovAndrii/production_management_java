package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.planModel.MachinePlan;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MachinePlanRepository extends JpaRepository<MachinePlan, Long> {


    List<MachinePlan> findByMachineNumberAndTimeStart_DateOrderByTimeStart(Integer machineNumber, LocalDate date);

    List<MachinePlan> findByMachineNumberAndTimeStart_Date(Integer machineNumber, LocalDate date, Sort sort);
}
