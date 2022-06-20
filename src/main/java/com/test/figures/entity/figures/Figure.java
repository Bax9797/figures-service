package com.test.figures.entity.figures;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Data
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class Figure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @CreatedDate
    private LocalDate createdDate;
    @CreatedBy
    private String createdBy;
    @Version
    private int version;
    @LastModifiedDate
    private LocalDate lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;

    public Figure(LocalDate createdDate, String createdBy, int version, LocalDate lastModifiedDate, String lastModifiedBy) {
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.version = version;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedBy = lastModifiedBy;
    }

    public abstract String getType();

    public abstract double getArea();

    public abstract Map<String, Double> getParameters();
}
