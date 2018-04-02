package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_batch")
public class RollBatch extends BaseEntity {

    @OneToOne
    @PrimaryKeyJoinColumn
    private RollType rollType;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "ready_to_use_date")
    private LocalDate readyToUseDate;

    @Column(name = "amount")
    private Integer amount;
}
