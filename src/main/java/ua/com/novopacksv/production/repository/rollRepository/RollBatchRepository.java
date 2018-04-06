package ua.com.novopacksv.production.repository.rollRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.rollModel.RollBatch;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.util.List;


@Repository
public interface RollBatchRepository extends JpaRepository<RollBatch, Long> {

    List<RollBatch> findAllByRollManufactured_RollType(RollType rollType);

}
