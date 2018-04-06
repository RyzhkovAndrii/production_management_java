package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_batch")
public class RollBatch extends BaseEntity {

    @OneToOne
    @PrimaryKeyJoinColumn
    private RollManufactured rollManufactured;

    @Column(name = "manufactured_amount")
    private Integer manufacturedAmount;

    @Column(name = "used_amount")
    private Integer usedAmount;

    @Column(name = "left_over_amount")
    private Integer leftOverAmount;
}
