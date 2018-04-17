package ua.com.novopacksv.production.repository.rollRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.rollModel.RollManufactured;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RollManufacturedRepository extends JpaRepository<RollManufactured, Long> {

    Optional<RollManufactured> findByManufacturedDateAndRollType(LocalDate manufacturedDate, RollType rollType);

    Optional<RollManufactured> findByManufacturedDateAndRollType_Id(LocalDate manufacturedDate, Long rollTypeId);

    List<RollManufactured> findAllByManufacturedDateBetween(LocalDate fromDate, LocalDate toDate);

    List<RollManufactured> findAllByManufacturedDateBetweenAndReadyToUseIsFalse(LocalDate fromDate, LocalDate toDate);

    List<RollManufactured> findAllByManufacturedDate(LocalDate manufacturedDate);

    List<RollManufactured> findAllByManufacturedDateBetweenAndRollType(
            LocalDate fromDate, LocalDate toDate, RollType rollType);

    List<RollManufactured> findAllByManufacturedDateBetweenAndRollType_Id(
            LocalDate fromManufacturedDate, LocalDate toManufacturedDate, Long rollTypeId);

}