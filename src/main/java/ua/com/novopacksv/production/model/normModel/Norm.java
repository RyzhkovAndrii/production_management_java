package ua.com.novopacksv.production.model.normModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;
import ua.com.novopacksv.production.model.productModel.ProductType;
import ua.com.novopacksv.production.model.rollModel.RollType;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "norm")
public class Norm extends BaseEntity {

    @ManyToMany
    @PrimaryKeyJoinColumn
    private List<RollType> rollTypes;

    @OneToOne
    @PrimaryKeyJoinColumn
    @MapsId
    private ProductType productType;

    @Column(name = "norm", nullable = false)
    private Double norm;
}
