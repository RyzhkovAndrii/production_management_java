package ua.com.novopacksv.production.model.rollModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_check")
public class RollCheck extends BaseEntity {

    @OneToOne
    @PrimaryKeyJoinColumn
    @MapsId
    private RollType rollType;

    @Column(name = "roll_left_over_check_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CheckStatus rollLeftOverCheckStatus;

}