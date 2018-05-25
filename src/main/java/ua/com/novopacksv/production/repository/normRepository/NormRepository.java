package ua.com.novopacksv.production.repository.normRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.normModel.Norm;

@Repository
public interface NormRepository extends JpaRepository<Norm, Long> {
}
