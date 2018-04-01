package ua.com.novopacksv.production.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@javax.persistence.Table(name = "modification")
public class Modification extends BaseEntity {

    @OneToOne
    @PrimaryKeyJoinColumn
    private User user;

    @Column(name = "modification_date")
    private Date modificationDate;

    @Column(name = "table_type")
    @Enumerated(EnumType.STRING)
    private Table tableType;
}
