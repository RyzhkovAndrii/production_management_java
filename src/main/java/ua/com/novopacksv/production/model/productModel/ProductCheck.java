package ua.com.novopacksv.production.model.productModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;
import ua.com.novopacksv.production.model.rollModel.CheckStatus;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_check")
public class ProductCheck extends BaseEntity {

    @OneToOne
    @PrimaryKeyJoinColumn
    @MapsId
    private ProductType productType;

    @Column(name = "product_left_over_check_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CheckStatus productLeftOverCheckStatus;
}
