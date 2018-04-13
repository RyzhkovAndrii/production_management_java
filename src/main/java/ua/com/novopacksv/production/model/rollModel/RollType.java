package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_type")
public class RollType extends BaseEntity {

    public final static Integer ROLL_WAITING_PERIOD_FOR_READY_TO_USE_IN_DAYS = 14;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "thickness", nullable = false)
    private Double thickness;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "color_code", nullable = false)
    private String colorCode;
}
