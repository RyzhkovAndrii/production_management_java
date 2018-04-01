package ua.com.novopacksv.production.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_batch")
public class ProductBatch extends BaseEntity{

    @OneToOne
    @PrimaryKeyJoinColumn
    private ProductType productType;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "amount")
    private Double amount;
}
