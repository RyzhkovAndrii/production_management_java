package ua.com.novopacksv.production.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_left_over")
public class RollLeftOver extends BaseEntity{

    @OneToOne
    @PrimaryKeyJoinColumn
    private RollType rollType;

    @Column(name = "amount")
    private Integer amount;
}
