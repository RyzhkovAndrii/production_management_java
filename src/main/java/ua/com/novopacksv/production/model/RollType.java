package ua.com.novopacksv.production.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "roll_type")
public class RollType extends BaseEntity{

    @Column(name = "thickness")
    private Double thickness;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "color_code")
    private String colorCode;
}
