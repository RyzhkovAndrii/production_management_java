package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

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
    private Date creationDate;

    @Column(name = "ready_to_use_date")
    private Date readyToUseDate;

    @Column(name = "amount")
    private Integer amount;
}
