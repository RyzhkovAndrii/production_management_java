package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_used")
public class RollUsed extends BaseEntity {

    @Column(name = "used_date")
    private LocalDate usedDate;

    @Column(name = "used_amount")
    private Integer usedAmount;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private RollBatch rollBatch;
}
