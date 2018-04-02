package ua.com.novopacksv.production.model.orderModel;

import lombok.*;
import org.hibernate.annotations.Type;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "order")
public class Order extends BaseEntity {

    @ManyToOne
    @PrimaryKeyJoinColumn
    private Client client;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Type(type = "true_false")
    @Column(name = "is_important")
    @NonNull
    private Boolean isImportant;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
}
