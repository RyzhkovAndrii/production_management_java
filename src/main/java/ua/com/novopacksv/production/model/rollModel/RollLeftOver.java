package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_left_over")
public class RollLeftOver extends BaseEntity {

    @Column(name = "date", nullable = false)
    @UpdateTimestamp
    private LocalDate date;

    @OneToOne
    @PrimaryKeyJoinColumn
    @MapsId
    private RollType rollType;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
