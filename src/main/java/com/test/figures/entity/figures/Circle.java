package com.test.figures.entity.figures;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Map;


@Entity
@Accessors(chain = true)
@NoArgsConstructor
@DiscriminatorValue("CIRCLE")
@Data
public class Circle extends Figure {
    private double radius;

    public Circle(LocalDate createdDate, String author, int version, LocalDate lastModification, String lastModifier, double radius) {
        super(createdDate, author, version, lastModification, lastModifier);
        this.radius = radius;
    }

    @Override
    public String getType() {
        return "CIRCLE";
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public Map<String, Double> getParameters() {
        return Map.of("radius", radius);
    }
}
