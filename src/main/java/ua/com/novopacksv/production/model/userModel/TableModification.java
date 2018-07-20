package ua.com.novopacksv.production.model.userModel;

import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "table_modifications")
public class TableModification extends BaseEntity {

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "modification_date", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modificationDateTime;

    @Column(name = "table_type", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private TableType tableType;

}