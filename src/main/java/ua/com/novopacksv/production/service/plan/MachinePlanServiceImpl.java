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

/**
 * Class implements interface {@link MachinePlanService} and contains methods for work with MachinePlan: CRUD and find by
 * pointed parameters
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MachinePlanServiceImpl implements MachinePlanService {

    /**
     * Time value of the starting a working shift as a beginning of a day
     */
    private static final LocalTime DAY_START_TIME = LocalTime.of(8, 0, 0);

    /**
     * Time value of the end of a last day's working shift as an end of a day
     */
    private static final LocalTime DAY_END_TIME = LocalTime.of(7, 59, 59);

    /**
     * An object of repository's layer for work with MachinePlan from db
     */
    private final MachinePlanRepository machinePlanRepository;

    /**
     * An object of service's layer for work with Norm
     */
    @Autowired
    @Lazy
    private NormService normService;

    /**
     * Method gets a duration in hours for producing of the product's amount of one MachinePlan
     *
     * @param machinePlan - MachinePlan
     * @return double, a quantity of hours that is needed for producing products
     * @throws ResourceNotFoundException if Norm for this ProductType does not exist in db
     */
    @Override
    public Double getDuration(MachinePlan machinePlan) throws ResourceNotFoundException {
        try {
            Double duration = ((double) getProductAmount(machinePlan) /
                    (normService.findOne(machinePlan.getProductType().getId()).getNormForDay() / 24));
            log.debug("Method getDuration(MachinePlan machinePlan): Duration is {} hours for MachinePlan {}", duration,
                    machinePlan);
            return duration;
        } catch (Exception e) {
            log.error("Method getDuration(MachinePlan machinePlan): Duration was not found for MachinePlan {}",
                    machinePlan);
            throw new ResourceNotFoundException("Norms for this product type was not found!");
        }
    }

    /**
     * Method finds all MachinePlans for one machine by it's number on one working day from 8*00 a.m. of
     * pointed date to 8*00 of the next date
     *
     * @param machineNumber - number of machine
     * @param date          - working day
     * @return List of MachinePlans for one machine on one working day
     */
    @Override
    public List<MachinePlan> findByMachineNumberAndDate(Integer machineNumber, LocalDate date) {
        LocalDateTime startDay = date.atTime(DAY_START_TIME);
        LocalDateTime endDay = date.plusDays(1).atTime(DAY_END_TIME);
        log.debug("Method findByMachineNumberAndDate(Integer machineNumber, LocalDate date): List<MachinePlan> for " +
                "machine #{} on day {} is finding", machineNumber, date);
        return machinePlanRepository.findAllByMachineNumberAndTimeStartBetween(machineNumber, startDay, endDay);
    }

    /**
     * Method finds and sorts by parameter all MachinePlans for one machine by it's number on one working day
     * from 8*00 a.m. of pointed date to 8*00 of the next date
     *
     * @param machineNumber  - number of machine
     * @param date           - working day
     * @param sortProperties - a parameter of sorting
     * @return List of sorted MachinePlans for one machine on one working day
     */
    @Override
    public List<MachinePlan> findSort(Integer machineNumber, LocalDate date, String sortProperties) {
        Sort sort = new Sort(Sort.Direction.ASC, sortProperties);
        LocalDateTime startDay = date.atTime(DAY_START_TIME);
        LocalDateTime endDay = date.plusDays(1).atTime(DAY_END_TIME);
        log.debug("Method findSort(Integer machineNumber, LocalDate date, String sortProperties): List<MachinePlan> " +
                "for machine #{} on day {} sorted by {} is finding", machineNumber, date, sortProperties);
        return machinePlanRepository.findAllByTimeStartBetweenAndMachineNumber(startDay, endDay, machineNumber, sort);
    }

    /**
     * Method finds all MachinePlans for one ProductType on one working day from 8*00 a.m. of pointed date to 8*00
     * of the next date
     *
     * @param productTypeId - ProductType's id
     * @param date          - working day
     * @return List of MachinePlans for one ProductType
     */
    @Override
    public List<MachinePlan> findByProductForMachinePlan(Long productTypeId, LocalDate date) {
        LocalDateTime startDay = date.atTime(DAY_START_TIME);
        LocalDateTime endDay = date.plusDays(1).atTime(DAY_END_TIME);
        log.debug("Method findByProductForMachinePlan(Long productTypeId, LocalDate date): List<MachinePlan> " +
                "for ProductType with id = {} on date {} is finding", productTypeId, date);
        return machinePlanRepository.findAllByProductType_IdAndTimeStartBetween(productTypeId, startDay, endDay);
    }

    /**
     * Method counts sum of productAmounts from all MachinePlans for one ProductType on one date
     *
     * @param productTypeId - ProductType's id
     * @param date          - pointed date
     * @return sum of productAmounts
     */
    @Override
    public Integer countProductAmountForMachinePlan(Long productTypeId, LocalDate date) {
        List<MachinePlan> plans = findByProductForMachinePlan(productTypeId, date);
        Integer sum = !plans.isEmpty() ? plans.stream().mapToInt(this::getProductAmount).sum() : 0;
        log.debug("Method countProductAmountForMachinePlan(Long productTypeId, LocalDate date): Sum of productAmounts " +
                "is {} for ProductType with id = {} on date {}", sum, productTypeId, date);
        return sum;
    }

    /**
     * Method counts productAmount from MachinePlanItems for a MachinePlan
     *
     * @param machinePlan - MachinePlan
     * @return sum of productAmounts from MachinePlanItems but if MachinePlanItems are absent or not determined (null)
     * return zero
     */
    @Override
    public Integer getProductAmount(MachinePlan machinePlan) {
        Integer amount = machinePlan.getMachinePlanItems() == null || machinePlan.getMachinePlanItems().isEmpty()
                ? 0
                : machinePlan.getMachinePlanItems()
                .stream()
                .mapToInt(MachinePlanItem::getProductAmount)
                .sum();
        log.debug("Method getProductAmount(MachinePlan machinePlan): Product amount is {} for MachinePlan {}", amount,
                machinePlan);
        return amount;
    }

    /**
     * Method finds MachinePlan by id from db
     *
     * @param id - MachinePlan's id
     * @return MachinePlan from db
     * @throws ResourceNotFoundException if MachinePlan with this id does not exist in db
     */
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

    /**
     * Method finds all existed MachinePlans from db
     *
     * @return List of MachinePlans
     */
    @Override
    public List<MachinePlan> findAll() {
        log.debug("Method findAll(): List<MachinePlan> is finding");
        return machinePlanRepository.findAll();
    }

    /**
     * Method tests if new MachinePlan is in day's interval and saves it in db
     *
     * @param machinePlan - new MachinePlan
     * @return new saved MachinePlan
     * @throws IntervalTimeForPlanException if this MachinePlan is not in valid interval of time
     */
    @Override
    public MachinePlan save(MachinePlan machinePlan) throws IntervalTimeForPlanException {
        ifTimeIsValid(machinePlan);
        MachinePlan machinePlanSaved = machinePlanRepository.save(machinePlan);
        log.debug("Method save(MachinePlan machinePlan): MachinePlan {} was saved", machinePlanSaved);
        return machinePlanSaved;
    }

    /**
     * Method tests if this MachinePlan exist in db and if it is in day's interval and saves it in db
     *
     * @param machinePlan - MachinePlan for update
     * @return updated MachinePlan
     * @throws ResourceNotFoundException    if this MachinePlan does not exist in db
     * @throws IntervalTimeForPlanException if this MachinePlan is not in valid interval of time
     */
    @Override
    public MachinePlan update(MachinePlan machinePlan) throws ResourceNotFoundException, IntervalTimeForPlanException {
        findById(machinePlan.getId());
        ifTimeIsValid(machinePlan);
        MachinePlan machinePlanUpdated = machinePlanRepository.save(machinePlan);
        log.debug("Method update(MachinePlan machinePlan): MachinePlan {} was updated", machinePlanUpdated);
        return machinePlanUpdated;
    }

    /**
     * Method tests if this MachinePlan exist in db and deletes it by id
     *
     * @param id - MachinePlan's id
     * @throws ResourceNotFoundException if this MachinePlan does not exist in db
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        machinePlanRepository.delete(findById(id));
        log.debug("Method delete(Long id): MachinePlane with id = {} was deleted", id);
    }

    /**
     * Method tests if MachinePlan is in interval of one working day that is from 8*00 a.m. of pointed in
     * MachinePlan's date to 8*00 a.m. of the next date
     *
     * @param machinePlan - MachinePlan
     * @throws IntervalTimeForPlanException if MachinePlan is out of interval
     */
    private void ifTimeIsValid(MachinePlan machinePlan) throws IntervalTimeForPlanException {
        ifInDay(machinePlan);
        List<MachinePlan> plans =
                findByMachineNumberAndDate(machinePlan.getMachineNumber(), machinePlan.getTimeStart().toLocalDate());
        if (!plans.isEmpty()) {
            plans.forEach((plan) -> ifInInterval(plan, machinePlan));
        }
        log.debug("Method ifTimeIsValid(MachinePlan machinePlan): MachinePlan {} was determined as valid");
    }

    /**
     * Method counts an end of working time for a MachinePlan
     *
     * @param machinePlan - MachinePlan
     * @return date and time of the end of planning work
     */
    private LocalDateTime findEndTime(MachinePlan machinePlan) {
        LocalDateTime endTime = machinePlan.getTimeStart().plusSeconds((long) (getDuration(machinePlan) * 360));
        log.debug("Method findEndTime(MachinePlan machinePlan): The end of work for MachinePlan {} is {}",
                machinePlan, endTime);
        return endTime;
    }

    /**
     * Method determines if one MachinePlan does not cover the other one by time interval
     *
     * @param machinePlanOne - one MachinePlan
     * @param machinePlan    - other MachinePlan
     * @return true if two MachinePlans don't cover one  other's by time interval or if this two are the same one
     * @throws IntervalTimeForPlanException if interval of working time for two MachinePlans covers one other's
     */
    private boolean ifInInterval(MachinePlan machinePlanOne, MachinePlan machinePlan) throws IntervalTimeForPlanException {
        if (!machinePlanOne.getId().equals(machinePlan.getId())) {
            if (machinePlanOne.getTimeStart().isAfter(findEndTime(machinePlan))
                    || findEndTime(machinePlanOne).isBefore(machinePlan.getTimeStart())) {
                log.debug("Method ifInInterval(MachinePlan machinePlanOne, MachinePlan machinePlan): MachinePlan {}" +
                        "and MachinePlan {} has a correct time interval", machinePlanOne, machinePlan);
                return true;
            } else {
                log.error("Method ifInInterval(MachinePlan machinePlanOne, MachinePlan machinePlan): MachinePlan {}" +
                        "and MachinePlan {} has no a correct time interval", machinePlanOne, machinePlan);
                throw new IntervalTimeForPlanException("Time interval is incorrect!");
            }
        } else {
            log.debug("Method ifInInterval(MachinePlan machinePlanOne, MachinePlan machinePlan): MachinePlan {}" +
                    "equals to MachinePlan {}", machinePlanOne, machinePlan);
            return true;
        }
    }

    /**
     * Method tests if end of work of MachinePlan is in working day from 8*00 a.m. of the date to 8*00 a.m. of the
     * next date
     *
     * @param machinePlan - MachinePlan
     * @throws IntervalTimeForPlanException if the end of working time is over the working day
     */
    private void ifInDay(MachinePlan machinePlan) throws IntervalTimeForPlanException {
        LocalDateTime startDay = machinePlan.getTimeStart().toLocalDate().atTime(DAY_START_TIME);
        LocalDateTime endDay = machinePlan.getTimeStart().toLocalDate().plusDays(1).atTime(DAY_END_TIME);
        if (findEndTime(machinePlan).toLocalDate().isBefore(machinePlan.getTimeStart().toLocalDate())) {
            log.error("Method ifInDay(MachinePlan machinePlan): Working time for MachinePlan {} is out of working " +
                    "day!", machinePlan);
            throw new IntervalTimeForPlanException("End for plan is out of the date!");
        }
    }
}
