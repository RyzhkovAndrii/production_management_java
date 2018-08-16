package ua.com.novopacksv.production.service.plan;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.novopacksv.production.exception.IntervalTimeForPlanException;
import ua.com.novopacksv.production.exception.ResourceNotFoundException;
import ua.com.novopacksv.production.model.planModel.MachinePlan;
import ua.com.novopacksv.production.model.planModel.MachinePlanItem;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.repository.planRepository.MachinePlanRepository;
import ua.com.novopacksv.production.service.norm.NormService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MachinePlanServiceImpl implements MachinePlanService {

    private static final LocalTime DAY_START_TIME = LocalTime.of(8, 0, 0);
    private static final LocalTime DAY_END_TIME = LocalTime.of(7, 59, 59);

    private final MachinePlanRepository machinePlanRepository;

    @Autowired
    @Lazy
    private NormService normService;

    @Override
    public Double getDuration(MachinePlan machinePlan) {
        return countDuration(machinePlan.getProductType(), getProductAmount(machinePlan));
    }

    @Override
    public List<MachinePlan> findByMachineNumberAndDate(Integer machineNumber, LocalDate date) {
        LocalDateTime startDay = date.atTime(DAY_START_TIME);
        LocalDateTime endDay = date.plusDays(1).atTime(DAY_END_TIME);
        return machinePlanRepository.findAllByMachineNumberAndTimeStartBetween(machineNumber, startDay, endDay);
    }

    @Override
    public List<MachinePlan> findSort(Integer machineNumber, LocalDate date, String sortProperties) {
        Sort sort = new Sort(Sort.Direction.ASC, sortProperties);
        LocalDateTime startDay = date.atTime(DAY_START_TIME);
        LocalDateTime endDay = date.plusDays(1).atTime(DAY_END_TIME);
        return machinePlanRepository.findAllByTimeStartBetweenAndMachineNumber(startDay, endDay, machineNumber, sort);
    }

    @Override
    public List<MachinePlan> findByProductForMachinePlan(Long productTypeId, LocalDate date) {
        LocalDateTime startDay = date.atTime(DAY_START_TIME);
        LocalDateTime endDay = date.plusDays(1).atTime(DAY_END_TIME);
        return machinePlanRepository.findAllByProductType_IdAndTimeStartBetween(productTypeId, startDay, endDay);
    }

    @Override
    public Integer countProductAmountForMachinePlan(Long productTypeId, LocalDate date) {
        List<MachinePlan> plans = findByProductForMachinePlan(productTypeId, date);
        return !plans.isEmpty() ? plans.stream().mapToInt(this::getProductAmount).sum() : 0;
    }

    @Override
    public Integer getProductAmount(MachinePlan machinePlan) {
        return machinePlan.getMachinePlanItems() == null
                ? 0
                : machinePlan.getMachinePlanItems()
                .stream()
                .mapToInt(MachinePlanItem::getProductAmount)
                .sum();
    }

    @Override
    public MachinePlan findById(Long id) throws ResourceNotFoundException {
        MachinePlan machinePlan = machinePlanRepository.findById(id).orElseThrow(() -> {
            String message = String.format("MachinePlan with id = %d was not found", id);
            log.error("Method findById(Long id): MachinePlan with id = {} was not found", id);
            return new ResourceNotFoundException(message);
        });
        log.debug("Method findById(Long id): MachinePlan with id = {} was found: {}", id, machinePlan);
        return machinePlan;
    }

    @Override
    public List<MachinePlan> findAll() {
        log.debug("Method findAll(): List<MachinePlan> is finding");
        return machinePlanRepository.findAll();
    }

    @Override
    public MachinePlan save(MachinePlan machinePlan) {
        ifTimeIsValid(machinePlan);
        log.debug("Method save(MachinePlan machinePlan): MachinePlan {} was saved", machinePlan);
        return machinePlanRepository.save(machinePlan);
    }

    @Override
    public MachinePlan update(MachinePlan machinePlan) throws ResourceNotFoundException {
        findById(machinePlan.getId());
        machinePlanRepository.save(machinePlan);
        MachinePlan machinePlanUpdated = findById(machinePlan.getId());
        log.debug("Method update(MachinePlan machinePlan): MachinePlan {} was updated", machinePlanUpdated);
        return machinePlanUpdated;
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        machinePlanRepository.delete(findById(id));
        log.debug("Method delete(Long id): MachinePlane with id = {} was deleted", id);
    }

    private Double countDuration(ProductType productType, Integer productAmount) {
        try {
            return ((double) productAmount / (normService.findOne(productType.getId()).getNormForDay() / 24));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Norms for this product type was not found!");
        }
    }

    private void ifTimeIsValid(MachinePlan machinePlan) {
        ifInDay(machinePlan);
        List<MachinePlan> plans =
                findByMachineNumberAndDate(machinePlan.getMachineNumber(), machinePlan.getTimeStart().toLocalDate());
        if (!plans.isEmpty()) {
            plans.forEach((plan) -> ifInInterval(plan, machinePlan));
        }
    }

    private LocalDateTime findEndTime(MachinePlan machinePlan) {
        return machinePlan.getTimeStart().plusSeconds((long) (getDuration(machinePlan) * 360));
    }

    private boolean ifInInterval(MachinePlan machinePlanOne, MachinePlan machinePlan) {
        if (!machinePlanOne.getId().equals(machinePlan.getId())) {
            if (machinePlanOne.getTimeStart().isAfter(findEndTime(machinePlan))
                    || findEndTime(machinePlanOne).isBefore(machinePlan.getTimeStart())) {
                return true;
            } else throw new IntervalTimeForPlanException("Time interval is incorrect!");
        } else return true;
    }

    private void ifInDay(MachinePlan machinePlan) {
        if (!findEndTime(machinePlan).toLocalDate().equals(machinePlan.getTimeStart().toLocalDate())) {
            throw new IntervalTimeForPlanException("End for plan is out of the date!");
        }
    }
}
