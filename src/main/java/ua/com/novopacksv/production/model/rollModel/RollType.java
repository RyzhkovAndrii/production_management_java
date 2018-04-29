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

    public final static Integer READY_TO_USE_PERIOD = 14;

    @Column(name = "note")
    private String note;

    @Column(name = "thickness", nullable = false)
    private Double thickness;

    @Column(name = "min_weight", nullable = false)
    private Double minWeight;

    @Column(name = "max_weight", nullable = false)
    private Double maxWeight;

    @Column(name = "color_code", nullable = false)
    private String colorCode;
}
