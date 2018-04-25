package ua.com.novopacksv.production.repository.rollRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.rollModel.RollType;

import java.util.Optional;

@Repository
public interface RollTypeRepository extends JpaRepository <RollType, Long> {

    Optional<RollType> findOne(String name);

}