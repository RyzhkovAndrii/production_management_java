package ua.com.novopacksv.production.model.productModel;

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
@Table(name = "product_batch")
public class ProductBatch extends BaseEntity {

    @OneToOne
    @PrimaryKeyJoinColumn
    private ProductType productType;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "amount")
    private Integer amount;
}
