package ua.com.novopacksv.production.model.productModel;

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
@Table(name = "product_left_over")
public class ProductLeftOver extends BaseEntity {

    @Column(name = "left_date", nullable = false)
    private LocalDate leftDate;

    @OneToOne
    @PrimaryKeyJoinColumn
    @MapsId
    private ProductType productType;

    @Column(name = "amount", nullable = false)
    private Integer amount;
}
