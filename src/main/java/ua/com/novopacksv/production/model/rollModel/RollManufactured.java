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
@Table(name = "roll_manufactured")
public class RollManufactured extends BaseEntity {

    @Column(name = "manufactured_date")
    private LocalDate manufacturedDate;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private RollType rollType;

    @Column(name = "ready_to_use")
    private Boolean readyToUse;
}
