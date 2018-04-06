package ua.com.novopacksv.production.model.productModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_batch")
public class ProductBatch extends BaseEntity {

    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductType productType;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductManufactured productManufactured;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductSold productSold;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private ProductLeftOver productLeftOver;
}
