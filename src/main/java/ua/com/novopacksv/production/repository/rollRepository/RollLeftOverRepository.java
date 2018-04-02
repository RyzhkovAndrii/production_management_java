package ua.com.novopacksv.production.repository.rollRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.rollModel.RollLeftOver;

@Repository
public interface RollLeftOverRepository extends JpaRepository <RollLeftOver, Long> {
}
