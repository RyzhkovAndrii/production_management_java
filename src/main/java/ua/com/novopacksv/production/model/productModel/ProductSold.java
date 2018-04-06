package ua.com.novopacksv.production.model.productModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_sold")
public class ProductSold extends BaseEntity {

    @Column(name = "sold_date")
    private LocalDate soldDate;

    @OneToMany(mappedBy = "productSold")
    private List<ProductBatch> productBatches;
}
