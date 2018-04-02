package ua.com.novopacksv.production.model.userModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.com.novopacksv.production.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@javax.persistence.Table(name = "modification")
public class Modification extends BaseEntity {

    @ManyToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @Column(name = "table_type")
    @Enumerated(EnumType.STRING)
    private Table tableType;
}
