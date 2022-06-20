package com.test.figures.entity.figures;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Map;


@Entity
@NoArgsConstructor
@ToString
@DiscriminatorValue("RECTANGLE")
@Data
public class Rectangle extends Figure {
    private double width;
    private double height;

    public Rectangle(LocalDate createdDate, String author, int version, LocalDate lastModification, String lastModifier, double width, double height) {
        super(createdDate, author, version, lastModification, lastModifier);
        this.width = width;
        this.height = height;
    }

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public Map<String, Double> getParameters() {
        return Map.of("width", width, "height", height);
    }
}
