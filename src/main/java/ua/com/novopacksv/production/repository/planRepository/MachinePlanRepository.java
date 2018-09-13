package ua.com.novopacksv.production.repository.planRepository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.planModel.MachinePlan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MachinePlanRepository extends JpaRepository<MachinePlan, Long> {

    List<MachinePlan> findAllByMachineNumberAndTimeStartBetween(Integer machineNumber, LocalDateTime dateStart,
                                                                LocalDateTime dateEnd);

    List<MachinePlan> findAllByTimeStartBetweenAndMachineNumber(LocalDateTime dateStart, LocalDateTime dateEnd,
                                                                Integer machineNumber, Sort sort);

    List<MachinePlan> findAllByProductType_IdAndTimeStartBetween(Long productTypeId, LocalDateTime dateStart,
                                                                 LocalDateTime dateEnd);
}
