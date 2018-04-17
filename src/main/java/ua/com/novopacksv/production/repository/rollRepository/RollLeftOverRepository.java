package ua.com.novopacksv.production.repository.rollRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;

import java.util.Optional;


@Repository
public interface RollLeftOverRepository extends JpaRepository <RollLeftOver, Long> {

    Optional<RollLeftOver> findByRollType_Id(Long rollTypeId);
}
